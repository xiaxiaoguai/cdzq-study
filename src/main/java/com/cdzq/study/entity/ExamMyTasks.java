package com.cdzq.study.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "exam_my_tasks")
public class ExamMyTasks {
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
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    /**
     * 是否合格 0-否 1-是
     */
    @Column(name = "is_qualified")
    private Byte isQualified;

    /**
     * 最高分数
     */
    @Column(name = "score")
    private Float score;

    /**
     * 成绩获取时间
     */
    @Column(name = "time_qualified")
    private Date timeQualified;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取是否合格 0-否 1-是
     *
     * @return is_qualified - 是否合格 0-否 1-是
     */
    public Byte getIsQualified() {
        return isQualified;
    }

    /**
     * 设置是否合格 0-否 1-是
     *
     * @param isQualified 是否合格 0-否 1-是
     */
    public void setIsQualified(Byte isQualified) {
        this.isQualified = isQualified;
    }

    /**
     * 获取最高分数
     *
     * @return score - 最高分数
     */
    public Float getScore() {
        return score;
    }

    /**
     * 设置最高分数
     *
     * @param score 最高分数
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     * 获取成绩获取时间
     *
     * @return time_qualified - 成绩获取时间
     */
    public Date getTimeQualified() {
        return timeQualified;
    }

    /**
     * 设置成绩获取时间
     *
     * @param timeQualified 成绩获取时间
     */
    public void setTimeQualified(Date timeQualified) {
        this.timeQualified = timeQualified;
    }
}