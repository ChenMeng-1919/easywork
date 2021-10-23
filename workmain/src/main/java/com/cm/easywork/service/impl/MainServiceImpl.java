package com.cm.easywork.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.cm.easywork.commonutil.InfoUtils;
import com.cm.easywork.datalistener.BaiYeInputEntityListener;
import com.cm.easywork.entity.BaiYeEntity;
import com.cm.easywork.entity.BaiYeInputEntity;
import com.cm.easywork.service.IMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author: cm
 * @date: Created in 2021/10/22 11:17
 * @description:
 */
@Service
@Slf4j
public class MainServiceImpl implements IMainService {

    @Override
    public ResponseEntity baiYeProcessing(MultipartFile file) throws IOException {
        //创建解析监听器
        BaiYeInputEntityListener baiYeInputEntityListener = new BaiYeInputEntityListener();
        //通过监听器读取数据
        EasyExcel.read(file.getInputStream(), BaiYeInputEntity.class, baiYeInputEntityListener).sheet().doRead();
        //获取读取到的数据集合
        List<BaiYeInputEntity> baiYeInputEntitylist = baiYeInputEntityListener.getList();
        //准备填充数据
        List<BaiYeEntity> baiYeEntitylist = new ArrayList<>();

        for (BaiYeInputEntity baiYeInputEntity : baiYeInputEntitylist) {
            //提取本地变量
            String openWay = baiYeInputEntity.getOpenWay();
            double width = baiYeInputEntity.getWidth();
            double high = baiYeInputEntity.getHigh();
            double number = baiYeInputEntity.getNumber();

            //将面积等数据四舍五入
            DecimalFormat df = new DecimalFormat("#.00");

            if ("双开门".equals(openWay)) {
                BaiYeEntity baiYeEntity1 = new BaiYeEntity();
                baiYeEntity1.setWidth(width);
                baiYeEntity1.setHigh(high);
                baiYeEntity1.setNumber(number);
                baiYeEntity1.setFrameLength(width);
                baiYeEntity1.setFrameCount(number * 2);
                baiYeEntity1.setArea(Double.parseDouble(df.format(width * high * number * 0.000001)));
                baiYeEntitylist.add(baiYeEntity1);

                BaiYeEntity baiYeEntity2 = new BaiYeEntity();
                baiYeEntity2.setFrameLength(width - 40);
                baiYeEntity2.setFrameCount(number);
                baiYeEntitylist.add(baiYeEntity2);

                BaiYeEntity baiYeEntity3 = new BaiYeEntity();
                baiYeEntity3.setFrameLength(high - 40);
                baiYeEntity3.setFrameCount(number * 2);
                baiYeEntitylist.add(baiYeEntity3);

                BaiYeEntity baiYeEntity4 = new BaiYeEntity();
                baiYeEntity4.setFrameLength(width - 40 - 10);
                baiYeEntity4.setFrameCount(number * 4);
                baiYeEntitylist.add(baiYeEntity4);

                BaiYeEntity baiYeEntity5 = new BaiYeEntity();
                baiYeEntity5.setFrameLength((high - 40 - 120) / 2);
                baiYeEntity5.setFrameCount(number * 4);
                baiYeEntity5.setHoles(InfoUtils.getHolesSK(baiYeInputEntity)[0]);
                baiYeEntity5.setPosition(InfoUtils.getHolesSK(baiYeInputEntity)[1]);
                baiYeEntity5.setLeafLength(String.valueOf(width - 40 - 10 - 5));
                baiYeEntity5.setLeafCount(number * baiYeEntity5.getHoles() * 2);
                baiYeEntitylist.add(baiYeEntity5);

                if (width - 40 - 10 >= 1200) {
                    BaiYeEntity baiYeEntity6 = new BaiYeEntity();
                    baiYeEntity6.setFrameLength((high - 40 - 120) / 2);
                    baiYeEntity6.setFrameCount(number * 2);
                    baiYeEntity6.setHoles(InfoUtils.getHolesSK(baiYeInputEntity)[0]);
                    baiYeEntity6.setPosition(InfoUtils.getHolesSK(baiYeInputEntity)[1]);
                    baiYeEntity6.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity6);
                }
            }
            if ("固定".equals(openWay)) {
                BaiYeEntity baiYeEntity1 = new BaiYeEntity();
                baiYeEntity1.setWidth(width);
                baiYeEntity1.setHigh(high);
                baiYeEntity1.setNumber(number);
                baiYeEntity1.setFrameLength(width);
                baiYeEntity1.setFrameCount(number * 2);
                baiYeEntity1.setArea(width * high * number * 0.000001);
                baiYeEntitylist.add(baiYeEntity1);

                BaiYeEntity baiYeEntity2 = new BaiYeEntity();
                baiYeEntity2.setFrameLength(high - 40);
                baiYeEntity2.setFrameCount(number * 2);
                baiYeEntity2.setHoles(InfoUtils.getHolesGD(baiYeInputEntity)[0]);
                baiYeEntity2.setPosition(InfoUtils.getHolesGD(baiYeInputEntity)[1]);
                baiYeEntity2.setLeafLength(String.valueOf(width - 5));
                baiYeEntity2.setLeafCount(number * baiYeEntity2.getHoles());
                baiYeEntitylist.add(baiYeEntity2);

                if (width >= 1200) {
                    BaiYeEntity baiYeEntity3 = new BaiYeEntity();
                    baiYeEntity3.setFrameLength(high - 40);
                    baiYeEntity3.setFrameCount(number);
                    baiYeEntity3.setHoles(InfoUtils.getHolesGD(baiYeInputEntity)[0]);
                    baiYeEntity3.setPosition(InfoUtils.getHolesGD(baiYeInputEntity)[1]);
                    baiYeEntitylist.add(baiYeEntity3);
                }
            }
            if ("单开门".equals(openWay)) {
                BaiYeEntity baiYeEntity1 = new BaiYeEntity();
                baiYeEntity1.setWidth(width);
                baiYeEntity1.setHigh(high);
                baiYeEntity1.setNumber(number);
                baiYeEntity1.setFrameLength(width);
                baiYeEntity1.setFrameCount(number * 2);
                baiYeEntity1.setArea(width * high * number * 0.000001);
                baiYeEntitylist.add(baiYeEntity1);

                BaiYeEntity baiYeEntity2 = new BaiYeEntity();
                baiYeEntity2.setFrameLength(width - 40);
                baiYeEntity2.setFrameCount(number);
                baiYeEntitylist.add(baiYeEntity2);

                BaiYeEntity baiYeEntity3 = new BaiYeEntity();
                baiYeEntity3.setFrameLength(high - 40);
                baiYeEntity3.setFrameCount(number * 2);
                baiYeEntity3.setHoles(InfoUtils.getHolesDK1(baiYeInputEntity)[0]);
                baiYeEntity3.setPosition(InfoUtils.getHolesDK1(baiYeInputEntity)[1]);
                baiYeEntity3.setLeafLength(String.valueOf(width - 5));
                baiYeEntity3.setLeafCount(number * baiYeEntity3.getHoles());
                baiYeEntitylist.add(baiYeEntity3);

                if (width >= 1200) {
                    BaiYeEntity baiYeEntity4 = new BaiYeEntity();
                    baiYeEntity4.setFrameLength(high - 60 - baiYeInputEntity.getOpenHeight());
                    baiYeEntity4.setFrameCount(number);
                    baiYeEntity4.setHoles(InfoUtils.getHolesDK1(baiYeInputEntity)[0]);
                    baiYeEntity4.setPosition(InfoUtils.getHolesDK1(baiYeInputEntity)[1]);
                    baiYeEntity4.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity4);
                }

                BaiYeEntity baiYeEntity5 = new BaiYeEntity();
                baiYeEntity5.setFrameLength(width - 40 - 10);
                baiYeEntity5.setFrameCount(number * 2);
                baiYeEntitylist.add(baiYeEntity5);

                BaiYeEntity baiYeEntity6 = new BaiYeEntity();
                baiYeEntity6.setFrameLength(baiYeInputEntity.getOpenHeight() - 50);
                baiYeEntity6.setFrameCount(number * 2);
                baiYeEntity6.setHoles(InfoUtils.getHolesDK2(baiYeInputEntity)[0]);
                baiYeEntity6.setPosition(InfoUtils.getHolesDK2(baiYeInputEntity)[1]);
                baiYeEntity6.setLeafLength(String.valueOf(width - 40 - 10 - 5));
                baiYeEntity6.setLeafCount(number * baiYeEntity6.getHoles());
                baiYeEntitylist.add(baiYeEntity6);

                if (width >= 1200) {
                    BaiYeEntity baiYeEntity7 = new BaiYeEntity();
                    baiYeEntity7.setFrameLength(baiYeInputEntity.getOpenHeight() - 50);
                    baiYeEntity7.setFrameCount(number);
                    baiYeEntity7.setHoles(InfoUtils.getHolesDK2(baiYeInputEntity)[0]);
                    baiYeEntity7.setPosition(InfoUtils.getHolesDK2(baiYeInputEntity)[1]);
                    baiYeEntity7.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity7);
                }
            }
            if ("半开门".equals(openWay)) {
                BaiYeEntity baiYeEntity1 = new BaiYeEntity();
                baiYeEntity1.setWidth(width);
                baiYeEntity1.setHigh(high);
                baiYeEntity1.setNumber(number);
                baiYeEntity1.setFrameLength(width);
                baiYeEntity1.setFrameCount(number * 2);
                baiYeEntity1.setArea(width * high * number * 0.000001);
                baiYeEntitylist.add(baiYeEntity1);

                BaiYeEntity baiYeEntity2 = new BaiYeEntity();
                baiYeEntity2.setFrameLength(width - 40);
                baiYeEntity2.setFrameCount(number);
                baiYeEntitylist.add(baiYeEntity2);

                BaiYeEntity baiYeEntity3 = new BaiYeEntity();
                baiYeEntity3.setFrameLength(high - 40);
                baiYeEntity3.setFrameCount(number * 2);
                baiYeEntity3.setHoles(InfoUtils.getHolesBK1(baiYeInputEntity)[0]);
                baiYeEntity3.setPosition(InfoUtils.getHolesBK1(baiYeInputEntity)[1]);
                baiYeEntity3.setLeafLength(String.valueOf(width - 5));
                baiYeEntity3.setLeafCount(number * baiYeEntity3.getHoles());
                baiYeEntitylist.add(baiYeEntity3);

                if (width >= 1200) {
                    BaiYeEntity baiYeEntity4 = new BaiYeEntity();
                    baiYeEntity4.setFrameLength((high - 60) / 2);
                    baiYeEntity4.setFrameCount(number);
                    baiYeEntity4.setHoles(InfoUtils.getHolesBK1(baiYeInputEntity)[0]);
                    baiYeEntity4.setPosition(InfoUtils.getHolesBK1(baiYeInputEntity)[1]);
                    baiYeEntity4.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity4);
                }

                BaiYeEntity baiYeEntity5 = new BaiYeEntity();
                baiYeEntity5.setFrameLength(width - 40 - 10);
                baiYeEntity5.setFrameCount(number * 2);
                baiYeEntitylist.add(baiYeEntity5);

                BaiYeEntity baiYeEntity6 = new BaiYeEntity();
                baiYeEntity6.setFrameLength(((baiYeInputEntity.getOpenHeight() - 60) / 2) - 50);
                baiYeEntity6.setFrameCount(number * 2);
                baiYeEntity6.setHoles(InfoUtils.getHolesBK2(baiYeInputEntity)[0]);
                baiYeEntity6.setPosition(InfoUtils.getHolesBK2(baiYeInputEntity)[1]);
                baiYeEntity6.setLeafLength(String.valueOf(width - 40 - 10 - 5));
                baiYeEntity6.setLeafCount(number * baiYeEntity6.getHoles());
                baiYeEntitylist.add(baiYeEntity6);

                if (width >= 1200) {
                    BaiYeEntity baiYeEntity7 = new BaiYeEntity();
                    baiYeEntity7.setFrameLength(((baiYeInputEntity.getOpenHeight() - 60) / 2) - 50);
                    baiYeEntity7.setFrameCount(number);
                    baiYeEntity7.setHoles(InfoUtils.getHolesBK2(baiYeInputEntity)[0]);
                    baiYeEntity7.setPosition(InfoUtils.getHolesBK2(baiYeInputEntity)[1]);
                    baiYeEntity7.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity7);
                }
            }
            if ("全开门".equals(openWay)) {
                BaiYeEntity baiYeEntity1 = new BaiYeEntity();
                baiYeEntity1.setWidth(width);
                baiYeEntity1.setHigh(high);
                baiYeEntity1.setNumber(number);
                baiYeEntity1.setFrameLength(width);
                baiYeEntity1.setFrameCount(number * 2);
                baiYeEntity1.setArea(width * high * number * 0.000001);
                baiYeEntitylist.add(baiYeEntity1);

                BaiYeEntity baiYeEntity2 = new BaiYeEntity();
                baiYeEntity2.setFrameLength(high - 40);
                baiYeEntity2.setFrameCount(number * 2);
                baiYeEntitylist.add(baiYeEntity2);

                BaiYeEntity baiYeEntity3 = new BaiYeEntity();
                baiYeEntity3.setFrameLength(width - 50);
                baiYeEntity3.setFrameCount(number * 2);
                baiYeEntitylist.add(baiYeEntity3);

                BaiYeEntity baiYeEntity4 = new BaiYeEntity();
                baiYeEntity4.setFrameLength(width - 40 - 50);
                baiYeEntity4.setFrameCount(number * 2);
                baiYeEntity4.setHoles(InfoUtils.getHolesQK(baiYeInputEntity)[0]);
                baiYeEntity4.setPosition(InfoUtils.getHolesQK(baiYeInputEntity)[1]);
                baiYeEntity4.setLeafLength(String.valueOf(width - 50 - 5));
                baiYeEntity4.setFrameCount(number * 2);
                baiYeEntitylist.add(baiYeEntity4);

                if (width - 50 >= 1200) {
                    BaiYeEntity baiYeEntity5 = new BaiYeEntity();
                    baiYeEntity5.setFrameLength(width - 40 - 50);
                    baiYeEntity5.setFrameCount(number);
                    baiYeEntity5.setHoles(InfoUtils.getHolesQK(baiYeInputEntity)[0]);
                    baiYeEntity5.setPosition(InfoUtils.getHolesQK(baiYeInputEntity)[1]);
                    baiYeEntity5.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity5);
                }
            }
            if ("左右开门".equals(openWay)) {
                double openLength = Double.parseDouble(baiYeInputEntity.getOpenLength());
                BaiYeEntity baiYeEntity1 = new BaiYeEntity();
                baiYeEntity1.setWidth(width);
                baiYeEntity1.setHigh(high);
                baiYeEntity1.setNumber(number);
                baiYeEntity1.setFrameLength(width);
                baiYeEntity1.setFrameCount(number * 2);
                baiYeEntity1.setArea(width * high * number * 0.000001);
                baiYeEntitylist.add(baiYeEntity1);

                BaiYeEntity baiYeEntity2 = new BaiYeEntity();
                baiYeEntity2.setFrameLength(high - 40);
                baiYeEntity2.setFrameCount(number);
                baiYeEntitylist.add(baiYeEntity2);

                BaiYeEntity baiYeEntity3 = new BaiYeEntity();
                baiYeEntity3.setFrameLength(width - 40);
                baiYeEntity3.setFrameCount(number * 2);
                baiYeEntity3.setHoles(InfoUtils.getHolesZYK1(baiYeInputEntity)[0]);
                baiYeEntity3.setPosition(InfoUtils.getHolesZYK1(baiYeInputEntity)[1]);
                baiYeEntity3.setLeafLength(String.valueOf(width - openLength - 20 - 5));
                baiYeEntity3.setLeafCount(number * baiYeEntity3.getHoles());
                baiYeEntitylist.add(baiYeEntity3);

                BaiYeEntity baiYeEntity4 = new BaiYeEntity();
                baiYeEntity4.setFrameLength(openLength - 10);
                baiYeEntity4.setFrameCount(number * 2);
                baiYeEntitylist.add(baiYeEntity4);

                BaiYeEntity baiYeEntity5 = new BaiYeEntity();
                baiYeEntity5.setFrameLength(high - 40 - 50);
                baiYeEntity5.setFrameCount(number * 2);
                baiYeEntity5.setHoles(InfoUtils.getHolesZYK2(baiYeInputEntity)[0]);
                baiYeEntity5.setPosition(InfoUtils.getHolesZYK2(baiYeInputEntity)[1]);
                baiYeEntity5.setLeafLength(String.valueOf(openLength - 10 - 5));
                baiYeEntity5.setLeafCount(number * baiYeEntity3.getHoles());
                baiYeEntitylist.add(baiYeEntity5);

                if (openLength - 10 >= 1200) {
                    BaiYeEntity baiYeEntity6 = new BaiYeEntity();
                    baiYeEntity6.setFrameLength(high - 40 - 50);
                    baiYeEntity6.setFrameCount(number);
                    baiYeEntity6.setHoles(InfoUtils.getHolesZYK2(baiYeInputEntity)[0]);
                    baiYeEntity6.setPosition(InfoUtils.getHolesZYK2(baiYeInputEntity)[1]);
                    baiYeEntity6.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity6);
                }
            }
            if ("左右对开门".equals(openWay)) {
                double openLength = Double.parseDouble(baiYeInputEntity.getOpenLength());
                
                BaiYeEntity baiYeEntity1 = new BaiYeEntity();
                baiYeEntity1.setWidth(width);
                baiYeEntity1.setHigh(high);
                baiYeEntity1.setNumber(number);
                baiYeEntity1.setFrameLength(width);
                baiYeEntity1.setFrameCount(number * 2);
                baiYeEntity1.setArea(width * high * number * 0.000001);
                baiYeEntitylist.add(baiYeEntity1);

                BaiYeEntity baiYeEntity2 = new BaiYeEntity();
                baiYeEntity2.setFrameLength(high - 40);
                baiYeEntity2.setFrameCount(number * 3);
                baiYeEntitylist.add(baiYeEntity2);

                BaiYeEntity baiYeEntity3 = new BaiYeEntity();
                baiYeEntity3.setFrameLength((width - 60) / 2 - 10);
                baiYeEntity3.setFrameCount(number * 4);
                baiYeEntitylist.add(baiYeEntity3);

                BaiYeEntity baiYeEntity4 = new BaiYeEntity();
                baiYeEntity4.setFrameLength(high - 40 - 50);
                baiYeEntity4.setFrameCount(number * 4);
                baiYeEntity4.setHoles(InfoUtils.getHolesZYK2(baiYeInputEntity)[0]);
                baiYeEntity4.setPosition(InfoUtils.getHolesZYK2(baiYeInputEntity)[1]);
                baiYeEntity4.setLeafLength(String.valueOf((width - 60) / 2 - 10 - 5));
                baiYeEntity4.setLeafCount(number * baiYeEntity3.getHoles());
                baiYeEntitylist.add(baiYeEntity4);

                if ((openLength - 60) / 2 - 10 >= 1200) {
                    BaiYeEntity baiYeEntity5 = new BaiYeEntity();
                    baiYeEntity5.setFrameLength(high - 40 - 50);
                    baiYeEntity5.setFrameCount(number * 2);
                    baiYeEntity5.setHoles(InfoUtils.getHolesZYK2(baiYeInputEntity)[0]);
                    baiYeEntity5.setPosition(InfoUtils.getHolesZYK2(baiYeInputEntity)[1]);
                    baiYeEntity5.setLeafLength("通孔");
                    baiYeEntitylist.add(baiYeEntity5);
                }
            }
        }

        log.info("数据解析成功");
        //获取模板文件的输入流
        String templateName = "融创云湖十里下料单-模板.xlsx";
        BaiYeInputEntity baiYeInputFirstEntity = baiYeInputEntitylist.get(0);
        Map<String, Object> otherInfoMap = new HashMap<String, Object>();
        otherInfoMap.put("project", baiYeInputFirstEntity.getProject());
        otherInfoMap.put("colour", baiYeInputFirstEntity.getColour());
        otherInfoMap.put("date", baiYeInputFirstEntity.getDate());
        otherInfoMap.put("frame", baiYeInputFirstEntity.getFrame());
        otherInfoMap.put("blade", baiYeInputFirstEntity.getBlade());

        double sumNumber = baiYeEntitylist.stream().mapToDouble(t -> t.getNumber()).sum();
        double sumLeafCount = baiYeEntitylist.stream().mapToDouble(t -> t.getLeafCount()).sum();
        double sumArea = baiYeEntitylist.stream().mapToDouble(t -> t.getArea()).sum();
        otherInfoMap.put("sumNumber", sumNumber);
        otherInfoMap.put("sumLeafCount", sumLeafCount);
        otherInfoMap.put("sumArea", sumArea);
        ResponseEntity responseEntity = this.downFileResMake(templateName, baiYeEntitylist, otherInfoMap);
        return responseEntity;
    }

    private <E> ResponseEntity downFileResMake(String templateName, List<E> entityList, Map<String, Object> otherInfoMap) throws IOException {
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
}
