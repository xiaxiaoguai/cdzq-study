package com.cdzq.study.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "courseware")
public class Courseware {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 章节课件名称
     */
    @NotNull(message = "名称不能为空")
    @Column(name = "title")
    private String title;

    /**
     * 排序字段
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 本节课时
     */
    @Column(name = "part_num")
    private String partNum;

    /**
     * 课件物理地址
     */
    @Column(name = "source_src")
    private String sourceSrc;

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
     * 获取课程ID
     *
     * @return course_id - 课程ID
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 设置课程ID
     *
     * @param courseId 课程ID
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * 获取章节课件名称
     *
     * @return title - 章节课件名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置章节课件名称
     *
     * @param title 章节课件名称
     */
    public void setTitle(String title) {
        this.title = title;
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

    /**
     * 获取本节课时
     *
     * @return part_num - 本节课时
     */
    public String getPartNum() {
        return partNum;
    }

    /**
     * 设置本节课时
     *
     * @param partNum 本节课时
     */
    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    /**
     * 获取课件物理地址
     *
     * @return source_src - 课件物理地址
     */
    public String getSourceSrc() {
        return sourceSrc;
    }

    /**
     * 设置课件物理地址
     *
     * @param sourceSrc 课件物理地址
     */
    public void setSourceSrc(String sourceSrc) {
        this.sourceSrc = sourceSrc;
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