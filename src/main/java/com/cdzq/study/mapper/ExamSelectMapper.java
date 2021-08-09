package com.cdzq.study.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ExamSelectMapper {
    @Select("<script>" +
            "select * from (\n" +
            "SELECT CONCAT('o',q.id) as id,q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'单选题' as leixing from questions_one q left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
            "SELECT CONCAT('m',q.id) as id,q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'多选题' as leixing from questions_more q left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 union all\n" +
            "SELECT CONCAT('b',q.id) as id,q.title,q.course_id,c.title as course_name,q.qtype_id,t.title as qtype_name,'填空题' as leixing from questions_blanks q left join course c on q.course_id=c.id left join qtype t on q.qtype_id=t.id where q.is_deleted=0 ) as biao where 1=1 " +
            "<if test='title != null and title != &quot;&quot;'> and title like CONCAT('%', #{title}, '%') </if>"+
            "<if test='courses != null and courses != &quot;&quot;'> and course_id = #{courses} </if>"+
            "<if test='qtype != null and qtype != &quot;&quot;'> and qtype_id = #{qtype} </if>"+
            "</script>")
    @Results(id="questionsResults", value={
            @Result(property="title",   column="title"),
            @Result(property="courses", column="courses"),
            @Result(property="qtype",  column="qtype"),
    })
    List<Map<String, Object>> selectQuestions(String title, String courses, String qtype);
}
