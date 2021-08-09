package com.cdzq.study.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.cdzq.study.base.NonStaticResourceHttpRequestHandler;
import com.cdzq.study.base.ResultData;
import com.cdzq.study.entity.LearningMyCourseware;
import com.cdzq.study.entity.LearningTasks;
import com.cdzq.study.mapper.LearningTasksMapper;
import com.cdzq.study.security.PassToken;
import com.cdzq.study.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Api(tags = "学习模块")
public class StudyController {

    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
    private final JdbcTemplate jdbcTemplate;

    private final LearningTasksMapper learningTasksMapper;

    @Value("${video.realpath}")
    private String videoRealpath;
    @Value("${video.abspath}")
    private String videoAbspath;
    @Value("${image.abspath}")
    private String imageAbspath;

    @GetMapping("/getMyTasks")
    @ApiOperation(value = "根据任务状态查询学习任务")
    @ApiImplicitParam(name = "type", value = "任务状态 1-进行中 9-已结束", paramType = "path", dataType = "Long", required = true)
    public ResultData getMyTasks(@NotNull Integer type, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        String sql = "select t.*,m.is_complete,m.p_complete,m.time_complete from learning_tasks t,learning_my_tasks m where t.id=m.learning_tasks_id and t.is_deleted=0 and t.status=? and m.user_id=? order by t.id desc";
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, type, user_id);
        return ResultData.ok().data("myTasks", maps);
    }


    @GetMapping("/getMyCourse")
    @ApiOperation(value = "根据学习任务ID获取课程")
    @ApiImplicitParam(name = "tasksId", value = "学习任务ID", paramType = "path", dataType = "Long", required = true)
    public ResultData getMyCourse(@NotNull Integer tasksId, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        String sql = "select c.*,CONCAT('"+imageAbspath+"',cover) as rcover,mc.is_complete,mc.p_complete,mc.time_complete from learning_my_course mc,course c where mc.course_id=c.id and c.is_deleted=0 and mc.user_id=? and mc.learning_tasks_id=?";
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, user_id, tasksId);
        return ResultData.ok().data("myCourse", maps);
    }

    @GetMapping("/getMyCourseWare")
    @ApiOperation(value = "根据学习任务ID,课程ID获取课件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tasksId", value = "学习任务ID", paramType = "path", dataType = "Long", required = true),
            @ApiImplicitParam(name = "courseId", value = "课程ID", paramType = "path", dataType = "Long", required = true)
    })
    public ResultData getMyCourseWare(@NotNull Integer tasksId, @NotNull Integer courseId, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        String sql = "select c.*,CONCAT('"+videoAbspath+"',source_src) as rsource_src,mc.is_complete,mc.p_complete,mc.m_complete,mc.time_complete from learning_my_courseware mc,courseware c where mc.courseware_id=c.id and c.is_deleted=0 and mc.user_id=? and mc.learning_tasks_id=? and mc.course_id=? order by c.sort";
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, user_id, tasksId, courseId);
        return ResultData.ok().data("myCourseWare", maps);
    }


    @PassToken
    @GetMapping("/videoPreview")
    @ApiOperation(value = "根据课件ID播放")
    @ApiImplicitParam(name = "courseWareId", value = "课件ID", paramType = "path", dataType = "Long", required = true)
    public void videoPreview(@NotNull Integer courseWareId,HttpServletRequest request, HttpServletResponse response) throws Exception {
        final Map<String, Object> courseWareMap = jdbcTemplate.queryForMap("SELECT t.course_id,t.source_src FROM courseware t where id=?", courseWareId);
        String file = courseWareMap.get("source_src").toString();
        int course_id = Integer.parseInt(courseWareMap.get("course_id").toString());
        jdbcTemplate.update("update course set see_count=see_count+1 where id = ?",course_id);
        String realPath = videoRealpath + file;
        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }

            try {
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            }catch (Exception e){

            }

        } else {
            ResponseUtil.out(response,ResultData.error().message("视频文件不存在"));
        }
    }

    @PostMapping("/upStudy")
    @ApiOperation(value = "提交学习记录")
    @Transactional
    public ResultData upStudy(@Validated @RequestBody LearningMyCourseware learningMyCourseware, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        String user_name = request.getAttribute("user_name").toString();
        int learningTasksId = learningMyCourseware.getLearningTasksId();
        //判断任务状态
        LearningTasks tasks= learningTasksMapper.selectByPrimaryKey(learningTasksId);
        if(tasks.getStatus()!=1){
            return ResultData.error().message("学习任务已结束");
        }
        Date d1 = tasks.getBeginTime();
        Date d2 = tasks.getEndTime();
        Date d3 = new Date();
        if(DateUtil.between(d2,d3, DateUnit.MS,false)>0){
            return ResultData.error().message("学习任务已到期");
        }
        if(DateUtil.between(d3,d1, DateUnit.MS,false)>0){
            return ResultData.error().message("学习任务未开始");
        }

        int courseId = learningMyCourseware.getCourseId();
        int coursewareId = learningMyCourseware.getCoursewareId();
        int mComplete = learningMyCourseware.getmComplete();
        int rComplete = learningMyCourseware.getrComplete();
        int partNum = jdbcTemplate.queryForObject("select part_num from courseware where id = ?",Integer.class,coursewareId);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        numberFormat.setGroupingUsed(false);
        int pComplete =Integer.parseInt(numberFormat.format((float) mComplete / (float) partNum * 100));
        String coursewareSql = "";
        if(learningMyCourseware.getIsComplete()==1){ //本课件学完了
            //更新我的课件表
            coursewareSql = "update learning_my_courseware set user_name=?,p_complete=?,m_complete=?,r_complete = r_complete + ?,is_complete=?,time_complete=? where user_id=? and learning_tasks_id=? and course_id=? and courseware_id=? and is_complete=?";
            int sl = jdbcTemplate.update(coursewareSql,user_name,100,mComplete,rComplete,1,new Date(),user_id,learningTasksId,courseId,coursewareId,0);
            if(sl>0){//更新我的课程表
                String sql0="select count(id) from learning_my_courseware where user_id=? and learning_tasks_id=? and course_id=? and is_complete=0";
                String sql1="select count(id) from learning_my_courseware where user_id=? and learning_tasks_id=? and course_id=? and is_complete=1";
                int count0 = jdbcTemplate.queryForObject(sql0,Integer.class,user_id,learningTasksId,courseId);
                int count1 = jdbcTemplate.queryForObject(sql1,Integer.class,user_id,learningTasksId,courseId);
                int countComplete =Integer.parseInt(numberFormat.format((float) count1 / (float) (count1+count0) * 100));
                int courseComplete = countComplete==100?1:0;
                Date date = countComplete==100?new Date():null;
                String coursesql = "update learning_my_course set user_name=?,p_complete=?,is_complete=?,time_complete=? where user_id=? and learning_tasks_id=? and course_id=? and is_complete=0";
                int coursesl = jdbcTemplate.update(coursesql,user_name,countComplete,courseComplete,date,user_id,learningTasksId,courseId);
                if(coursesl>0){//更新我的学习任务表
                    sql0="select count(id) from learning_my_course where user_id=? and learning_tasks_id=? and is_complete=0";
                    sql1="select count(id) from learning_my_course where user_id=? and learning_tasks_id=? and is_complete=1";
                    count0 = jdbcTemplate.queryForObject(sql0,Integer.class,user_id,learningTasksId);
                    count1 = jdbcTemplate.queryForObject(sql1,Integer.class,user_id,learningTasksId);
                    countComplete =Integer.parseInt(numberFormat.format((float) count1 / (float) (count1+count0) * 100));
                    courseComplete = countComplete==100?1:0;
                    date = countComplete==100?date:null;
                    coursesql = "update learning_my_tasks set user_name=?,p_complete=?,is_complete=?,time_complete=? where user_id=? and learning_tasks_id=? and is_complete=0";
                    jdbcTemplate.update(coursesql,user_name,countComplete,courseComplete,date,user_id,learningTasksId);
                }
            }
        }else{
            coursewareSql = "update learning_my_courseware set user_name=?,p_complete=?,m_complete=?,r_complete = r_complete + ? where user_id=? and learning_tasks_id=? and course_id=? and courseware_id=? and is_complete=?";
            jdbcTemplate.update(coursewareSql,user_name,pComplete>=100?99:pComplete,mComplete,rComplete,user_id,learningTasksId,courseId,coursewareId,0);
        }
         return ResultData.ok();
    }




}
