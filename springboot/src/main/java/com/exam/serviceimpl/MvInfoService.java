package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.MvInfo;
import com.exam.mapper.MvInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MvInfoService {

    @Autowired
    private MvInfoMapper mvInfoMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public IPage<MvInfo> listAll(Page page) {
    	return mvInfoMapper.listAll(page);
    }

    /**
     * mv列表查询
     *
     * @return 返回集合，没有返回空List
     */
    public IPage<MvInfo> listAllMv(Page page, String subject,String id){
        return mvInfoMapper.listAllMv(page,subject,id);
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public MvInfo getById(String id) {
    	return mvInfoMapper.getById(id);
    }
	
    /**
     * 新增，插入所有字段
     *
     * @param mvInfo 新增的记录
     * @return 返回影响行数
     */
    public int insert(MvInfo mvInfo) {
        mvInfo.setPid(String.valueOf(System.currentTimeMillis()));
    	return mvInfoMapper.insert(mvInfo);
    }
	

	
    /**
     * 修改，修改所有字段
     *
     * @param mvInfo 修改的记录
     * @return 返回影响行数
     */
    public int update(MvInfo mvInfo) {
    	return mvInfoMapper.update(mvInfo);
    }
	
    /**
     * 删除记录
     *
     * @param mvInfo 待删除的记录
     * @return 返回影响行数
     */
    public int delete(MvInfo mvInfo) {
    	return mvInfoMapper.delete(mvInfo);
    }
	
}