package com.exam.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.MvInfo;
import com.exam.serviceimpl.MvInfoService;
import com.exam.util.ApiResultHandler;
import com.exam.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("mv")
public class MvInfoController {

    @Autowired
    private MvInfoService mvInfoService;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public ApiResult listAll(@RequestBody JSONObject param) {
        Page<MvInfo> page = new Page<>(param.getLong("page"),param.getLong("size"));
        return ApiResultHandler.buildApiResult(200, "请求成功！", mvInfoService.listAll(page));
    }


    /**
     * mv列表查询：根据subject条件筛选
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping(value = "listAllMv",method = RequestMethod.POST)
    public ApiResult listAllMv(@RequestBody JSONObject param){
        Page<MvInfo> page = new Page<>(param.getLong("page"), param.getLong("size"));
        QueryWrapper<MvInfo> queryWrapper = new QueryWrapper<>();
        return ApiResultHandler.buildApiResult(200,"请求成功",mvInfoService.listAllMv(page,param.getString("subject")));
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("getById/{id}")
    public ApiResult getById(@PathVariable("id")String id) {
        return ApiResultHandler.buildApiResult(200,"请求成功！", mvInfoService.getById(id));
    }
    
    /**
     * 新增，插入所有字段
     *
     * @param mvInfo 新增的记录
     * @return 返回影响行数
     */
    @RequestMapping("insert")
    public ApiResult insert(@RequestBody MvInfo mvInfo) {
        mvInfo.setId(UUIDUtils.create());
        return ApiResultHandler.buildApiResult(200,"请求成功！", mvInfoService.insert(mvInfo));
    }
    
    /**
     * 修改，修改所有字段
     *
     * @param mvInfo 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("update")
    public ApiResult update(@RequestBody MvInfo mvInfo) {
        return  ApiResultHandler.buildApiResult(200,"请求成功！", mvInfoService.update(mvInfo));
    }
    
    /**
     * 删除记录
     *
     * @param mvInfo 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("delete")
    public ApiResult delete(@RequestBody MvInfo mvInfo) {
        return ApiResultHandler.buildApiResult(200,"请求成功！", mvInfoService.delete(mvInfo));
    }
    
}