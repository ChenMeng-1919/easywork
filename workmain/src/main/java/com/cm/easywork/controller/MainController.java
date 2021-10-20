package com.cm.easywork.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.cm.easywork.commonutil.InfoUtils;
import com.cm.easywork.datalistener.BaiYeInputEntityListener;
import com.cm.easywork.entity.BaiYeEntity;
import com.cm.easywork.entity.BaiYeInputEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
 * @author: cm
 * @date: Created in 2021/10/20 14:29
 * @description:
 */
@Controller
@Slf4j
public class MainController {

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }

    //百叶数据生成
    @PostMapping("/baiYeProducer")
    public ResponseEntity baiYeProducer(@RequestParam("file") MultipartFile file) throws IOException {
        //创建解析监听器
        BaiYeInputEntityListener baiYeInputEntityListener = new BaiYeInputEntityListener();
        //通过监听器读取数据
        EasyExcel.read(file.getInputStream(), BaiYeInputEntity.class, baiYeInputEntityListener).sheet().doRead();
        //准备填充数据
        List<BaiYeEntity> baiYeEntitylist = new ArrayList<>();
        //获取读取到的数据集合
        List<BaiYeInputEntity> baiYeInputEntitylist = baiYeInputEntityListener.getList();
        for (BaiYeInputEntity baiYeInputEntity : baiYeInputEntitylist) {
            BaiYeEntity baiYeEntity1 = new BaiYeEntity();
            baiYeEntity1.setWidth(baiYeInputEntity.getWidth());
            baiYeEntity1.setHigh(baiYeInputEntity.getHigh());
            baiYeEntity1.setNumber(baiYeInputEntity.getNumber());
            baiYeEntity1.setFrameLength(baiYeInputEntity.getWidth());
            baiYeEntity1.setFrameCount(baiYeInputEntity.getNumber() * 2);
            baiYeEntity1.setArea(baiYeInputEntity.getWidth() * baiYeInputEntity.getHigh() * baiYeInputEntity.getNumber() * 0.000001);
            baiYeEntitylist.add(baiYeEntity1);

            BaiYeEntity baiYeEntity2 = new BaiYeEntity();
            baiYeEntity2.setFrameLength(baiYeInputEntity.getWidth() - 40);
            baiYeEntity2.setFrameCount(baiYeInputEntity.getNumber());
            baiYeEntitylist.add(baiYeEntity2);

            BaiYeEntity baiYeEntity3 = new BaiYeEntity();
            baiYeEntity3.setFrameLength(baiYeInputEntity.getHigh() - 40);
            baiYeEntity3.setFrameCount(baiYeInputEntity.getNumber() * 2);
            baiYeEntitylist.add(baiYeEntity3);

            BaiYeEntity baiYeEntity4 = new BaiYeEntity();
            baiYeEntity4.setFrameLength(baiYeInputEntity.getWidth() - 50);
            baiYeEntity4.setFrameCount(baiYeInputEntity.getNumber() * 4);
            baiYeEntitylist.add(baiYeEntity4);

            BaiYeEntity baiYeEntity5 = new BaiYeEntity();
            baiYeEntity5.setFrameLength((baiYeInputEntity.getHigh() - 160) / 2);
            baiYeEntity5.setFrameCount(baiYeInputEntity.getNumber() * 4);
            baiYeEntity5.setHoles(InfoUtils.getHoles(baiYeInputEntity)[0]);
            baiYeEntity5.setPosition(InfoUtils.getHoles(baiYeInputEntity)[1]);
            baiYeEntity5.setLeafLength(baiYeInputEntity.getWidth() - 55);
            baiYeEntity5.setLeafCount(baiYeInputEntity.getNumber() * baiYeEntity5.getHoles() * 2);
            baiYeEntitylist.add(baiYeEntity5);
        }


        log.info("数据解析成功");
        //获取模板文件的输入流
        ClassPathResource classPathResource = new ClassPathResource("exceltemplate/融创云湖十里下料单-模板.xlsx");
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
        excelWriter.fill(baiYeEntitylist, fillConfig, writeSheet);
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        map.put("total", 1000);
        excelWriter.fill(map, writeSheet);*/

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
