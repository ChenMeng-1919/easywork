package com.cm.easywork.entity;

import lombok.Data;

/*
 * @author: cm
 * @date: Created in 2021/10/24 19:45
 * @description:栏杆实体
 */
@Data
public class LanGanEntity {
    //高度
    private double high;
    //长度
    private double length;
    //片数
    private double shards;

    //横杆分片数
    private double HGnumberOfShards;
    //横杆长度
    private double HGlength;
    //横杆焊杆数
    private double HGnumberOfWeldingRods;
    //横杆竖杆间净空
    private double HGverticalRods;
    //横杆支数
    private double HGcount;

    //竖杆长度
    private double SGlength;
    //竖杆支数
    private double SGcount;


    //立柱长度
    private double LZlength;
    //立柱支数
    private double LZcount;

    //面管长度
    private double MGlength;
    //面管支数
    private double MGcount;

    //左端
    private double left;
    //右端
    private double right;


}
