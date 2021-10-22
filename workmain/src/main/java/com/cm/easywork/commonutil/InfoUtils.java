package com.cm.easywork.commonutil;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.cm.easywork.entity.BaiYeInputEntity;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //半开上计算孔数和定位
    public static double[] getHolesBK1(BaiYeInputEntity baiYeInputEntity) {
        double[] result = new double[2];
        for (int i = 0; i < 100; i++) {
            double v = (((baiYeInputEntity.getOpenHeight() - 60) / 2) - i * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
            if (v < 0) {
                result[0] = i - 1;
                result[1] = (((baiYeInputEntity.getOpenHeight() - 60) / 2) - (i - 1) * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
                break;
            }
        }
        return result;
    }

    //半开下计算孔数和定位
    public static double[] getHolesBK2(BaiYeInputEntity baiYeInputEntity) {
        double[] result = new double[2];
        for (int i = 0; i < 100; i++) {
            double v = ((((baiYeInputEntity.getOpenHeight() - 60) / 2) - 50) - i * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
            if (v < 0) {
                result[0] = i - 1;
                result[1] = ((((baiYeInputEntity.getOpenHeight() - 60) / 2) - 50) - (i - 1) * (75 + baiYeInputEntity.getLouverSpacing()) + baiYeInputEntity.getLouverSpacing()) / 2;
                break;
            }
        }
        return result;
    }

    public static <E> ResponseEntity downFileResMake(String templateName, List<E> entityList, HashMap<String, String> otherInfoMap) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("exceltemplate" + File.separator + templateName);
        InputStream inputStream = classPathResource.getInputStream();

        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量

        //创建回传文件的输出流
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        //整合输入输出流，构建对象
        ExcelWriter excelWriter = EasyExcel.write(outByteStream).withTemplate(inputStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        //填充数据
        excelWriter.fill(entityList, fillConfig, writeSheet);
        //表头合计等其他信息填充


        //强行用泛型方法凑数
        E baiYeInputFirstEntity = entityList.get(0);

        excelWriter.fill(otherInfoMap, writeSheet);

        //关闭流对象
        excelWriter.finish();
        outByteStream.close();
        //回传文件
        HttpHeaders headers = new HttpHeaders();
        //通过ResponseEntity对象完成文件下载，需要注意的是writer对象生成的格式与导出文件的拓展名对应，否则就会提示无法打开文件，拓展名无效
        //在响应头中设置下载默认的名称
        headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode("融创云湖十里下料单-模板" + ".xlsx", "UTF-8"));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.valueOf("application/vnd.ms-excel;charset=utf-8"))
                .body(outByteStream.toByteArray());
    }

    //泛型方法实例
    public static <T> List<T> downFileResMake2( T entity) {
        ArrayList<T> list = new ArrayList<>();
        return list;
    }
}
