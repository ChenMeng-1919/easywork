package com.cm.easywork.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/*
 * @author: cm
 * @date: Created in 2021/10/24 19:43
 * @description:栏杆输入
 */
@Data
public class LanGanInputEntity {

    @ExcelProperty("高度")
    private double high;

    @ExcelProperty("长度")
    private double length;

    @ExcelProperty("片数")
    private double shards;

    @ExcelProperty("项目")
    private String project;

    @ExcelProperty("颜色")
    private String colour;

    @ExcelProperty("单号")
    private String serialNumber;

    @ExcelProperty("日期")
    private String date;

    @ExcelProperty("横杆")
    private String crossbar;

    @ExcelProperty("竖杆")
    private String verticalPole;

    @ExcelProperty("立柱")
    private String upright;

    @ExcelProperty("面管")
    private String auxiliaryLever;

    @ExcelProperty("栏杆类型")
    private String railingType;
}
