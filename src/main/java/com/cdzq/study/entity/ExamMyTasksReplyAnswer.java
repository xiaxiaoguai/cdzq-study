package com.cdzq.study.entity;

import javax.persistence.*;

@Table(name = "exam_my_tasks_reply_answer")
public class ExamMyTasksReplyAnswer {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 我的回答主表ID
     */
    @Column(name = "my_tasks_reply_id")
    private Integer myTasksReplyId;

    /**
     * 考试试题表ID
     */
    @Column(name = "exam_questions_id")
    private Integer examQuestionsId;

    /**
     * 答案
     */
    @Column(name = "answer")
    private String answer;

    /**
     * 答案
     */
    @Column(name = "up_answer")
    private String upAnswer;

    @Column(name = "is_correct")
    private Byte isCorrect;

    /**
     * 获取主键自增ID
     *
     * @return id - 主键自增ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键自增ID
     *
     * @param id 主键自增ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取我的回答主表ID
     *
     * @return my_tasks_reply_id - 我的回答主表ID
     */
    public Integer getMyTasksReplyId() {
        return myTasksReplyId;
    }

    /**
     * 设置我的回答主表ID
     *
     * @param myTasksReplyId 我的回答主表ID
     */
    public void setMyTasksReplyId(Integer myTasksReplyId) {
        this.myTasksReplyId = myTasksReplyId;
    }

    /**
     * 获取考试试题表ID
     *
     * @return exam_questions_id - 考试试题表ID
     */
    public Integer getExamQuestionsId() {
        return examQuestionsId;
    }

    /**
     * 设置考试试题表ID
     *
     * @param examQuestionsId 考试试题表ID
     */
    public void setExamQuestionsId(Integer examQuestionsId) {
        this.examQuestionsId = examQuestionsId;
    }

    /**
     * 获取答案
     *
     * @return answer - 答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置答案
     *
     * @param answer 答案
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取答案
     *
     * @return up_answer - 答案
     */
    public String getUpAnswer() {
        return upAnswer;
    }

    /**
     * 设置答案
     *
     * @param upAnswer 答案
     */
    public void setUpAnswer(String upAnswer) {
        this.upAnswer = upAnswer;
    }

    /**
     * @return is_correct
     */
    public Byte getIsCorrect() {
        return isCorrect;
    }

    /**
     * @param isCorrect
     */
    public void setIsCorrect(Byte isCorrect) {
        this.isCorrect = isCorrect;
    }
}