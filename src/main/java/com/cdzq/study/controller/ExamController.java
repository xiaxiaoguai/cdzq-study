package com.cdzq.study.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.cdzq.study.base.ResultData;
import com.cdzq.study.entity.ExamMyTasks;
import com.cdzq.study.entity.ExamMyTasksReply;
import com.cdzq.study.entity.ExamMyTasksReplyAnswer;
import com.cdzq.study.entity.ExamTasks;
import com.cdzq.study.mapper.ExamMyTasksMapper;
import com.cdzq.study.mapper.ExamMyTasksReplyAnswerMapper;
import com.cdzq.study.mapper.ExamMyTasksReplyMapper;
import com.cdzq.study.mapper.ExamTasksMapper;
import com.cdzq.study.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
@Api(tags = "考试模块")
public class ExamController {

    private final JdbcTemplate jdbcTemplate;

    private final ExamTasksMapper examTasksMapper;
    private final ExamMyTasksMapper examMyTasksMapper;
    private final ExamMyTasksReplyMapper examMyTasksReplyMapper;
    private final ExamMyTasksReplyAnswerMapper examMyTasksReplyAnswerMapper;

    @GetMapping("/getMyTasks")
    @ApiOperation(value = "根据任务状态查询考试任务")
    @ApiImplicitParam(name = "type", value = "任务状态 1-进行中 9-已结束", paramType = "path", dataType = "Long", required = true)
    public ResultData getMyTasks(@NotNull Integer type, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        String sql = "SELECT m.id,m.exam_tasks_id,case is_qualified when 1 then '合格' when 2 then '优秀' else '不合格' end as is_qualified,m.score,DATE_FORMAT(m.time_qualified,'%Y-%m-%d %H:%i:%s') as time_qualified,t.title,DATE_FORMAT(t.begin_time,'%Y-%m-%d %H:%i:%s') as begin_time,DATE_FORMAT(t.end_time,'%Y-%m-%d %H:%i:%s') as end_time FROM exam_my_tasks m,exam_tasks t where m.exam_tasks_id=t.id and t.is_deleted=0 and t.status=? and m.user_id=? order by t.id desc";
        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, type, user_id);
        return ResultData.ok().data("myTasks", maps);
    }


    @GetMapping("/getMyExam")
    @ApiOperation(value = "根据我的考试任务ID获取考试")
    @ApiImplicitParam(name = "myTasksId", value = "我的考试任务ID", paramType = "path", dataType = "Long", required = true)
    public ResultData getMyCourse(@NotNull Integer myTasksId, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        String sql = "SELECT m.id,m.user_id,m.exam_tasks_id,case is_qualified when 1 then '合格' when 2 then '优秀' else '不合格' end as is_qualified,m.score,DATE_FORMAT(m.time_qualified,'%Y-%m-%d %H:%i:%s') as time_qualified,t.title,DATE_FORMAT(t.begin_time,'%Y-%m-%d %H:%i:%s') as begin_time,DATE_FORMAT(t.end_time,'%Y-%m-%d %H:%i:%s') as end_time,case answercount when -1 then '不限制' else answercount end as answercount,case answertime when -1 then '不限制' else answertime end as answertime,total_score,round(total_score*qualified*0.01) as qualified,round(total_score*excellent*0.01) as excellent FROM exam_my_tasks m,exam_tasks t where m.exam_tasks_id=t.id and m.id=?";
        final List<Map<String, Object>> mapslist = jdbcTemplate.queryForList(sql, myTasksId);
        if (mapslist.size() > 0) {
            Map<String, Object> maps = mapslist.get(0);
            if (!user_id.equals(maps.get("user_id"))) {
                return ResultData.error().message("参数非法！");
            }
            //查询已答次数
            final int usecount = jdbcTemplate.queryForObject("SELECT count(*) as sl FROM exam_my_tasks_reply where my_tasks_id=?", Integer.class, myTasksId);
            maps.put("usecount", usecount);
            return ResultData.ok().data("myExam", maps);
        } else {
            return ResultData.error().message("参数非法！");
        }
    }

    @GetMapping("/getMyQuestions")
    @ApiOperation(value = "根据我的考试任务ID获取试题")
    @ApiImplicitParam(name = "myTasksId", value = "我的考试任务ID", paramType = "path", dataType = "Long", required = true)
    public ResultData getMyQuestions(@NotNull Integer myTasksId, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        final ExamMyTasks myTasks = examMyTasksMapper.selectByPrimaryKey(myTasksId);
        if(myTasks==null || !myTasks.getUserId().equals(user_id)){
            return ResultData.error().message("参数非法！");
        }
        //判断任务状态
        final ExamTasks tasks = examTasksMapper.selectByPrimaryKey(myTasks.getExamTasksId());
        if (tasks.getStatus() != 1) {
            return ResultData.error().message("考试任务已结束");
        }
        Date d1 = tasks.getBeginTime();
        Date d2 = tasks.getEndTime();
        Date d3 = new Date();
        if (DateUtil.between(d2, d3, DateUnit.MS, false) > 0) {
            return ResultData.error().message("考试任务已到期");
        }
        if (DateUtil.between(d3, d1, DateUnit.MS, false) > 0) {
            return ResultData.error().message("考试任务未开始");
        }
        //判断已考次数
        final int answercount = tasks.getAnswercount();
        if(answercount!=-1){
            final int usecount = jdbcTemplate.queryForObject("SELECT count(*) as sl FROM exam_my_tasks_reply where my_tasks_id=?", Integer.class,myTasksId);
            if(usecount>=answercount){
                return ResultData.error().message("考试次数已用完");
            }
        }
        final List<Map<String, Object>> questionsOne = jdbcTemplate.queryForList("SELECT e.*,q.title,q.optiona,q.optionb,q.optionc,q.optiond,q.optione,q.optionf FROM exam_questions e,questions_one q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=? order by e.sort", 1, myTasks.getExamTasksId());
        final List<Map<String, Object>> questionsMore = jdbcTemplate.queryForList("SELECT e.*,q.title,q.optiona,q.optionb,q.optionc,q.optiond,q.optione,q.optionf FROM exam_questions e,questions_more q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=? order by e.sort", 2, myTasks.getExamTasksId());
        final List<Map<String, Object>> questionsJudgment = jdbcTemplate.queryForList("SELECT e.*,q.title FROM exam_questions e,questions_judgment q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=? order by e.sort", 3, myTasks.getExamTasksId());
        final List<Map<String, Object>> questionsBlanks = jdbcTemplate.queryForList("SELECT e.*,q.title,q.answer_count FROM exam_questions e,questions_blanks q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=? order by e.sort", 4, myTasks.getExamTasksId());
        Map<String, Object> map=new HashMap<>();
        map.put("questionsOne",questionsOne);
        map.put("questionsMore",questionsMore);
        map.put("questionsJudgment",questionsJudgment);
        map.put("questionsBlanks",questionsBlanks);
        ExamMyTasksReply examMyTasksReply=new ExamMyTasksReply();
        examMyTasksReply.setMyTasksId(myTasksId);
        examMyTasksReply.setBeginTime(new Date());
        examMyTasksReplyMapper.insert(examMyTasksReply);
        map.put("myTasksReplyId",examMyTasksReply.getId());
        return ResultData.ok().data(map);
    }

    @PostMapping("/upExam")
    @ApiOperation(value = "提交考试结果")
    @Transactional
    public ResultData upExam(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        String user_id = request.getAttribute("user_id").toString();
        String user_name = request.getAttribute("user_name").toString();
        if(data.get("myTasksId")==null){
            return ResultData.error().message("myTasksId参数非法！");
        }
        if(data.get("myTasksReplyId")==null){
            return ResultData.error().message("myTasksReplyId参数非法！");
        }
        int myTasksId = Integer.parseInt(data.get("myTasksId").toString());
        int myTasksReplyId = Integer.parseInt(data.get("myTasksReplyId").toString());
        final ExamMyTasks myTasks = examMyTasksMapper.selectByPrimaryKey(myTasksId);
        if(myTasks==null || !myTasks.getUserId().equals(user_id)){
            return ResultData.error().message("参数非法！");
        }
        final ExamTasks tasks = examTasksMapper.selectByPrimaryKey(myTasks.getExamTasksId());
        //判断已考次数
        final int answercount = tasks.getAnswercount();
        if(answercount!=-1){
            final int usecount = jdbcTemplate.queryForObject("SELECT count(*) as sl FROM exam_my_tasks_reply where my_tasks_id=?", Integer.class,myTasksId);
            if(usecount>=answercount){
                return ResultData.error().message("考试次数已用完");
            }
        }
        float total_score = 0;
        final List<Map<String, Object>> questionsOne = jdbcTemplate.queryForList("SELECT e.id,e.question_id,e.question_type,e.question_score,q.answer FROM exam_questions e,questions_one q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=?", 1, myTasks.getExamTasksId());
        final List<Map<String, Object>> questionsMore = jdbcTemplate.queryForList("SELECT e.id,e.question_id,e.question_type,e.question_score,q.answer FROM exam_questions e,questions_more q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=?", 2, myTasks.getExamTasksId());
        final List<Map<String, Object>> questionsJudgment = jdbcTemplate.queryForList("SELECT e.id,e.question_id,e.question_type,e.question_score,q.answer FROM exam_questions e,questions_judgment q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=?", 3, myTasks.getExamTasksId());
        final List<Map<String, Object>> questionsBlanks = jdbcTemplate.queryForList("SELECT e.id,e.question_id,e.question_type,e.question_score,CONCAT(answera,if(answerb<>'','#cdfg;',''),answerb,if(answerc<>'','#cdfg;',''),answerc,if(answerd<>'','#cdfg;',''),answerd,if(answere<>'','#cdfg;',''),answere,if(answerf<>'','#cdfg;',''),answerf) as answer FROM exam_questions e,questions_blanks q where e.question_id=q.id and e.question_type=? and e.exam_tasks_id=?", 4, myTasks.getExamTasksId());
        questionsOne.addAll(questionsMore);
        questionsOne.addAll(questionsJudgment);
        questionsOne.addAll(questionsBlanks);
        for(Map map: questionsOne){
            int id= (int) map.get("id");
            int question_type= (int) map.get("question_type");
            int question_id= (int) map.get("question_id");
            float question_score= (float) map.get("question_score");
            String answer = (String) map.get("answer");
            String up_answer=data.get("q"+"_"+question_type+"_"+question_id)==null?"":data.get("q"+"_"+question_type+"_"+question_id).toString();
            ExamMyTasksReplyAnswer examMyTasksReplyAnswer=new ExamMyTasksReplyAnswer();
            examMyTasksReplyAnswer.setMyTasksReplyId(myTasksReplyId);
            examMyTasksReplyAnswer.setExamQuestionsId(id);
            examMyTasksReplyAnswer.setAnswer(answer);
            examMyTasksReplyAnswer.setUpAnswer(up_answer);
            if(answer.equals(up_answer)){
                examMyTasksReplyAnswer.setIsCorrect((byte) 1);
                total_score = total_score + question_score;
            }else{
                examMyTasksReplyAnswer.setIsCorrect((byte) 0);
            }
            examMyTasksReplyAnswerMapper.insert(examMyTasksReplyAnswer);
        }
        //插入回复主表
        ExamMyTasksReply examMyTasksReply=examMyTasksReplyMapper.selectByPrimaryKey(myTasksReplyId);
        examMyTasksReply.setEndTime(new Date());
        examMyTasksReply.setIp(IpUtils.getIp(request));
        examMyTasksReply.setScore(total_score);
        examMyTasksReplyMapper.updateByPrimaryKeySelective(examMyTasksReply);
        //判断成绩是否合格
        Map<String,Object> result=new HashMap<>();
        if(total_score>=tasks.getExcellent()) {
            myTasks.setIsQualified((byte) 2);
            result.put("qualified","优秀");
        }else if(total_score>=tasks.getQualified()){
            myTasks.setIsQualified((byte) 1);
            result.put("qualified","合格");
        }else{
            myTasks.setIsQualified((byte) 0);
            result.put("qualified","不合格");
        }
        //修改任务表
        if(myTasks.getScore()==null || total_score>myTasks.getScore()){
            myTasks.setUserName(user_name);
            myTasks.setScore(total_score);
            myTasks.setTimeQualified(new Date());
            examMyTasksMapper.updateByPrimaryKeySelective(myTasks);
        }
        if(tasks.getIsScore()==1){
            result.put("score",total_score);
        }
        return ResultData.ok().data(result);
    }


}
