package com.cm.easywork.controller;

import com.alibaba.excel.EasyExcel;
import com.cm.easywork.datalistener.BaiYeEntityListener;
import com.cm.easywork.entity.BaiYeEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
 * @author: cm
 * @date: Created in 2021/10/20 14:29
 * @description:
 */
@Controller
public class MainController {

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }

    //百叶数据生成
    @PostMapping("/baiYeProducer")
    public ResponseEntity baiYeProducer(@RequestParam("file") MultipartFile file) throws IOException {
        BaiYeEntityListener baiYeEntityListener = new BaiYeEntityListener();
        EasyExcel.read(file.getInputStream(), BaiYeEntity.class, baiYeEntityListener).sheet().doRead();
        List<BaiYeEntity> list = baiYeEntityListener.getList();

        //ResponseEntity<byte[]> responseEntity = fileUDService.checkAccount(files);
        //return responseEntity;
        return null;
    }
}
