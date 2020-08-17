package com.Travel.biz.Feed.Dao;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageDao {
    List<String> addImages(MultipartFile[] multipartFiles);
    String addImage(MultipartFile multipartFile);
    byte[] getImages(List<String> images);
    byte[] getImage(String image);
}