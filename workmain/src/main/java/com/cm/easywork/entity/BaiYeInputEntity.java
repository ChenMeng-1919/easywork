package com.cm.easywork.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/*
 * @author: cm
 * @date: Created in 2021/10/20 17:22
 * @description:
 */
@Data
public class BaiYeInputEntity {

    @ExcelProperty("宽度")
    private double width;

    @ExcelProperty("高度")
    private double high;

    @ExcelProperty("个数")
    private double number;

    @ExcelProperty("百叶间距")
    private double louverSpacing;

    @ExcelProperty("开门宽度")
    private String openWidth;

    @ExcelProperty("开门方式")
    private String openWay;

    @ExcelProperty("开门高度")
    private double openHeight;

    @ExcelProperty("项目")
    private String project;

    @ExcelProperty("颜色")
    private String colour;

    @ExcelProperty("日期")
    private String date;

    @ExcelProperty("边框")
    private String frame;

    @ExcelProperty("叶片")
    private String blade;

}
