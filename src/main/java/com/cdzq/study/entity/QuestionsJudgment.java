package com.cdzq.study.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "questions_judgment")
public class QuestionsJudgment {
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
     * 题目分类ID
     */
    @Column(name = "qtype_id")
    private Integer qtypeId;

    /**
     * 完成时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

    /**
     * 题干
     */
    @Column(name = "title")
    private String title;

    /**
     * 正确答案
     */
    @Column(name = "answer")
    private String answer;

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
     * 获取题目分类ID
     *
     * @return qtype_id - 题目分类ID
     */
    public Integer getQtypeId() {
        return qtypeId;
    }

    /**
     * 设置题目分类ID
     *
     * @param qtypeId 题目分类ID
     */
    public void setQtypeId(Integer qtypeId) {
        this.qtypeId = qtypeId;
    }

    /**
     * 获取完成时间
     *
     * @return insert_time - 完成时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * 设置完成时间
     *
     * @param insertTime 完成时间
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * 获取题干
     *
     * @return title - 题干
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置题干
     *
     * @param title 题干
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取正确答案
     *
     * @return answer - 正确答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置正确答案
     *
     * @param answer 正确答案
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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