package com.cdzq.study.entity;

import javax.persistence.*;

@Table(name = "exam_users")
public class ExamUsers {
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
}