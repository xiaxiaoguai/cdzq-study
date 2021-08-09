package com.cdzq.study.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "questions_blanks")
public class QuestionsBlanks {
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
     * 空格数
     */
    @Column(name = "answer_count")
    private Integer answerCount;

    /**
     * 空格a答案
     */
    @Column(name = "answera")
    private String answera;

    /**
     * 空格b答案
     */
    @Column(name = "answerb")
    private String answerb;

    /**
     * 空格c答案
     */
    @Column(name = "answerc")
    private String answerc;

    /**
     * 空格d答案
     */
    @Column(name = "answerd")
    private String answerd;

    /**
     * 空格e答案
     */
    @Column(name = "answere")
    private String answere;

    /**
     * 空格f答案
     */
    @Column(name = "answerf")
    private String answerf;

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
     * 获取空格数
     *
     * @return answer_count - 空格数
     */
    public Integer getAnswerCount() {
        return answerCount;
    }

    /**
     * 设置空格数
     *
     * @param answerCount 空格数
     */
    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    /**
     * 获取空格a答案
     *
     * @return answera - 空格a答案
     */
    public String getAnswera() {
        return answera;
    }

    /**
     * 设置空格a答案
     *
     * @param answera 空格a答案
     */
    public void setAnswera(String answera) {
        this.answera = answera;
    }

    /**
     * 获取空格b答案
     *
     * @return answerb - 空格b答案
     */
    public String getAnswerb() {
        return answerb;
    }

    /**
     * 设置空格b答案
     *
     * @param answerb 空格b答案
     */
    public void setAnswerb(String answerb) {
        this.answerb = answerb;
    }

    /**
     * 获取空格c答案
     *
     * @return answerc - 空格c答案
     */
    public String getAnswerc() {
        return answerc;
    }

    /**
     * 设置空格c答案
     *
     * @param answerc 空格c答案
     */
    public void setAnswerc(String answerc) {
        this.answerc = answerc;
    }

    /**
     * 获取空格d答案
     *
     * @return answerd - 空格d答案
     */
    public String getAnswerd() {
        return answerd;
    }

    /**
     * 设置空格d答案
     *
     * @param answerd 空格d答案
     */
    public void setAnswerd(String answerd) {
        this.answerd = answerd;
    }

    /**
     * 获取空格e答案
     *
     * @return answere - 空格e答案
     */
    public String getAnswere() {
        return answere;
    }

    /**
     * 设置空格e答案
     *
     * @param answere 空格e答案
     */
    public void setAnswere(String answere) {
        this.answere = answere;
    }

    /**
     * 获取空格f答案
     *
     * @return answerf - 空格f答案
     */
    public String getAnswerf() {
        return answerf;
    }

    /**
     * 设置空格f答案
     *
     * @param answerf 空格f答案
     */
    public void setAnswerf(String answerf) {
        this.answerf = answerf;
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