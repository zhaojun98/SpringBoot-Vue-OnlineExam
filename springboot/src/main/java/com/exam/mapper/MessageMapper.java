package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("select id,id as temp_id,student_id,content,time,mv_id from message order by id desc")
    @Results({
            @Result(property = "replays", column = "temp_id",many = @Many(select = "com.exam.mapper.ReplayMapper.findAllById"))
    })
    IPage<Message> findAll(Page page);

    @Select("select id,student_id,content,time,mv_id from message where id = #{id}")
    @Results({
            @Result(property = "replays", column = "id",many = @Many(select = "com.exam.mapper.ReplayMapper.findAllById"))
    })
    Message findById(Integer id);

    @Delete("delete from message where id = #{id}")
    int delete(Integer id);

    @Update("update message set student_id = #{studentId}, content = #{content}, time = #{time},mv_id=#{mvId} where " +
            "id = #{id}")
    int update(Message message);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into message(student_id, content, time,mv_id) values(#{studentId},#{content},#{time},#{mvId})")
    int add(Message message);

    @Select("select id,student_id,content,time,mv_id from message where mv_id=#{mvId} order by id desc")
    IPage<Message> findAll2(Page page,@Param("mvId") String mvId);
}
