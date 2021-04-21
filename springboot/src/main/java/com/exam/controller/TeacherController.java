package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.Student;
import com.exam.entity.Teacher;
import com.exam.serviceimpl.TeacherServiceImpl;
import com.exam.util.ApiResultHandler;
import com.exam.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TeacherController {

    private TeacherServiceImpl teacherService;
    @Autowired
    public TeacherController(TeacherServiceImpl teacherService){
        this.teacherService = teacherService;
    }
    //分页查询全部教师信息查询
    @GetMapping("/teachers/{page}/{size}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size){
        Page<Teacher> teacherPage = new Page<>(page,size);
        IPage<Teacher> teacherIPage = teacherService.findAll(teacherPage);

        return ApiResultHandler.buildApiResult(200,"查询所有教师",teacherIPage);
    }
    //根据教师编号查询教师信息
    @GetMapping("/teacher/{teacherId}")
    public ApiResult findById(@PathVariable("teacherId") Integer teacherId){
        return ApiResultHandler.success(teacherService.findById(teacherId));
    }
    //更具教师编号删除教师信息
    @DeleteMapping("/teacher/{teacherId}")
    public ApiResult deleteById(@PathVariable("teacherId") Integer teacherId){
        return ApiResultHandler.success(teacherService.deleteById(teacherId));
    }
    //更新教师信息
    @PutMapping("/teacher")
    public ApiResult update(@RequestBody Teacher teacher){
        return ApiResultHandler.success(teacherService.update(teacher));
    }
    //添加教师信息
    @PostMapping("/teacher")
    public ApiResult add(@RequestBody Teacher teacher){
        return ApiResultHandler.success(teacherService.add(teacher));
    }

    /**
     * 导出教师信息模板
     * @param response
     */
    @RequestMapping("/teacher/exportClient")
    public void toUser(HttpServletResponse response, HttpServletRequest req){
        teacherService.file(response);
    }


    /**
     * 导入教师信息数据
     * @param
     */
    @RequestMapping("/teacher/importClient")
    public Object importClient(HttpServletRequest request ){
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");

        List<Teacher> teachers = teacherService.clientParseExcel(file);
        for (Teacher teacher : teachers) {
            teacherService.add(teacher);
        }
        return ApiResultHandler.buildApiResult(200,"导入成功","success");
    }


}
