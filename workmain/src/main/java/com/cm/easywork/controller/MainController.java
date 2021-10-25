package com.cm.easywork.controller;

import com.cm.easywork.service.IMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
