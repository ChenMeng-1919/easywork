package com.cm.easywork.commonutil;

import com.cm.easywork.entity.BaiYeInputEntity;

/*
 * @author: cm
 * @date: Created in 2021/10/20 19:17
 * @description:
 */
public class InfoUtils {
    //双开门计算孔数和定位
    public static double[] getHolesSK(BaiYeInputEntity baiYeInputEntity) {
        double[] result = new double[2];
        for (int i = 0; i < 100; i++) {
            double v = (((baiYeInputEntity.getHigh() - 40 - 120) / 2) - i * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
            if (v < 0) {
                result[0] = i - 1;
                result[1] = (((baiYeInputEntity.getHigh() - 40 - 120) / 2) - (i - 1) * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
                break;
            }
        }
        return result;
    }

    //固定计算孔数和定位
    public static double[] getHolesGD(BaiYeInputEntity baiYeInputEntity) {
        double[] result = new double[2];
        for (int i = 0; i < 100; i++) {
            double v = ((baiYeInputEntity.getHigh() - 40) - i * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
            if (v < 0) {
                result[0] = i - 1;
                result[1] = ((baiYeInputEntity.getHigh() - 40) - (i - 1) * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
                break;
            }
        }
        return result;
    }

    //单开上计算孔数和定位
    public static double[] getHolesDK1(BaiYeInputEntity baiYeInputEntity) {
        double[] result = new double[2];
        for (int i = 0; i < 100; i++) {
            double v = ((baiYeInputEntity.getHigh() - 60 - baiYeInputEntity.getOpenHeight()) - i * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
            if (v < 0) {
                result[0] = i - 1;
                result[1] = ((baiYeInputEntity.getHigh() - 60 - baiYeInputEntity.getOpenHeight()) - (i - 1) * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
                break;
            }
        }
        return result;
    }

    //单开下计算孔数和定位
    public static double[] getHolesDK2(BaiYeInputEntity baiYeInputEntity) {
        double[] result = new double[2];
        for (int i = 0; i < 100; i++) {
            double v = ((baiYeInputEntity.getOpenHeight() - 50) - i * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
            if (v < 0) {
                result[0] = i - 1;
                result[1] = ((baiYeInputEntity.getOpenHeight() - 50) - (i - 1) * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
                break;
            }
        }
        return result;
    }
}
