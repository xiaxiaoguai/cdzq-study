package com.cdzq.study.entity;

import javax.persistence.*;

@Table(name = "learning_users")
public class LearningUsers {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 学习任务ID
     */
    @Column(name = "learning_tasks_id")
    private Integer learningTasksId;

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
     * 获取学习任务ID
     *
     * @return learning_tasks_id - 学习任务ID
     */
    public Integer getLearningTasksId() {
        return learningTasksId;
    }

    /**
     * 设置学习任务ID
     *
     * @param learningTasksId 学习任务ID
     */
    public void setLearningTasksId(Integer learningTasksId) {
        this.learningTasksId = learningTasksId;
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