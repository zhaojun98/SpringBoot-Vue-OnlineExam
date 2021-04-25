package com.exam.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.MvInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface MvInfoMapper {

	/**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
	@Select("SELECT m.*,t.teacherName,t.institute,t.type FROM mv_info m inner join teacher t on t.teacherId=m.teacher_id")
	List<MvInfo> listAll(Page page);

	/**
	 * 视频列表查询
	 *
	 * @return 返回集合，没有返回空List
	 */
	@Select("SELECT m.*,t.teacherName,t.institute,t.type FROM mv_info m inner join teacher t on t.teacherId=m.teacher_id where m.subject= #{subject}")
	List<MvInfo> listAllMv(Page page, @Param("subject") String subject);

	/**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
	@Select("SELECT  t.id,t.teacher_id,t.create_time, t.mv_path,t.subject,t.pid,t.drama FROM mv_info t WHERE t.id = #{id}")
	MvInfo getById(@Param("id") String id);
	
	/**
     * 新增，插入所有字段
     *
     * @param mvInfo 新增的记录
     * @return 返回影响行数
     */
	@Insert(" INSERT INTO mv_info id,teacher_id,create_time,mv_path,subject,pid,drama " +
			"VALUES(#{id},#{teacherId},#{createTime},#{mvPath},#{subject},#{pid},#{drama})")
	int insert(MvInfo mvInfo);
	
	/**
     * 修改，修改所有字段
     *
     * @param mvInfo 修改的记录
     * @return 返回影响行数
     */
	@Update("UPDATE mv_info set teacher_id=#{teacherId},create_time=#{createTime}," +
			"mv_path=#{mvPath},subject=#{subject},pid=#{pid},drama=#{drama} WHERE id = #{id}")
	int update(MvInfo mvInfo);
	
	/**
     * 删除记录
     *
     * @param mvInfo 待删除的记录
     * @return 返回影响行数
     */
	@Delete("DELETE from mv_info WHERE id = #{id}")
	int delete(MvInfo mvInfo);
	
}