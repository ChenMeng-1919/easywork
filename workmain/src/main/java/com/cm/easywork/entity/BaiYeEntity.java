package com.cm.easywork.entity;

import lombok.Data;

import java.math.BigDecimal;

/*
 * @author: cm
 * @date: Created in 2021/10/20 14:36
 * @description:百叶实体
 */
@Data
public class BaiYeEntity {
    //宽度
    private Double width;
    //高度
    private Double high;
    //个数
    private Double number;
    //边框长度
    private Double frameLength;
    //边框支数
    private Double frameCount;
    //孔数
    private Double holes;
    //定位
    private Double position;
    //叶片长度
    private String leafLength;
    //叶片支数
    private Double leafCount;
    //面积
    private BigDecimal area;
    //备注
    private String remark;

}
