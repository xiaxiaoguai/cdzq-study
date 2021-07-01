package com.cdzq.study.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "course")
public class Course {
    /**
     * 主键自增ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 分类ID
     */
    @Column(name = "subject_id")
    private Integer subjectId;

    /**
     * 分类父ID
     */
    @Column(name = "subject_parent_id")
    private Integer subjectParentId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 课程名称
     */
    @NotNull(message = "名称不能为空")
    @Column(name = "title")
    private String title;

    /**
     * 课程简介
     */
    @Column(name = "title_description")
    private String titleDescription;

    /**
     * 讲师名称
     */
    @Column(name = "teacher")
    private String teacher;

    /**
     * 讲师简介
     */
    @Column(name = "teacher_description")
    private String teacherDescription;

    /**
     * 总课时
     */
    @Column(name = "lesson_num")
    private String lessonNum;

    /**
     * 课程封面图片
     */
    @Column(name = "cover")
    private String cover;

    /**
     * 观看次数
     */
    @Column(name = "see_count")
    private Integer seeCount;

    /**
     * 使用次数
     */
    @Column(name = "use_count")
    private Integer useCount;

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
     * 获取分类ID
     *
     * @return subject_id - 分类ID
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * 设置分类ID
     *
     * @param subjectId 分类ID
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * 获取分类父ID
     *
     * @return subject_parent_id - 分类父ID
     */
    public Integer getSubjectParentId() {
        return subjectParentId;
    }

    /**
     * 设置分类父ID
     *
     * @param subjectParentId 分类父ID
     */
    public void setSubjectParentId(Integer subjectParentId) {
        this.subjectParentId = subjectParentId;
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
     * 获取课程名称
     *
     * @return title - 课程名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置课程名称
     *
     * @param title 课程名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取课程简介
     *
     * @return title_description - 课程简介
     */
    public String getTitleDescription() {
        return titleDescription;
    }

    /**
     * 设置课程简介
     *
     * @param titleDescription 课程简介
     */
    public void setTitleDescription(String titleDescription) {
        this.titleDescription = titleDescription;
    }

    /**
     * 获取讲师名称
     *
     * @return teacher - 讲师名称
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * 设置讲师名称
     *
     * @param teacher 讲师名称
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * 获取讲师简介
     *
     * @return teacher_description - 讲师简介
     */
    public String getTeacherDescription() {
        return teacherDescription;
    }

    /**
     * 设置讲师简介
     *
     * @param teacherDescription 讲师简介
     */
    public void setTeacherDescription(String teacherDescription) {
        this.teacherDescription = teacherDescription;
    }

    /**
     * 获取总课时
     *
     * @return lesson_num - 总课时
     */
    public String getLessonNum() {
        return lessonNum;
    }

    /**
     * 设置总课时
     *
     * @param lessonNum 总课时
     */
    public void setLessonNum(String lessonNum) {
        this.lessonNum = lessonNum;
    }

    /**
     * 获取课程封面图片
     *
     * @return cover - 课程封面图片
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置课程封面图片
     *
     * @param cover 课程封面图片
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * 获取观看次数
     *
     * @return see_count - 观看次数
     */
    public Integer getSeeCount() {
        return seeCount;
    }

    /**
     * 设置观看次数
     *
     * @param seeCount 观看次数
     */
    public void setSeeCount(Integer seeCount) {
        this.seeCount = seeCount;
    }

    /**
     * 获取使用次数
     *
     * @return use_count - 使用次数
     */
    public Integer getUseCount() {
        return useCount;
    }

    /**
     * 设置使用次数
     *
     * @param useCount 使用次数
     */
    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
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