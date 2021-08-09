package com.cdzq.study.entity;

import javax.persistence.*;

@Table(name = "exam_questions")
public class ExamQuestions {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 考试任务ID
     */
    @Column(name = "exam_tasks_id")
    private Integer examTasksId;

    /**
     * 1-单选 2-多选 3-判断 4-填空
     */
    @Column(name = "question_type")
    private Byte questionType;

    /**
     * 试题ID
     */
    @Column(name = "question_id")
    private Integer questionId;

    /**
     * 试题分数
     */
    @Column(name = "question_score")
    private Float questionScore;

    /**
     * 排序字段
     */
    @Column(name = "sort")
    private Integer sort;

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
     * 获取考试任务ID
     *
     * @return exam_tasks_id - 考试任务ID
     */
    public Integer getExamTasksId() {
        return examTasksId;
    }

    /**
     * 设置考试任务ID
     *
     * @param examTasksId 考试任务ID
     */
    public void setExamTasksId(Integer examTasksId) {
        this.examTasksId = examTasksId;
    }

    /**
     * 获取1-单选 2-多选 3-判断 4-填空
     *
     * @return question_type - 1-单选 2-多选 3-判断 4-填空
     */
    public Byte getQuestionType() {
        return questionType;
    }

    /**
     * 设置1-单选 2-多选 3-判断 4-填空
     *
     * @param questionType 1-单选 2-多选 3-判断 4-填空
     */
    public void setQuestionType(Byte questionType) {
        this.questionType = questionType;
    }

    /**
     * 获取试题ID
     *
     * @return question_id - 试题ID
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 设置试题ID
     *
     * @param questionId 试题ID
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取试题分数
     *
     * @return question_score - 试题分数
     */
    public Float getQuestionScore() {
        return questionScore;
    }

    /**
     * 设置试题分数
     *
     * @param questionScore 试题分数
     */
    public void setQuestionScore(Float questionScore) {
        this.questionScore = questionScore;
    }

    /**
     * 获取排序字段
     *
     * @return sort - 排序字段
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序字段
     *
     * @param sort 排序字段
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}