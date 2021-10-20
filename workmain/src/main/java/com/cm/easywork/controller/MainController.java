package com.cm.easywork.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.cm.easywork.datalistener.BaiYeInputEntityListener;
import com.cm.easywork.entity.BaiYeEntity;
import com.cm.easywork.entity.BaiYeInputEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        BaiYeInputEntityListener baiYeInputEntityListener = new BaiYeInputEntityListener();
        EasyExcel.read(file.getInputStream(), BaiYeInputEntity.class, baiYeInputEntityListener).sheet().doRead();
        List<BaiYeInputEntity> baiYeInputEntitylist = baiYeInputEntityListener.getList();
        log.info("数据解析成功");
        ClassPathResource classPathResource = new ClassPathResource("exceltemplate/融创云湖十里下料单-模板.xlsx");
        InputStream inputStream =classPathResource.getInputStream();
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outByteStream).withTemplate(inputStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        List<BaiYeEntity> baiYeEntitylist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BaiYeEntity baiYeEntity = new BaiYeEntity();
            baiYeEntity.setWidth(1.0D);
            baiYeEntity.setHigh(2.0D);
            baiYeEntity.setNumber(3.0D);
            baiYeEntity.setFrameLength(4.0D);
            baiYeEntity.setFrameCount(5.0D);
            baiYeEntity.setHoles(6.0D);
            baiYeEntity.setPosition(7.0D);
            baiYeEntity.setLeafLength(8.0D);
            baiYeEntity.setLeafCount(9.0D);
            baiYeEntity.setArea(10.0D);
            baiYeEntity.setRemark("1111");
            baiYeEntitylist.add(baiYeEntity);
        }
        excelWriter.fill(baiYeEntitylist, fillConfig, writeSheet);
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        map.put("total", 1000);
        excelWriter.fill(map, writeSheet);*/
        excelWriter.finish();
        outByteStream.close();
        //回传文件
        HttpHeaders headers = new HttpHeaders();
        //通过ResponseEntity对象完成文件下载，需要注意的是writer对象生成的格式与导出文件的拓展名对应，否则就会提示无法打开文件，拓展名无效
        //在响应头中设置下载默认的名称
        headers.add("Content-Disposition", "attachment;filename="  + URLEncoder.encode("融创云湖十里下料单-模板" + ".xlsx", "UTF-8"));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.valueOf("application/vnd.ms-excel;charset=utf-8"))
                .body(outByteStream.toByteArray());
    }
}
