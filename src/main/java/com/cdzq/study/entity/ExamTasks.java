package com.cdzq.study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "exam_tasks")
public class ExamTasks {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 考试任务名称
     */
    @NotNull(message = "名称不能为空")
    @Column(name = "title")
    private String title;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 考试开始时间
     */
    @NotNull(message = "考试开始时间不能为空")
    @Column(name = "begin_time")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date beginTime;

    /**
     * 考试结束时间
     */
    @NotNull(message = "考试结束时间不能为空")
    @Column(name = "end_time")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date endTime;

    /**
     * 任务关闭时间
     */
    @Column(name = "close_time")
    private Date closeTime;

    /**
     * 0-草稿 1-发布 9-结束
     */
    @Column(name = "status")
    private Byte status;

    /**
     * 答题次数 -1表示无限次
     */
    @NotNull(message = "答题次数不能为空")
    @Column(name = "answercount")
    private Integer answercount;

    /**
     * 答题时长分钟 -1表示无时长
     */
    @NotNull(message = "答题时长不能为空")
    @Column(name = "answertime")
    private Integer answertime;

    /**
     * 总分
     */
    @Column(name = "total_score")
    private Float totalScore;

    /**
     * 合格比例整数
     */
    @NotNull(message = "合格比例不能为空")
    @Column(name = "qualified")
    private Byte qualified;

    /**
     * 优秀比例整数
     */
    @NotNull(message = "优秀比例不能为空")
    @Column(name = "excellent")
    private Byte excellent;

    /**
     * 是否显示成绩
     */
    @NotNull(message = "是否显示成绩不能为空")
    @Column(name = "is_score")
    private Byte isScore;

    /**
     * 是否显示答案
     */
    @NotNull(message = "是否显示答案不能为空")
    @Column(name = "is_answer")
    private Byte isAnswer;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;

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
     * 获取考试任务名称
     *
     * @return title - 考试任务名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置考试任务名称
     *
     * @param title 考试任务名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取发布时间
     *
     * @return publish_time - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取考试开始时间
     *
     * @return begin_time - 考试开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置考试开始时间
     *
     * @param beginTime 考试开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取考试结束时间
     *
     * @return end_time - 考试结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置考试结束时间
     *
     * @param endTime 考试结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取任务关闭时间
     *
     * @return close_time - 任务关闭时间
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * 设置任务关闭时间
     *
     * @param closeTime 任务关闭时间
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * 获取0-草稿 1-发布 9-结束
     *
     * @return status - 0-草稿 1-发布 9-结束
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置0-草稿 1-发布 9-结束
     *
     * @param status 0-草稿 1-发布 9-结束
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取答题次数 -1表示无限次
     *
     * @return answercount - 答题次数 -1表示无限次
     */
    public Integer getAnswercount() {
        return answercount;
    }

    /**
     * 设置答题次数 -1表示无限次
     *
     * @param answercount 答题次数 -1表示无限次
     */
    public void setAnswercount(Integer answercount) {
        this.answercount = answercount;
    }

    /**
     * 获取答题时长分钟 -1表示无时长
     *
     * @return answertime - 答题时长分钟 -1表示无时长
     */
    public Integer getAnswertime() {
        return answertime;
    }

    /**
     * 设置答题时长分钟 -1表示无时长
     *
     * @param answertime 答题时长分钟 -1表示无时长
     */
    public void setAnswertime(Integer answertime) {
        this.answertime = answertime;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 获取合格比例整数
     *
     * @return qualified - 合格比例整数
     */
    public Byte getQualified() {
        return qualified;
    }

    /**
     * 设置合格比例整数
     *
     * @param qualified 合格比例整数
     */
    public void setQualified(Byte qualified) {
        this.qualified = qualified;
    }

    public Byte getExcellent() {
        return excellent;
    }

    public void setExcellent(Byte excellent) {
        this.excellent = excellent;
    }

    /**
     * 获取是否显示成绩
     *
     * @return is_score - 是否显示成绩
     */
    public Byte getIsScore() {
        return isScore;
    }

    /**
     * 设置是否显示成绩
     *
     * @param isScore 是否显示成绩
     */
    public void setIsScore(Byte isScore) {
        this.isScore = isScore;
    }

    /**
     * 获取是否显示答案
     *
     * @return is_answer - 是否显示答案
     */
    public Byte getIsAnswer() {
        return isAnswer;
    }

    /**
     * 设置是否显示答案
     *
     * @param isAnswer 是否显示答案
     */
    public void setIsAnswer(Byte isAnswer) {
        this.isAnswer = isAnswer;
    }

    /**
     * 获取逻辑删除 1（true）已删除， 0（false）未删除
     *
     * @return is_deleted - 逻辑删除 1（true）已删除， 0（false）未删除
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置逻辑删除 1（true）已删除， 0（false）未删除
     *
     * @param isDeleted 逻辑删除 1（true）已删除， 0（false）未删除
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}