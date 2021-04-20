package com.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.exam.entity.ApiResult;
import com.exam.entity.MultiQuestion;
import com.exam.serviceimpl.MultiQuestionServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class MultiQuestionController {

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @GetMapping("/multiQuestionId")
    public ApiResult findOnlyQuestion() {
        MultiQuestion res = multiQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(200,"查询成功",res);
    }

    @PostMapping("/MultiQuestion")
    public ApiResult add(@RequestBody MultiQuestion multiQuestion) {
        int res = multiQuestionService.add(multiQuestion);
        if (res != 0) {

            return ApiResultHandler.buildApiResult(200,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }

    @RequestMapping("/importClient")
    public Object importClient(HttpServletRequest request ){
        System.out.println();
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");

        List<MultiQuestion> multiQuestions = multiQuestionService.clientParseExcel(file);
        for (MultiQuestion multiQuestion : multiQuestions) {
            multiQuestionService.add(multiQuestion);
        }
        return ApiResultHandler.buildApiResult(200,"导入成功","res");
    }
    /**
     * 导出题库模板
     * @param response
     */
    @RequestMapping("/exportClient")
    public void toUser(HttpServletResponse response, HttpServletRequest req){
        System.out.println();
        multiQuestionService.file(response);
    }

}
