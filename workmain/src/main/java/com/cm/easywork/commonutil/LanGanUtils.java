package com.cm.easywork.commonutil;

import com.cm.easywork.entity.LanGanInputEntity;

import java.util.ArrayList;
import java.util.List;

/*
 * @author: cm
 * @date: Created in 2021/10/25 17:37
 * @description:
 */
public class LanGanUtils {
    public static double[] getHGlength(LanGanInputEntity lanGanInputEntity, List<String[]> splitArgsList) {
        //分片数
        int HGnumberOfShards = 0;
        //横杆长度
        double HGlength = 0;
        double left = 0;
        ArrayList<Double[]> resultList = new ArrayList<>();
        //确定分片数
        for (int i = 1; i < 50; i++) {
            double v = (lanGanInputEntity.getLength() - 100 - 100 - Double.parseDouble(splitArgsList.get(3)[0]) * (i + 1)) / i;
            if (v <= 1200) {
                HGnumberOfShards = i;
                break;
            }
        }
        //确定横杆长度的备选集合
        for (double j = 100; j > 90; j -= 0.5) {
            double v = (lanGanInputEntity.getLength() - j - j - Double.parseDouble(splitArgsList.get(3)[0]) * (HGnumberOfShards + 1)) / HGnumberOfShards;
            if ((int) v == v && v <= 1200) {
                Double[] tmp = {v, j};
                resultList.add(tmp);
            }
        }
        //判断有无最优解
        for (Double[] aDouble : resultList) {
            if (aDouble[0] % 10 == 0 || aDouble[0] % 10 == 5) {
                HGlength = aDouble[0];
                left = aDouble[1];
                break;
            }
        }
        //如果无最优解则取最大整数
        if (HGlength == 0) {
            HGlength = resultList.get(0)[0];
            left = resultList.get(0)[1];
        }
        double[] result = {HGnumberOfShards, HGlength, left};
        return result;
    }

    public static double[] getHGnumberOfWeldingRods(LanGanInputEntity lanGanInputEntity, List<String[]> splitArgsList, Double HGlength) {
        double[] result = new double[2];
        for (int i = 1; i < 100; i++) {
            double v = (HGlength - Double.parseDouble(splitArgsList.get(1)[0]) * i) / (i + 1);
            if (v <= 110) {
                result[0] = i;
                result[1] = v;
                break;
            }
        }
        return result;
    }

    /*转角栏杆*/
    public static double[] getHGlengthZJ(LanGanInputEntity lanGanInputEntity, List<String[]> splitArgsList) {
        //分片数
        int HGnumberOfShards = 0;
        //横杆长度
        double HGlength = 0;
        double left = 0;
        ArrayList<Double[]> resultList = new ArrayList<>();
        //确定分片数
        for (int i = 1; i < 50; i++) {
            double v = (lanGanInputEntity.getLength() - Double.parseDouble(splitArgsList.get(3)[1]) - 100 - 40 - Double.parseDouble(splitArgsList.get(3)[0]) * (i + 1)) / i;
            if (v <= 1200) {
                HGnumberOfShards = i;
                break;
            }
        }
        //确定横杆长度的备选集合
        for (double j = 100; j > 90; j -= 0.5) {
            double v = (lanGanInputEntity.getLength() - Double.parseDouble(splitArgsList.get(3)[1]) - j - 40 - Double.parseDouble(splitArgsList.get(3)[0]) * (HGnumberOfShards + 1)) / HGnumberOfShards;
            if ((int) v == v && v <= 1200) {
                Double[] tmp = {v, j};
                resultList.add(tmp);
            }
        }
        //判断有无最优解
        for (Double[] aDouble : resultList) {
            if (aDouble[0] % 10 == 0 || aDouble[0] % 10 == 5) {
                HGlength = aDouble[0];
                left = aDouble[1];
                break;
            }
        }
        //如果无最优解则取最大整数
        if (HGlength == 0) {
            HGlength = resultList.get(0)[0];
            left = resultList.get(0)[1];
        }
        double[] result = {HGnumberOfShards, HGlength, left};
        return result;
    }

}
