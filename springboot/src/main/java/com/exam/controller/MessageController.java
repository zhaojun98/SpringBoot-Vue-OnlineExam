package com.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.Message;
import com.exam.serviceimpl.MessageServiceImpl;
import com.exam.serviceimpl.MvInfoService;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private MvInfoService mvInfoService;



    @PostMapping("/messages/findAll")
    public ApiResult<Message> findAll(@RequestBody JSONObject param) {
        Page<Message> page = new Page<>(param.getLong("page"),param.getLong("size"));
        return ApiResultHandler.buildApiResult(200,"查询所有留言",messageService.findAll2(page,param.getString("mvId")));
    }

    @GetMapping("/message/{id}")
    public ApiResult findById(@PathVariable("id") Integer id) {
        Message res = messageService.findById(id);
        return ApiResultHandler.buildApiResult(200,"根据Id查询",res);
    }

    @DeleteMapping("/message/delete/{id}")
    public int delete(@PathVariable("id") Integer id) {
        int res = messageService.delete(id);
        return res;
    }

    @PostMapping("/message/add")
    public ApiResult add(@RequestBody Message message) {
        message.setTime(String.valueOf(System.currentTimeMillis()));
        int res = messageService.add(message);
        if (res == 0) {
            return ApiResultHandler.buildApiResult(400,"添加失败",res);
        } else {
            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
    }

}
