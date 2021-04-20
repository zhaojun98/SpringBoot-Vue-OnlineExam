package com.exam.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.MultiQuestion;
import com.exam.mapper.MultiQuestionMapper;
import com.exam.service.MultiQuestionService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultiQuestionServiceImpl implements MultiQuestionService {

    @Autowired
    private MultiQuestionMapper multiQuestionMapper;
    @Override
    public List<MultiQuestion> findByIdAndType(Integer PaperId) {
        return multiQuestionMapper.findByIdAndType(PaperId);
    }

    @Override
    public IPage<MultiQuestion> findAll(Page<MultiQuestion> page) {
        return multiQuestionMapper.findAll(page);
    }

    @Override
    public MultiQuestion findOnlyQuestionId() {
        return multiQuestionMapper.findOnlyQuestionId();
    }

    @Override
    public int add(MultiQuestion multiQuestion) {
        return multiQuestionMapper.add(multiQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return multiQuestionMapper.findBySubject(subject,pageNo);
    }
    //  导出模版
    public List<MultiQuestion> clientParseExcel(MultipartFile file) {
        //获取文件上传名字
        String originalFilename = file.getOriginalFilename();
        //获取文件流
        InputStream inputStream= null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MultiQuestion> list = new ArrayList<>();

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
                MultiQuestion client =new MultiQuestion();      //题库对象
                for (int j = 0; j <= clos; j++) {//获取单元格中内容
                    String subject = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String question = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerA = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerB =sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerC = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerD =sheet.getRow(i).getCell(j++).getStringCellValue();
                    String rightAnswer=sheet.getRow(i).getCell(j++).getStringCellValue();
                    String analysis=sheet.getRow(i).getCell(j++).getStringCellValue();
                    String score = df.format(sheet.getRow(i).getCell(j++).getNumericCellValue());
                    String section = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String level = df.format(sheet.getRow(i).getCell(j++).getNumericCellValue());


//                    client.setQuestionId(); //题库编号
                    client.setSubject(subject);
                    client.setQuestion(question);       //题目内容
                    client.setAnswerA(answerA);     //答案A
                    client.setAnswerB(answerB);
                    client.setAnswerC(answerC);
                    client.setAnswerD(answerD);
                    client.setRightAnswer(rightAnswer);     //答案
                    client.setAnalysis(analysis);           //解析
                    client.setScore(Integer.valueOf(score));                 //分数
                    client.setSection(section);         //分类
                    client.setLevel(level);         //等级
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
                MultiQuestion client =new MultiQuestion();
                for (int j = 0; j <= clos; j++) {//获取单元格中内容
                    String subject = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String question = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerA = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerB =sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerC = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String answerD =sheet.getRow(i).getCell(j++).getStringCellValue();
                    String rightAnswer =sheet.getRow(i).getCell(j++).getStringCellValue();
                    String analysis=sheet.getRow(i).getCell(j++).getStringCellValue();
                    String score = df.format(sheet.getRow(i).getCell(j++).getNumericCellValue());
                    String section = sheet.getRow(i).getCell(j++).getStringCellValue();
                    String level = df.format(sheet.getRow(i).getCell(j++).getNumericCellValue());


//                    client.setQuestionId(); //题库编号
                    client.setSubject(subject);
                    client.setQuestion(question);       //题目内容
                    client.setAnswerA(answerA);     //答案A
                    client.setAnswerB(answerB);
                    client.setAnswerC(answerC);
                    client.setAnswerD(answerD);
                    client.setRightAnswer(rightAnswer);     //答案
                    client.setAnalysis(analysis);           //解析
                    client.setScore(Integer.valueOf(score));                 //分数
                    client.setSection(section);         //分类
                    client.setLevel(level);         //等级
                }
                list.add(client);
            }
        }

        return list;

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
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("题库模版.xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建excel文件夹
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("题库模版");
        // 在sheet里创建第一行，参数为行索引excel的行()
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cellOne = row1.createCell(0);
        cellOne.setCellValue("题库信息");
        //设置行高40px
        row1.setHeight((short)(15.625*40));
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER); //竖向居中
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));    // 合并单元格
        cellOne.setCellStyle(style);


        for(int i = 0; i < 11; i++){
            sheet.setColumnWidth(i, 20*256);
        }

        HSSFRow row2 = sheet.createRow(1);
        row2.setRowStyle(style);
        row2.setHeight((short)(15.625*40));

        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row2.createCell(0);
        // 设置单元格内容
        cell.setCellValue("科目");
        HSSFCell cell1 = row2.createCell(1);
        cell1.setCellValue("题目");


        HSSFCell cell2 = row2.createCell(2);
        cell2.setCellValue("答案A");

        HSSFCell cell3 = row2.createCell(3);
        cell3.setCellValue("答案B");


        HSSFCell cell4 = row2.createCell(4);
        cell4.setCellValue("答案C");

        HSSFCell cell5 = row2.createCell(5);
        cell5.setCellValue("答案D");

        HSSFCell cell6 = row2.createCell(6);
        cell6.setCellValue("正确答案");

        HSSFCell cell7 = row2.createCell(7);
        cell7.setCellValue("解析");

        HSSFCell cell8 = row2.createCell(8);
        cell8.setCellValue("分数");

        HSSFCell cell9 = row2.createCell(9);
        cell9.setCellValue("类别");

        HSSFCell cell10 = row2.createCell(10);
        cell10.setCellValue("级别");
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


    /**
     * 获取 cell 的值,excel获取单元格类型
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String value = "";
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                //如果为时间格式的内容
                if (DateUtil.isCellDateFormatted(cell)) {
                    //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value=sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case FORMULA:
                value = cell.getCellFormula() + "";
                break;
            case BLANK:
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;
            case ERROR:
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }
}
