package com.exam.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class Result {
    public Object success(Object data){
        JSONObject result = new JSONObject();
        result.put("code", "200");
        result.put("data", data);
        return result;
    }

    /**
     * 返回值
     * @param data 数据
     * @return object
     */
    public Object fail(Object data){
        JSONObject result = new JSONObject();
        result.put("code", "500");
        result.put("data", data);
        return result;
    }

    /**
     * 返回值
     * @param page 数据
     * @return object
     */
    public <T> Object buildPage(Page<T> page){
        JSONObject result = new JSONObject();
        result.put("code", "200");
        result.put("total", page.getTotal());
        result.put("rows", page.getRecords());
        return result;
    }

}
