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

    @ExcelProperty("孔数")
    private double holes;

}
