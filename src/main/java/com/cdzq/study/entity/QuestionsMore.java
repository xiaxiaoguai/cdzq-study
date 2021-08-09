package com.cdzq.study.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "questions_more")
public class QuestionsMore {
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
     * 选项
     */
    @Column(name = "optiona")
    private String optiona;

    /**
     * 选项
     */
    @Column(name = "optionb")
    private String optionb;

    /**
     * 选项
     */
    @Column(name = "optionc")
    private String optionc;

    /**
     * 选项
     */
    @Column(name = "optiond")
    private String optiond;

    /**
     * 选项
     */
    @Column(name = "optione")
    private String optione;

    /**
     * 选项
     */
    @Column(name = "optionf")
    private String optionf;

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
     * 获取选项
     *
     * @return optiona - 选项
     */
    public String getOptiona() {
        return optiona;
    }

    /**
     * 设置选项
     *
     * @param optiona 选项
     */
    public void setOptiona(String optiona) {
        this.optiona = optiona;
    }

    /**
     * 获取选项
     *
     * @return optionb - 选项
     */
    public String getOptionb() {
        return optionb;
    }

    /**
     * 设置选项
     *
     * @param optionb 选项
     */
    public void setOptionb(String optionb) {
        this.optionb = optionb;
    }

    /**
     * 获取选项
     *
     * @return optionc - 选项
     */
    public String getOptionc() {
        return optionc;
    }

    /**
     * 设置选项
     *
     * @param optionc 选项
     */
    public void setOptionc(String optionc) {
        this.optionc = optionc;
    }

    /**
     * 获取选项
     *
     * @return optiond - 选项
     */
    public String getOptiond() {
        return optiond;
    }

    /**
     * 设置选项
     *
     * @param optiond 选项
     */
    public void setOptiond(String optiond) {
        this.optiond = optiond;
    }

    /**
     * 获取选项
     *
     * @return optione - 选项
     */
    public String getOptione() {
        return optione;
    }

    /**
     * 设置选项
     *
     * @param optione 选项
     */
    public void setOptione(String optione) {
        this.optione = optione;
    }

    /**
     * 获取选项
     *
     * @return optionf - 选项
     */
    public String getOptionf() {
        return optionf;
    }

    /**
     * 设置选项
     *
     * @param optionf 选项
     */
    public void setOptionf(String optionf) {
        this.optionf = optionf;
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