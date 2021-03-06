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
     * ??????excel??????
     * @param
     */
    public  void file(HttpServletResponse response) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // ?????????????????????????????????
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("??????????????????.xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //??????excel?????????
        // ??????HSSFWorkbook??????(excel???????????????)
        HSSFWorkbook wb = new HSSFWorkbook();
        // ????????????sheet?????????excel????????????
        HSSFSheet sheet = wb.createSheet("????????????");
        // ???sheet???????????????????????????????????????excel??????()
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cellOne = row1.createCell(0);
        cellOne.setCellValue("????????????");
        //????????????40px
        row1.setHeight((short)(15.625*40));
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //????????????
        style.setVerticalAlignment(VerticalAlignment.CENTER); //????????????
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));    // ???????????????
        cellOne.setCellStyle(style);


        for(int i = 0; i < 9; i++){
            sheet.setColumnWidth(i, 20*256);
        }

        HSSFRow row2 = sheet.createRow(1);
        row2.setRowStyle(style);
        row2.setHeight((short)(15.625*40));

        // ??????????????????excel?????????????????????????????????????????????0???255?????????????????????
        HSSFCell cell = row2.createCell(0);
        cell.setCellValue("??????");    // ?????????????????????

        HSSFCell cell1 = row2.createCell(1);
        cell1.setCellValue("??????");

        HSSFCell cell2 = row2.createCell(2);
        cell2.setCellValue("??????");

        HSSFCell cell3 = row2.createCell(3);
        cell3.setCellValue("??????");


        HSSFCell cell4 = row2.createCell(4);
        cell4.setCellValue("??????");

        HSSFCell cell5 = row2.createCell(5);
        cell5.setCellValue("??????");

        HSSFCell cell6 = row2.createCell(6);
        cell6.setCellValue("????????????");

        HSSFCell cell7 = row2.createCell(7);
        cell7.setCellValue("???????????????");

        HSSFCell cell8 = row2.createCell(8);
        cell8.setCellValue("??????");

        HSSFCell cell9 = row2.createCell(9);
        cell9.setCellValue("??????");

        DataValidationHelper helper = sheet.getDataValidationHelper();
//        DataValidationConstraint constraint = helper.createExplicitListConstraint(strings);
        //???????????????????????????
        CellRangeAddressList regions = new CellRangeAddressList(2, 1024, 3, 3);

        //??????Excel???????????????
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


    //  ????????????
    public List<Student> clientParseExcel(MultipartFile file) {
        //????????????????????????
        String originalFilename = file.getOriginalFilename();
        //???????????????
        InputStream inputStream= null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Student> list = new ArrayList<>();

        // ?????????????????????Excel(2003???2007)
        String suffix = originalFilename
                .substring(originalFilename.lastIndexOf("."), originalFilename.length());
        // Excel2003
        if (".xls".equals(suffix)) {
            POIFSFileSystem fileSystem = null;
            // ?????????
            HSSFWorkbook workBook = null;
            try {
                fileSystem = new POIFSFileSystem(inputStream);
                workBook = new HSSFWorkbook(fileSystem);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // ????????????????????????
            HSSFSheet sheet = workBook.getSheetAt(0);
            //????????????
            int rows = sheet.getLastRowNum();
            //????????????
            int clos = sheet.getRow(0).getPhysicalNumberOfCells();
            DecimalFormat df = new DecimalFormat("#");
            for (int i = 2; i <= rows; i++) {
                Student client =new Student();      //????????????
                for (int j = 0; j <= clos; j++) {//????????????????????????

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



                    client.setStudentName(name);            //??????
                    client.setInstitute(institute);       //??????
                    client.setMajor(major);     //??????
                    client.setGrade(grade);         //??????
                    client.setClazz(clazz);         //??????
                    client.setSex(sex);         //??????
                    client.setTel(tel);     //????????????
                    client.setRole("2");            //??????
                    client.setCardId(cardId);       //???????????????
                    client.setEmail(email);     //??????
                    client.setPwd(password);           //??????
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
            // ????????????????????????
            XSSFSheet sheet = workBook.getSheetAt(0);
            //????????????
            int rows = sheet.getLastRowNum();
            //????????????
            int clos = sheet.getRow(0).getPhysicalNumberOfCells();
            DecimalFormat df = new DecimalFormat("#");
            for (int i = 2; i <= rows; i++) {
                Student client =new Student();
                for (int j = 0; j <= clos; j++) {//????????????????????????
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



                    client.setStudentName(name);            //??????
                    client.setInstitute(institute);       //??????
                    client.setMajor(major);     //??????
                    client.setGrade(grade);         //??????
                    client.setClazz(clazz);         //??????
                    client.setSex(sex);         //??????
                    client.setTel(tel);     //????????????
                    client.setRole("2");            //??????
                    client.setCardId(cardId);       //???????????????
                    client.setEmail(email);     //??????
                    client.setPwd(password);           //??????
                }
                list.add(client);
            }
        }

        return list;

    }
}
