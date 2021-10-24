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

}
