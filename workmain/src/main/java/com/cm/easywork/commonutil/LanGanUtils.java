package com.cm.easywork.commonutil;

import com.cm.easywork.entity.LanGanInputEntity;

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
        for (int i = 0; i < 50; i++) {
            double v = (lanGanInputEntity.getLength() - 100 - Double.parseDouble(splitArgsList.get(3)[0]) * (i + 1)) / i;
            if (v >= 1200) {
                double v2 = (lanGanInputEntity.getLength() - 100 - Double.parseDouble(splitArgsList.get(3)[0]) * (i + 1-1)) / (i-1);
                HGnumberOfShards = i-1;
                break;

            }
        }
        for (double j = 100; j > 90; j -= 0.5) {
            double v = (lanGanInputEntity.getLength() - 100 - Double.parseDouble(splitArgsList.get(3)[0]) * (HGnumberOfShards + 1)) / HGnumberOfShards;
            if (v % 10 == 0 || v % 10 == 5) {
                HGlength = v;
                break;
            }
        }
        double[] result = {HGnumberOfShards, HGlength};
        return result;
    }

}
