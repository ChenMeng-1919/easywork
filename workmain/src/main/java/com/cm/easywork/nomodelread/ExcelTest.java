package com.cm.easywork.nomodelread;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author: cm
 * @since: Created in 2022/11/25 14:39
 * @description:
 */
@Slf4j
public class ExcelTest {
    public static void main(String[] args) throws FileNotFoundException {

        //spring boot获取类路径 获取当前类路径
        String springbooPath1 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println("类路径springbooPath1:"+springbooPath1);
        String springbooPath2 = ResourceUtils.getURL("classpath:").getPath();
        System.out.println("类路径springbooPath2:"+springbooPath2);
        //环境变量中的属性
        Properties properties = System.getProperties();
        //jar包所在的路径
        String dir = properties.getProperty("user.dir");
        System.out.println("项目路径dir:"+dir);
        String realPath = properties.getProperty("uploadFilePath");
        System.out.println("uploadFilePath:"+realPath);

        String oldReportFileName = File.separator + "exceltemplate" + File.separator + "原有报表.xlsx";
        String newReportFileName = File.separator + "exceltemplate" + File.separator + "新报表.xlsx";
        String path = null;
        try {
            path = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String newReportPath = new ClassPathResource(newReportFileName).getPath();
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        NoModelDataListener oldReport = new NoModelDataListener();
        NoModelDataListener newReport = new NoModelDataListener();
        EasyExcel.read(path + oldReportFileName, oldReport).sheet().doRead();
        EasyExcel.read(path + newReportFileName, newReport).sheet().doRead();
        List<Map<Integer, String>> cachedDataList = oldReport.cachedDataList;
        List<Map<Integer, String>> cachedDataList1 = newReport.cachedDataList;
        Set<String> indicators = new HashSet<>();
        for (Map<Integer, String> integerStringMap : cachedDataList) {
            for (int i = 1; i < integerStringMap.size(); i++) {
                indicators.add(integerStringMap.get(i));
            }
        }
        for (Map<Integer, String> integerStringMap : cachedDataList1) {
            for (int i = 1; i < integerStringMap.size(); i++) {
                String o = integerStringMap.get(i);
                if (!indicators.contains(o)) {
                    log.info("{}新增了指标:{}", integerStringMap.get(0), o);
                }
            }
        }
    }
}
