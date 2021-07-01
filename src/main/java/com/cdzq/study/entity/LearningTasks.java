package com.cdzq.study.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Table(name = "learning_tasks")
@ApiModel(value = "com.cdzq.study.entity.LearningTasks",description = "学习任务")
public class LearningTasks {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 学习任务名称
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
     * 学习开始时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 学习结束时间
     */
    @Column(name = "end_time")
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
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;
}