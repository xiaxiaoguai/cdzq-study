package com.cdzq.study.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "learning_my_courseware")
public class LearningMyCourseware {
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
    @NotNull(message = "learningTasksId不能为空")
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
     * 课程ID
     */
    @NotNull(message = "courseId不能为空")
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 课件ID
     */
    @NotNull(message = "coursewareId不能为空")
    @Column(name = "courseware_id")
    private Integer coursewareId;

    /**
     * 本课件是否完成 0-否 1-是
     */
    @NotNull(message = "isComplete不能为空")
    @Column(name = "is_complete")
    private Byte isComplete;

    /**
     * 完成百分比整数
     */
    @Column(name = "p_complete")
    private Byte pComplete;

    /**
     * 完成秒
     */
    @NotNull(message = "mComplete不能为空")
    @Column(name = "m_complete")
    private Integer mComplete;

    /**
     * 实际秒
     */
    @NotNull(message = "rComplete不能为空")
    @Column(name = "r_complete")
    private Integer rComplete;

    /**
     * 完成时间
     */
    @Column(name = "time_complete")
    private Date timeComplete;

    public Integer getId() {
        return id;
    }

    public Integer getLearningTasksId() {
        return learningTasksId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getCoursewareId() {
        return coursewareId;
    }

    public Byte getIsComplete() {
        return isComplete;
    }

    public Byte getpComplete() {
        return pComplete;
    }

    public Integer getmComplete() {
        return mComplete;
    }

    public Integer getrComplete() {
        return rComplete;
    }

    public Date getTimeComplete() {
        return timeComplete;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLearningTasksId(Integer learningTasksId) {
        this.learningTasksId = learningTasksId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setCoursewareId(Integer coursewareId) {
        this.coursewareId = coursewareId;
    }

    public void setIsComplete(Byte isComplete) {
        this.isComplete = isComplete;
    }

    public void setpComplete(Byte pComplete) {
        this.pComplete = pComplete;
    }

    public void setmComplete(Integer mComplete) {
        this.mComplete = mComplete;
    }

    public void setrComplete(Integer rComplete) {
        this.rComplete = rComplete;
    }

    public void setTimeComplete(Date timeComplete) {
        this.timeComplete = timeComplete;
    }
}