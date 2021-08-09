package com.cdzq.study.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "exam_my_tasks_reply")
public class ExamMyTasksReply {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 我的考试任务ID
     */
    @Column(name = "my_tasks_id")
    private Integer myTasksId;

    /**
     * 答题开始时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 答题结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 最高分数
     */
    @Column(name = "score")
    private Float score;

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
     * 获取我的考试任务ID
     *
     * @return my_tasks_id - 我的考试任务ID
     */
    public Integer getMyTasksId() {
        return myTasksId;
    }

    /**
     * 设置我的考试任务ID
     *
     * @param myTasksId 我的考试任务ID
     */
    public void setMyTasksId(Integer myTasksId) {
        this.myTasksId = myTasksId;
    }

    /**
     * 获取答题开始时间
     *
     * @return begin_time - 答题开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置答题开始时间
     *
     * @param beginTime 答题开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取答题结束时间
     *
     * @return end_time - 答题结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置答题结束时间
     *
     * @param endTime 答题结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取IP地址
     *
     * @return ip - IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP地址
     *
     * @param ip IP地址
     */
    public void setIp(String ip) {
        this.ip = ip;
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
}