package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.MultiQuestion;
import com.exam.entity.Student;
import com.exam.mapper.StudentMapper;
import com.exam.service.StudentService;
import com.exam.util.POIExcelUtil;
import com.exam.util.UUIDUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public IPage<Student> findAll(Page<Student> page) {
        return studentMapper.findAll(page);
    }

    @Override
    public Student findById(Integer studentId) {
        return studentMapper.findById(studentId);
    }

    @Override
    public int deleteById(Integer studentId) {
        return studentMapper.deleteById(studentId);
    }

    @Override
    public int update(Student student) {
        return studentMapper.update(student);
    }

    @Override
    public int updatePwd(Student student) {
        return studentMapper.updatePwd(student);
    }

    @Override
    public int add(Student student) {
        return studentMapper.add(student);
    }

    /**
     * 创建excel表格
     * @param
     */
    public  void file(HttpServletResponse response) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("学生信息模版.xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建excel文件夹
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("学生信息");
        // 在sheet里创建第一行，参数为行索引excel的行()
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cellOne = row1.createCell(0);
        cellOne.setCellValue("学生信息");
        //设置行高40px
        row1.setHeight((short)(15.625*40));
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER); //竖向居中
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));    // 合并单元格
        cellOne.setCellStyle(style);


        for(int i = 0; i < 9; i++){
            sheet.setColumnWidth(i, 20*256);
        }

        HSSFRow row2 = sheet.createRow(1);
        row2.setRowStyle(style);
        row2.setHeight((short)(15.625*40));

        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row2.createCell(0);
        cell.setCellValue("姓名");    // 设置单元格内容

        HSSFCell cell1 = row2.createCell(1);
        cell1.setCellValue("学院");

        HSSFCell cell2 = row2.createCell(2);
        cell2.setCellValue("专业");

        HSSFCell cell3 = row2.createCell(3);
        cell3.setCellValue("年级");


        HSSFCell cell4 = row2.createCell(4);
        cell4.setCellValue("班级");

        HSSFCell cell5 = row2.createCell(5);
        cell5.setCellValue("性别");

        HSSFCell cell6 = row2.createCell(6);
        cell6.setCellValue("联系方式");

        HSSFCell cell7 = row2.createCell(7);
        cell7.setCellValue("身份证号码");

        HSSFCell cell8 = row2.createCell(8);
        cell8.setCellValue("邮箱");

        HSSFCell cell9 = row2.createCell(9);
        cell9.setCellValue("密码");

        DataValidationHelper helper = sheet.getDataValidationHelper();
//        DataValidationConstraint constraint = helper.createExplicitListConstraint(strings);
        //设置下拉控制的范围
        CellRangeAddressList regions = new CellRangeAddressList(2, 1024, 3, 3);

        //处理Excel兼容性问题
//        if(dataValidation instanceof XSSFDataValidation) {
//            dataValidation.setSuppressDropDownArrow(true);
//            dataValidation.setShowErrorBox(true);
//        }else {
//            dataValidation.setSuppressDropDownArrow(false);
//        }
        try {
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //  导出模版
    public List<Student> clientParseExcel(MultipartFile file) {
        //获取文件上传名字
        String originalFilename = file.getOriginalFilename();
        //获取文件流
        InputStream inputStream= null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Student> list = new ArrayList<>();

        // 判断文件是否是Excel(2003、2007)
        String suffix = originalFilename
                .substring(originalFilename.lastIndexOf("."), originalFilename.length());
        // Excel2003
        if (".xls".equals(suffix)) {
            POIFSFileSystem fileSystem = null;
            // 工作簿
            HSSFWorkbook workBook = null;
            try {
                fileSystem = new POIFSFileSystem(inputStream);
                workBook = new HSSFWorkbook(fileSystem);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取第一个工作簿
            HSSFSheet sheet = workBook.getSheetAt(0);
            //获取行数
            int rows = sheet.getLastRowNum();
            //获取列数
            int clos = sheet.getRow(0).getPhysicalNumberOfCells();
            DecimalFormat df = new DecimalFormat("#");
            for (int i = 2; i <= rows; i++) {
                Student client =new Student();      //题库对象
                for (int j = 0; j <= clos; j++) {//获取单元格中内容

                    String name =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String institute =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String major = POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String grade =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String clazz =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String sex =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String tel=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String cardId=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String email=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String password=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));



                    client.setStudentName(name);            //姓名
                    client.setInstitute(institute);       //学院
                    client.setMajor(major);     //专业
                    client.setGrade(grade);         //年级
                    client.setClazz(clazz);         //班级
                    client.setSex(sex);         //性别
                    client.setTel(tel);     //联系方式
                    client.setRole("2");            //角色
                    client.setCardId(cardId);       //身份证号码
                    client.setEmail(email);     //邮箱
                    client.setPwd(password);           //密码
                }
                list.add(client);
            }
            // Excel2007
        } else if (".xlsx".equals(suffix)) {
            XSSFWorkbook workBook = null;
            try {
                workBook = new XSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取第一个工作簿
            XSSFSheet sheet = workBook.getSheetAt(0);
            //获取行数
            int rows = sheet.getLastRowNum();
            //获取列数
            int clos = sheet.getRow(0).getPhysicalNumberOfCells();
            DecimalFormat df = new DecimalFormat("#");
            for (int i = 2; i <= rows; i++) {
                Student client =new Student();
                for (int j = 0; j <= clos; j++) {//获取单元格中内容
                    String name =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String institute =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String major = POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String grade =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String clazz =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String sex =POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String tel=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String cardId=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String email=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));
                    String password=POIExcelUtil.getCellValue(sheet.getRow(i).getCell(j++));



                    client.setStudentName(name);            //姓名
                    client.setInstitute(institute);       //学院
                    client.setMajor(major);     //专业
                    client.setGrade(grade);         //年级
                    client.setClazz(clazz);         //班级
                    client.setSex(sex);         //性别
                    client.setTel(tel);     //联系方式
                    client.setRole("2");            //角色
                    client.setCardId(cardId);       //身份证号码
                    client.setEmail(email);     //邮箱
                    client.setPwd(password);           //密码
                }
                list.add(client);
            }
        }

        return list;

    }
}
