package com.cm.easywork.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    IMainService iMainService;

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }

    //百叶数据生成
    @PostMapping("/baiYeProducer")
    public ResponseEntity baiYeProducer(@RequestParam("file") MultipartFile file) throws IOException {

        ResponseEntity responseEntity = iMainService.baiYeProcessing(file);

        return responseEntity;

    }

    //栏杆数据生成
    @PostMapping("/lanGanProducer")
    public ResponseEntity lanGanProducer(@RequestParam("file") MultipartFile file) throws IOException {

        ResponseEntity responseEntity = iMainService.lanGanProcessing(file);

        return responseEntity;

    }
}
