package com.cm.easywork.entity;

import lombok.Data;

/*
 * @author: cm
 * @date: Created in 2021/10/20 14:36
 * @description:百叶实体
 */
@Data
public class BaiYeEntity {
    //宽度
    private double width;
    //高度
    private double high;
    //个数
    private double number;
    //边框长度
    private double frameLength;
    //边框支数
    private double frameCount;
    //孔数
    private double holes;
    //定位
    private double position;
    //叶片长度
    private String leafLength;
    //叶片支数
    private double leafCount;
    //面积
    private double area;
    //备注
    private String remark;

}
