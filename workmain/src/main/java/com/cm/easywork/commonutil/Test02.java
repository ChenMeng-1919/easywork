package com.cm.easywork.commonutil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/*
 * @author: cm
 * @date: Created in 2021/10/24 15:30
 * @description:
 */
public class Test02 {
    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(3.585);
       /* DecimalFormat df = new DecimalFormat("#.##");
        String format = df.format(a);
        double v = Double.parseDouble(format);
        System.out.println(v);*/


        BigDecimal  bd2 = a.setScale(2,BigDecimal.ROUND_HALF_UP);
        double v1 = Double.parseDouble(String.valueOf(bd2));
        System.out.println(v1);
    }
}
