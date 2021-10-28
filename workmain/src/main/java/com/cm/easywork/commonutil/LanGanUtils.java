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
        ArrayList<Double> resultList = new ArrayList<>();
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
            if ((int) v == v || v <= 1200) {
                resultList.add(v);
            }
        }
        //判断有无最优解
        for (Double aDouble : resultList) {
            if (aDouble % 10 == 0 || aDouble % 10 == 5) {
                HGlength = aDouble;
                break;
            }
        }
        //如果无最优解则取最大整数
        if (HGlength == 0) {
            HGlength = resultList.get(0);
        }
        double[] result = {HGnumberOfShards, HGlength};
        return result;
    }

}
