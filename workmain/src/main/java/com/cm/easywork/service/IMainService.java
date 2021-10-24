package com.cm.easywork.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
 * @author: cm
 * @date: Created in 2021/10/20 14:31
 * @description:
 */
public interface IMainService {

    ResponseEntity baiYeProcessing(MultipartFile file) throws IOException;

    ResponseEntity lanGanProcessing(MultipartFile file) throws IOException;
}
