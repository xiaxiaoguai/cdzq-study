package com.cdzq.study.controller;

import cn.hutool.core.util.StrUtil;
import com.cdzq.study.base.ResultData;
import com.cdzq.study.entity.*;
import com.cdzq.study.mapper.*;
import com.cdzq.study.security.PassToken;
import com.cdzq.study.util.ExampleUtil;
import com.cdzq.study.util.JqueryUiJson;
import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/examhome")
@RequiredArgsConstructor
@Api(tags = "考试设置模块")
public class ExamHomeController {
    private final JdbcTemplate jdbcTemplate;
    private final SqlMapper sqlMapper;

    private final ExamTasksMapper examTasksMapper;
    private final ExamUsersMapper examUsersMapper;
    private final ExamQuestionsMapper examQuestionsMapper;

    private final ExamMyTasksMapper examMyTasksMapper;
    private final ExamMyTasksReplyMapper examMyTasksReplyMapper;
    private final ExamMyTasksReplyAnswerMapper examMyTasksReplyAnswerMapper;

    @ApiOperation(value = "试题列表")
    @PostMapping("questionsList")
    @PassToken
    public JqueryUiJson questionsList(HttpServletRequest request) throws UnsupportedEncodingException {
        String where = " where 1=1";
        String sql = "select * from (\n" +
                "SELECT DATE_FORMAT(insert_time,'%Y-%m-%d %H:%i:%s') as insert_time,CONCAT('o',q.id) as id,q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'单选题' as leixing,q.use_count from questions_one q left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
                "SELECT DATE_FORMAT(insert_time,'%Y-%m-%d %H:%i:%s') as insert_time,CONCAT('m',q.id) as id,q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'多选题' as leixing,q.use_count from questions_more q left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
                "SELECT DATE_FORMAT(insert_time,'%Y-%m-%d %H:%i:%s') as insert_time,CONCAT('j',q.id) as id,q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'判断题' as leixing,q.use_count from questions_judgment q left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
                "SELECT DATE_FORMAT(insert_time,'%Y-%m-%d %H:%i:%s') as insert_time,CONCAT('b',q.id) as id,q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'填空题' as leixing,q.use_count from questions_blanks q left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 ) as biao";
        String[] _data = java.net.URLDecoder.decode(request.getParameter("data"), "UTF-8").split("&");
        Map<String, String> m = new HashMap<>();
        for (int i = 0; i < _data.length; i++) {
            String[] _tmp = _data[i].split("=");
            if (_tmp.length > 1)
                m.put(_tmp[0], _tmp[1]);
        }

        if (m.get("title") != null && !m.get("title").equals(""))
            where += " and biao.title like '%" + m.get("title") + "%'";
        if (m.get("courses") != null && !m.get("courses").equals(""))
            where += " and biao.course_id = " + m.get("courses");
        if (m.get("qtype") != null && !m.get("qtype").equals(""))
            where += " and biao.qtype_id = " + m.get("qtype");
        if (m.get("leixing") != null && !m.get("leixing").equals(""))
            where += " and biao.leixing = '" + m.get("leixing") + "'";


        // 分页
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request));
        // 查询
        List<Map<String, Object>> filelist = sqlMapper.selectList(sql + where + " order by insert_time desc");
        //List<Map<String, Object>> filelist = examSelectMapper.selectQuestions(m.get("title"),m.get("courses"),m.get("qtype"));
        // 返回结果
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(filelist).getTotal(), filelist);

        return jqueryUiJson;
    }

    @ApiOperation(value = "获取全部课程")
    @PostMapping("getCourses")
    @PassToken
    public ResultData getCourses() {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT id,title FROM course where is_deleted=0 order by subject_id");
        return ResultData.ok().data("courses", maps);
    }

    @ApiOperation(value = "获取全部试题类型")
    @PostMapping("getQtype")
    @PassToken
    public ResultData getQtype() {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT id,title FROM qtype where is_deleted=0 order by sort");
        return ResultData.ok().data("qtype", maps);
    }

    @ApiOperation(value = "根据名称获取课程ID")
    @PostMapping("getCourseByName")
    @PassToken
    public ResultData getCourseByName(String title) throws UnsupportedEncodingException {
        title = java.net.URLDecoder.decode(title, "UTF-8");
        final Map<String, Object> stringObjectMap = sqlMapper.selectOne("SELECT id FROM course where title=#{title} limit 1", title);
        return ResultData.ok().data("id", stringObjectMap==null?null:stringObjectMap.get("id"));
    }

    @ApiOperation(value = "根据名称获取试题类型ID")
    @PostMapping("getQtypeByName")
    @PassToken
    public ResultData getQtypeByName(String title) throws UnsupportedEncodingException {
        title = java.net.URLDecoder.decode(title, "UTF-8");
        final Map<String, Object> stringObjectMap = sqlMapper.selectOne("SELECT id FROM qtype where title=#{title} limit 1",title);
        return ResultData.ok().data("id", stringObjectMap==null?null:stringObjectMap.get("id"));
    }

    @ApiOperation(value = "试题增加")
    @PostMapping("addQuestion")
    @PassToken
    public ResultData addQuestion(String leixing, Integer courses, Integer qtype, String title, String answer, String questiona, String questionb, String questionc, String questiond, String questione, String questionf) {
        String sql = "";
        if (leixing.equals("单选题")) {
            sql = "insert into questions_one (course_id,qtype_id,insert_time,title,answer,optiona,optionb,optionc,optiond,optione,optionf) values (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer,questiona,questionb,questionc,questiond,questione,questionf);
        } else if (leixing.equals("多选题")) {
            sql = "insert into questions_more (course_id,qtype_id,insert_time,title,answer,optiona,optionb,optionc,optiond,optione,optionf) values (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer,questiona,questionb,questionc,questiond,questione,questionf);
        } else if (leixing.equals("判断题")) {
            sql = "insert into questions_judgment (course_id,qtype_id,insert_time,title,answer) values (?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer);
        }else if (leixing.equals("填空题")) {
            int count = 0;
            if(StrUtil.isNotBlank(questiona)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionb)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionc)){
                count ++;
            }
            if(StrUtil.isNotBlank(questiond)){
                count ++;
            }
            if(StrUtil.isNotBlank(questione)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionf)){
                count ++;
            }
            sql = "insert into questions_blanks (course_id,qtype_id,insert_time,title,answer_count,answera,answerb,answerc,answerd,answere,answerf) values (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,count,questiona,questionb,questionc,questiond,questione,questionf);
        }else{
            return ResultData.error().message("非法的类型：" + leixing);
        }
        return ResultData.ok();
    }

    @ApiOperation(value = "试题导入")
    @PostMapping("addmQuestion")
    @PassToken
    public ResultData addmQuestion(String leixing, Integer courses, Integer qtype, String title, String answer, String questiona, String questionb, String questionc, String questiond, String questione, String questionf) throws UnsupportedEncodingException {
        leixing = java.net.URLDecoder.decode(leixing, "UTF-8");
        title = java.net.URLDecoder.decode(title, "UTF-8");
        questiona = java.net.URLDecoder.decode(questiona, "UTF-8");
        questionb = java.net.URLDecoder.decode(questionb, "UTF-8");
        questionc = java.net.URLDecoder.decode(questionc, "UTF-8");
        questiond = java.net.URLDecoder.decode(questiond, "UTF-8");
        questione = java.net.URLDecoder.decode(questione, "UTF-8");
        questionf = java.net.URLDecoder.decode(questionf, "UTF-8");

        String sql = "";
        if (leixing.equals("单选题")) {
            sql = "insert into questions_one (course_id,qtype_id,insert_time,title,answer,optiona,optionb,optionc,optiond,optione,optionf) values (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer==null?null:answer.toLowerCase(),questiona,questionb,questionc,questiond,questione,questionf);
        } else if (leixing.equals("多选题")) {
            sql = "insert into questions_more (course_id,qtype_id,insert_time,title,answer,optiona,optionb,optionc,optiond,optione,optionf) values (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer==null?null:answer.toLowerCase(),questiona,questionb,questionc,questiond,questione,questionf);
        } else if (leixing.equals("判断题")) {
            sql = "insert into questions_judgment (course_id,qtype_id,insert_time,title,answer) values (?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer==null?null:answer.toLowerCase());
        }else if (leixing.equals("填空题")) {
            int count = 0;
            if(StrUtil.isNotBlank(questiona)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionb)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionc)){
                count ++;
            }
            if(StrUtil.isNotBlank(questiond)){
                count ++;
            }
            if(StrUtil.isNotBlank(questione)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionf)){
                count ++;
            }
            sql = "insert into questions_blanks (course_id,qtype_id,insert_time,title,answer_count,answera,answerb,answerc,answerd,answere,answerf) values (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,count,questiona,questionb,questionc,questiond,questione,questionf);
        }else{
            return ResultData.error().message("非法的类型：" + leixing);
        }
        return ResultData.ok();
    }

    @ApiOperation(value = "试题修改")
    @PostMapping("updateQuestion")
    @PassToken
    public ResultData updateQuestion(String id,Integer courses, Integer qtype, String title, String answer, String questiona, String questionb, String questionc, String questiond, String questione, String questionf) {
        String biaozhi=id.substring(0,1);
        Integer realId= Integer.valueOf(id.substring(1));
        String sql="";
        if (biaozhi.equals("o")) {
            sql = "update questions_one set course_id=?,qtype_id=?,insert_time=?,title=?,answer=?,optiona=?,optionb=?,optionc=?,optiond=?,optione=?,optionf=? where id = ?";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer,questiona,questionb,questionc,questiond,questione,questionf,realId);
        } else if (biaozhi.equals("m")) {
            sql = "update questions_more set course_id=?,qtype_id=?,insert_time=?,title=?,answer=?,optiona=?,optionb=?,optionc=?,optiond=?,optione=?,optionf=? where id = ?";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer,questiona,questionb,questionc,questiond,questione,questionf,realId);
        } else if (biaozhi.equals("j")) {
            sql = "update questions_judgment set course_id=?,qtype_id=?,insert_time=?,title=?,answer=? where id = ?";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,answer,realId);
        }else if (biaozhi.equals("b")) {
            int count = 0;
            if(StrUtil.isNotBlank(questiona)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionb)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionc)){
                count ++;
            }
            if(StrUtil.isNotBlank(questiond)){
                count ++;
            }
            if(StrUtil.isNotBlank(questione)){
                count ++;
            }
            if(StrUtil.isNotBlank(questionf)){
                count ++;
            }
            sql = "update questions_blanks set course_id=?,qtype_id=?,insert_time=?,title=?,answer_count=?,answera=?,answerb=?,answerc=?,answerd=?,answere=?,answerf=? where id = ?";
            jdbcTemplate.update(sql,courses,qtype,new Date(),title,count,questiona,questionb,questionc,questiond,questione,questionf,realId);
        }
        return ResultData.ok();
    }

    @ApiOperation(value = "试题删除")
    @PostMapping("deleteQuestion")
    @PassToken
    public ResultData deleteQuestion(String id) {
        String biaozhi=id.substring(0,1);
        Integer realId= Integer.valueOf(id.substring(1));
        String table="";
        if(biaozhi.equals("o")){
            table="questions_one";
        }else if(biaozhi.equals("m")){
            table="questions_more";
        }else if(biaozhi.equals("b")){
            table="questions_blanks";
        }else if(biaozhi.equals("j")){
            table="questions_judgment";
        }
        String sql="update " + table + " set is_deleted = ? where id = ?";
        jdbcTemplate.update(sql,1,realId);
        return ResultData.ok();
    }

    @ApiOperation(value = "根据ID查询试题")
    @PostMapping("getQuestion")
    @PassToken
    public ResultData getQuestion (String questionid) {
        String biaozhi=questionid.substring(0,1);
        Integer realId= Integer.valueOf(questionid.substring(1));
        String table="";
        if(biaozhi.equals("o")){
            table="questions_one";
        }else if(biaozhi.equals("m")){
            table="questions_more";
        }else if(biaozhi.equals("b")){
            table="questions_blanks";
        }else if(biaozhi.equals("j")){
            table="questions_judgment";
        }
        final Map<String, Object> question = jdbcTemplate.queryForMap("select * from " + table + " where id = ?", realId);
        return ResultData.ok().data("question",question);
    }

    @ApiOperation(value = "考试任务列表")
    @PostMapping("examTasksList")
    @PassToken
    public JqueryUiJson examTasksList(HttpServletRequest request) {
        Example example = ExampleUtil.getExample(ExamTasks.class, request);
        PageHelper.startPage(ExampleUtil.getPage(request), ExampleUtil.getRows(request),"id desc");
        List<ExamTasks> tasksList = examTasksMapper.selectByExample(example);
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(tasksList).getTotal(), tasksList);
        return jqueryUiJson;
    }

    @ApiOperation(value = "考试任务增加")
    @PostMapping("addExamTasks")
    @PassToken
    @Transactional
    public ResultData addExamTasks(@Validated ExamTasks examTasks) {
        examTasks.setCreateTime(new Date());
        examTasks.setStatus((byte) 0);
        examTasks.setIsDeleted((byte) 0);
        examTasksMapper.insert(examTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "考试任务修改")
    @PostMapping("updateExamTasks")
    @PassToken
    @Transactional
    public ResultData updateExamTasks(@Validated ExamTasks examTasks) {
        examTasksMapper.updateByPrimaryKeySelective(examTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "考试任务删除")
    @PostMapping("deleteExamTasks")
    @PassToken
    @Transactional
    public ResultData deleteExamTasks(Integer id) {
        ExamTasks examTasks = examTasksMapper.selectByPrimaryKey(id);
        examTasks.setIsDeleted((byte) 1);
        examTasksMapper.updateByPrimaryKeySelective(examTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "考试任务发布")
    @PostMapping("fbExamTasks")
    @PassToken
    @Transactional
    public ResultData fbExamTasks(Integer id) {
        final int usercount = jdbcTemplate.queryForObject("select count(*) as sl from exam_users where exam_tasks_id=?", Integer.class, id);
        if (usercount == 0) {
            return ResultData.error().message("还没有维护人员范围，不能发布");
        }
        final int questioncount = jdbcTemplate.queryForObject("select count(*) as sl from exam_questions where exam_tasks_id=?", Integer.class, id);
        if (questioncount == 0) {
            return ResultData.error().message("还没有维护试卷，不能发布");
        }
        final float question_score = jdbcTemplate.queryForObject("SELECT sum(question_score) as question_score FROM exam_questions where exam_tasks_id=?", Float.class, id);

        //更改发布状态
        ExamTasks examTasks = examTasksMapper.selectByPrimaryKey(id);
        examTasks.setStatus((byte) 1);
        examTasks.setPublishTime(new Date());
        examTasks.setTotalScore(question_score);
        examTasksMapper.updateByPrimaryKeySelective(examTasks);

        return ResultData.ok();
    }

    @ApiOperation(value = "考试任务结束")
    @PostMapping("endExamTasks")
    @PassToken
    @Transactional
    public ResultData endExamTasks(Integer id) {
        ExamTasks examTasks = examTasksMapper.selectByPrimaryKey(id);
        examTasks.setStatus((byte) 9);
        examTasks.setCloseTime(new Date());
        examTasksMapper.updateByPrimaryKeySelective(examTasks);
        return ResultData.ok();
    }

    @ApiOperation(value = "获取已经插入的人员")
    @PostMapping("getUsers")
    @PassToken
    public ResultData getUsers(Integer tasksId) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select user_id from exam_users where exam_tasks_id=?", tasksId);
        return ResultData.ok().data("userIds", maps);
    }

    @ApiOperation(value = "插入人员")
    @PostMapping("insertUsers")
    @PassToken
    @Transactional
    public ResultData insertUsers(Integer tasksId, String ids) {
        //先删除
        jdbcTemplate.update("delete from exam_my_tasks where exam_tasks_id=?", tasksId);
        jdbcTemplate.update("delete from exam_users where exam_tasks_id=?", tasksId);
        //在插入
        String[] _id = ids.split(",");
        for (int i = 0; i < _id.length; i++) {
            //插入考试与用户对应关系表
            ExamUsers eUsers = new ExamUsers();
            eUsers.setUserId(_id[i]);
            eUsers.setExamTasksId(tasksId);
            examUsersMapper.insert(eUsers);
            //插入我的考试任务表
            ExamMyTasks myTasks = new ExamMyTasks();
            myTasks.setExamTasksId(tasksId);
            myTasks.setUserId(_id[i]);
            myTasks.setIsQualified((byte) 0);
            examMyTasksMapper.insert(myTasks);
        }
        return ResultData.ok();
    }
    @ApiOperation(value = "完成情况统计")
    @PostMapping("tjWc")
    @PassToken
    public ResultData tjWc(Integer id) {
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT user_name,case is_qualified when 1 then '合格' when 2 then '优秀' else '不合格' end as is_qualified,score,DATE_FORMAT(time_qualified,'%Y-%m-%d %H:%i:%s') as time_qualified FROM exam_my_tasks where exam_tasks_id=?", id);
        return ResultData.ok().data("tjwc", maps);
    }

    @ApiOperation(value = "试卷列表")
    @PostMapping("paperList")
    @PassToken
    public JqueryUiJson paperList(HttpServletRequest request) throws UnsupportedEncodingException {
        String[] _data = java.net.URLDecoder.decode(request.getParameter("data"), "UTF-8").split("&");
        Map<String, String> m = new HashMap<>();
        for (int i = 0; i < _data.length; i++) {
            String[] _tmp = _data[i].split("=");
            if (_tmp.length > 1)
                m.put(_tmp[0], _tmp[1]);
        }
        String where = " where 1=1";
        if (m.get("title") != null && !m.get("title").equals(""))
            where += " and biao.title like '%" + m.get("title") + "%'";
        if (m.get("courses") != null && !m.get("courses").equals(""))
            where += " and biao.course_id = " + m.get("courses");
        if (m.get("qtype") != null && !m.get("qtype").equals(""))
            where += " and biao.qtype_id = " + m.get("qtype");
        if (m.get("leixing") != null && !m.get("leixing").equals(""))
            where += " and biao.leixing = '" + m.get("leixing") + "'";

        String sql = "select * from (\n" +
                "SELECT q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'单选题' as leixing,e.question_score,e.id,e.sort,e.question_type from questions_one q inner join (SELECT id,question_id,question_score,question_type,sort FROM exam_questions where question_type=1 and exam_tasks_id="+m.get("examTasksId")+") as e on q.id=e.question_id left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
                "SELECT q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'多选题' as leixing,e.question_score,e.id,e.sort,e.question_type from questions_more q inner join (SELECT id,question_id,question_score,question_type,sort FROM exam_questions where question_type=2 and exam_tasks_id="+m.get("examTasksId")+") as e on q.id=e.question_id left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
                "SELECT q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'判断题' as leixing,e.question_score,e.id,e.sort,e.question_type from questions_judgment q inner join (SELECT id,question_id,question_score,question_type,sort FROM exam_questions where question_type=3 and exam_tasks_id="+m.get("examTasksId")+") as e on q.id=e.question_id left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
                "SELECT q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'填空题' as leixing,e.question_score,e.id,e.sort,e.question_type from questions_blanks q inner join (SELECT id,question_id,question_score,question_type,sort FROM exam_questions where question_type=4 and exam_tasks_id="+m.get("examTasksId")+") as e on q.id=e.question_id left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 ) as biao\n";
        // 分页
        int rows = 1000;
        if (StrUtil.isNotBlank(request.getParameter("rows"))) {
            rows = Integer.parseInt(request.getParameter("rows"));
        }
        PageHelper.startPage(ExampleUtil.getPage(request), rows);
        // 查询
        List<Map<String, Object>> filelist = sqlMapper.selectList(sql + where + " order by question_type,sort");
        //List<Map<String, Object>> filelist = examSelectMapper.selectQuestions(m.get("title"),m.get("courses"),m.get("qtype"));
        // 返回结果
        JqueryUiJson jqueryUiJson = new JqueryUiJson(ExampleUtil.getPageInfo(filelist).getTotal(), filelist);

        return jqueryUiJson;
    }

    @ApiOperation(value = "删除试卷里的试题")
    @PostMapping("deleteExamQuestions")
    @PassToken
    public ResultData deleteExamQuestions(Integer id) {
        jdbcTemplate.update("delete from exam_questions where id = ?",id);
        return ResultData.ok();
    }

    @ApiOperation(value = "添加试卷里的试题")
    @PostMapping("addExamQuestions")
    @PassToken
    @Transactional
    public ResultData addExamQuestions(String ids,Float fenzhi,Integer tasksId) {
        for (String questionid : ids.split(",")) {
            String biaozhi=questionid.substring(0,1);
            int realId= Integer.parseInt(questionid.substring(1));
            int realType=0;
            String table = "";
            if(biaozhi.equals("o")){
                realType=1;
                table = "questions_one";
            }else if(biaozhi.equals("m")){
                realType=2;
                table = "questions_more";
            }else if(biaozhi.equals("j")){
                realType=3;
                table = "questions_judgment";
            }else if(biaozhi.equals("b")){
                realType=4;
                table = "questions_blanks";
            }else{
                return ResultData.error().message(questionid + ":非法的questionid");
            }
            ExamQuestions examQuestions=new ExamQuestions();
            examQuestions.setExamTasksId(tasksId);
            examQuestions.setQuestionId(realId);
            examQuestions.setQuestionType((byte) realType);
            final int selectCount = examQuestionsMapper.selectCount(examQuestions);
            if(selectCount == 0){
                examQuestions.setQuestionScore(fenzhi);
                final int sort = jdbcTemplate.queryForObject("select IFNULL(max(sort),0)+1 from exam_questions where exam_tasks_id=? and question_type=?", Integer.class,tasksId,realType);
                examQuestions.setSort(sort);
                examQuestionsMapper.insert(examQuestions);
                jdbcTemplate.update("update " + table +" set use_count=use_count+1 where id = ?",realId);
            }
        }
        return ResultData.ok();
    }

    @ApiOperation(value = "修改试卷里的试题")
    @PostMapping("updateExamQuestions")
    @PassToken
    public ResultData updateExamQuestions(Integer id,Float question_score,Integer sort) {
        jdbcTemplate.update("update exam_questions set question_score=?,sort=? where id=?",question_score,sort,id);
        return ResultData.ok();
    }

    @ApiOperation(value = "添加试题类型")
    @PostMapping("addType")
    @PassToken
    public ResultData addType(String title,Integer sort) {
        jdbcTemplate.update("insert into qtype (title,sort) value (?,?,?)",title,sort);
        return ResultData.ok();
    }

    @ApiOperation(value = "每隔15分钟，处理一次过期任务")
    @Scheduled(cron = "0 */15 * * * ?")
    public void modLearningTasks() {
        jdbcTemplate.update("update exam_tasks t set t.close_time=SYSDATE(),t.status=9 where SYSDATE()>end_time and t.status=1");
    }
}
