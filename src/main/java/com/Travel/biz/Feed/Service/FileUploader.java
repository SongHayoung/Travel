package com.Travel.biz.Feed.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploader {
    List<String> uploadFiles(MultipartFile[] multipartFiles);
    String uploadFile(MultipartFile multipartFile);
}