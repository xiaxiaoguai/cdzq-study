package com.cdzq.study.controller;

import com.cdzq.study.base.ResultData;
import com.cdzq.study.entity.*;
import com.cdzq.study.exception.BadRequestException;
import com.cdzq.study.mapper.*;
import com.cdzq.study.security.PassToken;
import com.cdzq.study.util.ExampleUtil;
import com.cdzq.study.util.FileUtil;
import com.cdzq.study.util.JqueryUiJson;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studyhome")
@RequiredArgsConstructor
@Api(tags = "学习设置模块")
public class StudyHomeController {

    private final LearningTasksMapper learningTasksMapper;
    private final LearningUsersMapper learningUsersMapper;
    private final LearningCourseMapper learningCourseMapper;

    private final LearningMyTasksMapper learningMyTasksMapper;
    private final LearningMyCourseMapper learningMyCourseMapper;
    private final LearningMyCoursewareMapper learningMyCoursewareMapper;

    private final CourseMapper courseMapper;
    private final CoursewareMapper coursewareMapper;
    private final SubjectMapper subjectMapper;

    private final JdbcTemplate jdbcTemplate;

    @Value("${image.realpath}")
    private String coverPath;
    @Value("${video.realpath}")
    private String videoPath;


    @ApiOperation(value = "课程封面上传")
    @PostMapping("uploadCover")
    @PassToken
    public ResultData uploadCover(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("上传文件为空");
        }
        String fileName = FileUtil.getRandomFileName(file.getOriginalFilename());
        File filePath = new File(coverPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File dest = new File(coverPath + fileName);
        try {
            file.transferTo(dest);
            return ResultData.ok().data("rdname", fileName);
        } catch (IOException e) {
            throw new BadRequestException("upfail");
        }

    }

