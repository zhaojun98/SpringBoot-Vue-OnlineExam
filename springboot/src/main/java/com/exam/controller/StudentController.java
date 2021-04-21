package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.MultiQuestion;
import com.exam.entity.Student;
import com.exam.serviceimpl.StudentServiceImpl;
import com.exam.util.ApiResultHandler;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;
    //分页查询全部学生信息
    @GetMapping("/students/{page}/{size}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Student> studentPage = new Page<>(page,size);
        IPage<Student> res = studentService.findAll(studentPage);
        return  ApiResultHandler.buildApiResult(200,"分页查询所有学生",res);
    }
    //更具学生编号查询学生信息
    @GetMapping("/student/{studentId}")
    public ApiResult findById(@PathVariable("studentId") Integer studentId) {
        Student res = studentService.findById(studentId);
        if (res != null) {
        return ApiResultHandler.buildApiResult(200,"请求成功",res);
        } else {
            return ApiResultHandler.buildApiResult(404,"查询的用户不存在",null);
        }
    }
    //更具学生编号删除学生信息
    @DeleteMapping("/student/{studentId}")
    public ApiResult deleteById(@PathVariable("studentId") Integer studentId) {
        return ApiResultHandler.buildApiResult(200,"删除成功",studentService.deleteById(studentId));
    }
    //更新学生信息密码
    @PutMapping("/studentPWD")
    public ApiResult updatePwd(@RequestBody Student student) {
        studentService.updatePwd(student);
        return ApiResultHandler.buildApiResult(200,"密码更新成功",null);
    }
    //更新学生信息
    @PutMapping("/student")
    public ApiResult update(@RequestBody Student student) {
        int res = studentService.update(student);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"更新成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"更新失败",res);
    }
    //添加学生信息
    @PostMapping("/student")
    public ApiResult add(@RequestBody Student student) {
        int res = studentService.add(student);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"添加成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"添加失败",null);
        }
    }

    /**
     * 导入学生信息模板
     * @param response
     */
    @RequestMapping("/student/exportClient")
    public void toUser(HttpServletResponse response, HttpServletRequest req){
        System.out.println();
        studentService.file(response);
    }

    /**
     * 导入学生信息数据
     * @param
     */
    @RequestMapping("/student/importClient")
    public Object importClient(HttpServletRequest request ){
        System.out.println();
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");

        List<Student> students = studentService.clientParseExcel(file);
        for (Student student : students) {
            studentService.add(student);
        }
        return ApiResultHandler.buildApiResult(200,"导入成功","success");
    }


}
