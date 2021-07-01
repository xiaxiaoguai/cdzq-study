package com.cdzq.study.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "learning_my_tasks")
public class LearningMyTasks {
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

    @Column(name = "user_name")
    private String userName;

    /**
     * 本学习任务是否完成 0-否 1-是
     */
    @Column(name = "is_complete")
    private Byte isComplete;

    /**
     * 完成百分比整数
     */
    @Column(name = "p_complete")
    private Byte pComplete;

    /**
     * 完成时间
     */
    @Column(name = "time_complete")
    private Date timeComplete;

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
     * 获取本学习任务是否完成 0-否 1-是
     *
     * @return is_complete - 本学习任务是否完成 0-否 1-是
     */
    public Byte getIsComplete() {
        return isComplete;
    }

    /**
     * 设置本学习任务是否完成 0-否 1-是
     *
     * @param isComplete 本学习任务是否完成 0-否 1-是
     */
    public void setIsComplete(Byte isComplete) {
        this.isComplete = isComplete;
    }

    /**
     * 获取完成百分比整数
     *
     * @return p_complete - 完成百分比整数
     */
    public Byte getpComplete() {
        return pComplete;
    }

    /**
     * 设置完成百分比整数
     *
     * @param pComplete 完成百分比整数
     */
    public void setpComplete(Byte pComplete) {
        this.pComplete = pComplete;
    }

    /**
     * 获取完成时间
     *
     * @return time_complete - 完成时间
     */
    public Date getTimeComplete() {
        return timeComplete;
    }

    /**
     * 设置完成时间
     *
     * @param timeComplete 完成时间
     */
    public void setTimeComplete(Date timeComplete) {
        this.timeComplete = timeComplete;
    }
}