    @ApiOperation(value = "课件上传")
    @PostMapping("uploadVideo")
    @PassToken
    public ResultData uploadVideo(MultipartFile file, HttpServletRequest request) {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        if (file.isEmpty()) {
            throw new BadRequestException("上传文件为空");
        }
        String fileName = FileUtil.getRandomFileName(file.getOriginalFilename());
        String realpath = videoPath + courseId + "/";
        File filePath = new File(realpath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File dest = new File(realpath + fileName);
        try {
            file.transferTo(dest);
            return ResultData.ok().data("rdname", courseId + "/" + fileName);
        } catch (IOException e) {
            throw new BadRequestException("upfail");
        }

    }

    @ApiOperation(value = "学习任务列表")
    @PostMapping("learningTaskslist")
    @PassToken
    public JqueryUiJson learningTaskslist(HttpServletRequest request) {
        Example example = ExampleUtil.getExample(LearningTasks.class, request);
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request),"id desc");
        List<LearningTasks> tasksList = learningTasksMapper.selectByExample(example);
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(tasksList).getTotal(), tasksList);
        return jqueryUiJson;
    }

    @ApiOperation(value = "学习任务增加")
    @PostMapping("addlearningTasks")
    @PassToken
    @Transactional
    public ResultData addlearningTasks(@Validated LearningTasks learningTasks) {
        learningTasks.setCreateTime(new Date());
        learningTasks.setStatus((byte) 0);
        learningTasks.setIsDeleted((byte) 0);
        learningTasksMapper.insert(learningTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "学习任务修改")
    @PostMapping("updatelearningTasks")
    @PassToken
    @Transactional
    public ResultData updatelearningTasks(@Validated LearningTasks learningTasks) {
        learningTasksMapper.updateByPrimaryKeySelective(learningTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "学习任务删除")
    @PostMapping("deletelearningTasks")
    @PassToken
    @Transactional
    public ResultData deletelearningTasks(Integer id) {
        LearningTasks learningTasks = learningTasksMapper.selectByPrimaryKey(id);
        learningTasks.setIsDeleted((byte) 1);
        learningTasksMapper.updateByPrimaryKeySelective(learningTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "学习任务发布")
    @PostMapping("fbLearningTasks")
    @PassToken
    @Transactional
    public ResultData fbLearningTasks(Integer id) {
        final Integer usercount = jdbcTemplate.queryForObject("select count(*) as sl from learning_users where learning_tasks_id=?", Integer.class, id);
        if (usercount == 0) {
            return ResultData.error().message("还没有维护人员范围，不能发布");
        }
        final List<Map<String, Object>> courselist = jdbcTemplate.queryForList("select * from learning_course where learning_tasks_id=?", id);
        if (courselist.size() == 0) {
            return ResultData.error().message("还没有维护课程范围，不能发布");
        }
        //更改发布状态
        LearningTasks learningTasks = learningTasksMapper.selectByPrimaryKey(id);
        learningTasks.setStatus((byte) 1);
        learningTasks.setPublishTime(new Date());
        learningTasksMapper.updateByPrimaryKeySelective(learningTasks);
        //更改课程使用次数
        for (Map<String, Object> course : courselist) {
            jdbcTemplate.update("update course set use_count=use_count+1 where id=?", Integer.parseInt(course.get("course_id").toString()));
        }
        return ResultData.ok();
    }

    @ApiOperation(value = "学习任务结束")
    @PostMapping("endLearningTasks")
    @PassToken
    @Transactional
    public ResultData endLearningTasks(Integer id) {
        LearningTasks learningTasks = learningTasksMapper.selectByPrimaryKey(id);
        learningTasks.setStatus((byte) 9);
        learningTasks.setCloseTime(new Date());
        learningTasksMapper.updateByPrimaryKeySelective(learningTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "获取已经插入的人员")
    @PostMapping("getUsers")
    @PassToken
    public ResultData getUsers(Integer tasksId) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select user_id from learning_users where learning_tasks_id=?", tasksId);
        return ResultData.ok().data("userIds", maps);
    }

    @ApiOperation(value = "插入人员")
    @PostMapping("insertUsers")
    @PassToken
    @Transactional
    public ResultData insertUsers(Integer tasksId, String ids) {
        //查询课程数据
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from learning_course where learning_tasks_id=?", tasksId);
        if (maps.size() > 0) {
            //先删除三表
            jdbcTemplate.update("delete from learning_my_course where learning_tasks_id=?", tasksId);
            jdbcTemplate.update("delete from learning_my_courseware where learning_tasks_id=?", tasksId);
            jdbcTemplate.update("delete from learning_my_tasks where learning_tasks_id=?", tasksId);
        }
        //先删除关系表
        LearningUsers learningUsers = new LearningUsers();
        learningUsers.setLearningTasksId(tasksId);
        learningUsersMapper.delete(learningUsers);
        //在插入关系表
        String[] _id = ids.split(",");
        for (int i = 0; i < _id.length; i++) {
            LearningUsers lUsers = new LearningUsers();
            lUsers.setUserId(_id[i]);
            lUsers.setLearningTasksId(tasksId);
            learningUsersMapper.insert(lUsers);
            //更新我的任务三表
            if (maps.size() > 0) {
                //插入我的任务表
                LearningMyTasks myTasks = new LearningMyTasks();
                myTasks.setLearningTasksId(tasksId);
                myTasks.setUserId(_id[i]);
                myTasks.setIsComplete((byte) 0);
                myTasks.setpComplete((byte) 0);
                learningMyTasksMapper.insert(myTasks);
                //插入我的课程
                for (Map<String, Object> map : maps) {
                    int course_id = Integer.parseInt(map.get("course_id").toString());
                    LearningMyCourse myCourse = new LearningMyCourse();
                    myCourse.setLearningTasksId(tasksId);
                    myCourse.setUserId(_id[i]);
                    myCourse.setCourseId(course_id);
                    myCourse.setIsComplete((byte) 0);
                    myCourse.setpComplete((byte) 0);
                    learningMyCourseMapper.insert(myCourse);
                    //插入我的课件
                    final List<Map<String, Object>> coursewares = jdbcTemplate.queryForList("select * from courseware where is_deleted=0 and course_id=? order by sort", course_id);
                    for (Map<String, Object> courseware : coursewares) {
                        LearningMyCourseware myCourseware = new LearningMyCourseware();
                        myCourseware.setLearningTasksId(tasksId);
                        myCourseware.setUserId(_id[i]);
                        myCourseware.setCourseId(course_id);
                        myCourseware.setCoursewareId(Integer.parseInt(courseware.get("id").toString()));
                        myCourseware.setIsComplete((byte) 0);
                        myCourseware.setpComplete((byte) 0);
                        myCourseware.setrComplete(0);
                        myCourseware.setmComplete(0);
                        learningMyCoursewareMapper.insert(myCourseware);
                    }
                }
            }
        }


        return ResultData.ok();
    }

    @ApiOperation(value = "获取课程列表")
    @PostMapping("getCourses")
    @PassToken
    public ResultData getCourses() {
        String Sql = "select * from \n" +
                "(SELECT CONCAT('f',id) as id,title,parent_id as fid,0 as isleaf FROM subject where is_deleted=0\n" +
                "union all\n" +
                "SELECT id,title,CONCAT('f',subject_id) as fid,1 as isleaf FROM course where is_deleted=0) as tree";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(Sql);
        return ResultData.ok().data("tree", maps);
    }

    @ApiOperation(value = "获取已经插入的课程列表")
    @PostMapping("getMyCourses")
    @PassToken
    public ResultData getMyCourses(Integer tasksId) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select course_id from learning_course where learning_tasks_id=?", tasksId);
        return ResultData.ok().data("coursesIds", maps);
    }

    @ApiOperation(value = "插入课程")
    @PostMapping("insertCourses")
    @PassToken
    @Transactional
    public ResultData inserCourses(Integer tasksId, String ids) {
        //查询人员数据
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from learning_users where learning_tasks_id=?", tasksId);
        if (maps.size() > 0) {
            //先删除三表
            jdbcTemplate.update("delete from learning_my_course where learning_tasks_id=?", tasksId);
            jdbcTemplate.update("delete from learning_my_courseware where learning_tasks_id=?", tasksId);
            jdbcTemplate.update("delete from learning_my_tasks where learning_tasks_id=?", tasksId);
        }
        for (Map<String, Object> map : maps) {
            //插入我的任务表
            LearningMyTasks myTasks = new LearningMyTasks();
            myTasks.setLearningTasksId(tasksId);
            myTasks.setUserId(map.get("user_id").toString());
            myTasks.setIsComplete((byte) 0);
            myTasks.setpComplete((byte) 0);
            learningMyTasksMapper.insert(myTasks);
        }
        //先删除关系表
        LearningCourse learningCourse = new LearningCourse();
        learningCourse.setLearningTasksId(tasksId);
        learningCourseMapper.delete(learningCourse);
        //在插入关系表
        String[] _id = ids.split(",");
        for (int i = 0; i < _id.length; i++) {
            LearningCourse lCourse = new LearningCourse();
            lCourse.setCourseId(Integer.parseInt(_id[i]));
            lCourse.setLearningTasksId(tasksId);
            learningCourseMapper.insert(lCourse);
            //更新我的任务三表
            if (maps.size() > 0) {
                for (Map<String, Object> map : maps) {
                    //插入我的课程
                    int course_id = Integer.parseInt(_id[i]);
                    LearningMyCourse myCourse = new LearningMyCourse();
                    myCourse.setLearningTasksId(tasksId);
                    myCourse.setUserId(map.get("user_id").toString());
                    myCourse.setCourseId(course_id);
                    myCourse.setIsComplete((byte) 0);
                    myCourse.setpComplete((byte) 0);
                    learningMyCourseMapper.insert(myCourse);
                    //插入我的课件
                    final List<Map<String, Object>> coursewares = jdbcTemplate.queryForList("select * from courseware where is_deleted=0 and course_id=? order by sort", course_id);
                    for (Map<String, Object> courseware : coursewares) {
                        LearningMyCourseware myCourseware = new LearningMyCourseware();
                        myCourseware.setLearningTasksId(tasksId);
                        myCourseware.setUserId(map.get("user_id").toString());
                        myCourseware.setCourseId(course_id);
                        myCourseware.setCoursewareId(Integer.parseInt(courseware.get("id").toString()));
                        myCourseware.setIsComplete((byte) 0);
                        myCourseware.setpComplete((byte) 0);
                        myCourseware.setmComplete(0);
                        learningMyCoursewareMapper.insert(myCourseware);
                    }
                }
            }
        }
        return ResultData.ok();
    }


    @ApiOperation(value = "完成情况统计")
    @PostMapping("tjWc")
    @PassToken
    public ResultData tjWc(Integer id) {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM learning_my_tasks where learning_tasks_id=? order by is_complete desc", id);
        return ResultData.ok().data("tjwc", maps);
    }

    @ApiOperation(value = "获取全部课程分类")
    @PostMapping("getSubject")
    @PassToken
    public ResultData getSubject() {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT id,title FROM subject where is_deleted=0 order by sort");
        return ResultData.ok().data("subject", maps);
    }

    @ApiOperation(value = "获取单个分类")
    @PostMapping("getSubjectByID")
    @PassToken
    public ResultData getSubjectByID(Integer id) {
        return ResultData.ok().data("subject", subjectMapper.selectByPrimaryKey(id));
    }

    @ApiOperation(value = "课程列表")
    @PostMapping("learningCourselist")
    @PassToken
    public JqueryUiJson learningCourselist(HttpServletRequest request) {
        Example example = ExampleUtil.getExample(Course.class, request);
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request));
        List<Course> courseList = courseMapper.selectByExample(example);
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(courseList).getTotal(), courseList);
        return jqueryUiJson;
    }

    @ApiOperation(value = "课程增加")
    @PostMapping("addCourse")
    @PassToken
    @Transactional
    public ResultData addCourse(@Validated Course course) {
        course.setSubjectParentId(0);
        course.setCreateTime(new Date());
        course.setSeeCount(0);
        course.setUseCount(0);
        course.setIsDeleted((byte) 0);
        courseMapper.insert(course);
        return ResultData.ok();
    }

    @ApiOperation(value = "课程修改")
    @PostMapping("updateCourse")
    @PassToken
    @Transactional
    public ResultData updateCourse(@Validated Course course) {
        courseMapper.updateByPrimaryKeySelective(course);
        return ResultData.ok();
    }

    @ApiOperation(value = "课程删除")
    @PostMapping("deleteCourse")
    @PassToken
    @Transactional
    public ResultData deleteCourse(Integer id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        course.setIsDeleted((byte) 1);
        courseMapper.updateByPrimaryKeySelective(course);
        return ResultData.ok();
    }

    @ApiOperation(value = "课件列表")
    @PostMapping("learningCourseWarelist")
    @PassToken
    public JqueryUiJson learningCourseWarelist(HttpServletRequest request) {
        Example example = ExampleUtil.getExample(Courseware.class, request);
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request));
        List<Courseware> coursewareList = coursewareMapper.selectByExample(example);
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(coursewareList).getTotal(), coursewareList);
        return jqueryUiJson;
    }

    @ApiOperation(value = "课件增加")
    @PostMapping("addCourseWare")
    @PassToken
    @Transactional
    public ResultData addCourseWare(@Validated Courseware courseware) {
        courseware.setIsDeleted((byte) 0);
        coursewareMapper.insert(courseware);
        return ResultData.ok();
    }

    @ApiOperation(value = "课件修改")
    @PostMapping("updateCourseWare")
    @PassToken
    @Transactional
    public ResultData updateCourseWare(@Validated Courseware courseware) {
        coursewareMapper.updateByPrimaryKeySelective(courseware);
        return ResultData.ok();
    }

    @ApiOperation(value = "课件删除")
    @PostMapping("deleteCourseWare")
    @PassToken
    @Transactional
    public ResultData deleteCourseWare(Integer id) {
        Courseware courseware = coursewareMapper.selectByPrimaryKey(id);
        courseware.setIsDeleted((byte) 1);
        coursewareMapper.updateByPrimaryKeySelective(courseware);
        return ResultData.ok();
    }

    @ApiOperation(value = "添加课程类型")
    @PostMapping("addType")
    @PassToken
    public ResultData addType(String title,Integer sort) {
        jdbcTemplate.update("insert into subject (title,sort) value (?,?,?)",title,sort);
        return ResultData.ok();
    }

    @ApiOperation(value = "每隔15分钟，处理一次过期任务")
    @Scheduled(cron = "0 */15 * * * ?")
    public void modLearningTasks() {
        jdbcTemplate.update("update learning_tasks t set t.close_time=SYSDATE(),t.status=9 where SYSDATE()>end_time and t.status=1");
    }
}
