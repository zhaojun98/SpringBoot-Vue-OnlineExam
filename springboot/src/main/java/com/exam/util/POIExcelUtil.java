package com.exam.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class POIExcelUtil {

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
