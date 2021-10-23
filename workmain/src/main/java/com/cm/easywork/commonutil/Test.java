package com.cm.easywork.commonutil;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

/*
 * @author: cm
 * @date: Created in 2021/10/22 10:45
 * @description:
 */
@Slf4j
public class Test {
    public static void main(String[] args) {

        List<Integer> strs = Arrays.asList(1, 2, 3, 4, 5, 6);

        //方式一：求和
        Optional<Integer> sum = strs.stream().reduce((x, y) -> x + y);
        System.out.println("reduce 求和方式1:" + sum.get());

        //方式二：求和
        Integer sum2 = strs.stream().reduce(0, (x, y) -> x + y);
        System.out.println("reduce 求和方式2:" + sum2);

        //方式三：求和
        Integer sum3 = strs.stream().mapToInt((x) -> x).sum();
        System.out.println("mapToInt+ sum求和方式:" + sum3);
        //方式四：求和
        DoubleSummaryStatistics doubleSummaryStatistics = strs.stream().mapToDouble(t -> t).summaryStatistics();
        log.info("{}", doubleSummaryStatistics.getAverage());
        log.info("{}", doubleSummaryStatistics.getMax());
        log.info("{}",  doubleSummaryStatistics.getMin());
        log.info("{}", doubleSummaryStatistics.getSum());
    }
}
