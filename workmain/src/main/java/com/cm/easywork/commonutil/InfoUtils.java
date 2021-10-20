package com.cm.easywork.commonutil;

import com.cm.easywork.entity.BaiYeInputEntity;

/*
 * @author: cm
 * @date: Created in 2021/10/20 19:17
 * @description:
 */
public class InfoUtils {
    //
    public static double[] getHoles(BaiYeInputEntity baiYeInputEntity) {
        double[] result = new double[2];
        for (int i = 0; i < 100; i++) {
            double v = (((baiYeInputEntity.getHigh() - 160) / 2) - i * 145 + 70) / 2;
            if (v < 0) {
                result[0] = i - 1;
                result[1] = (((baiYeInputEntity.getHigh() - 160) / 2) - (i-1) * 145 + 70) / 2;;
                break;
            }
        }
        return result;
    }
}
