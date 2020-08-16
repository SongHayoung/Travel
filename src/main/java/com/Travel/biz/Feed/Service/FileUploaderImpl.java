package com.Travel.biz.Feed.Service;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:/properties/fileUploader.properties")
public class FileUploaderImpl implements FileUploader {
    @Value("${fileUploader.path}")
    private static String fileName;
    private final Logger logger = LoggerFactory.getLogger(FileUploaderImpl.class);
    
    @Override
    public List<String> uploadFiles(MultipartFile[] multipartFiles) {
        List<String> uploadResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles)
            uploadResult.add(uploadFile(multipartFile));
        return uploadResult;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        logger.info("upload File");
        Objects.requireNonNull(multipartFile, "image is null");
        try (InputStream inputStream = multipartFile.getInputStream();
             OutputStream out = new FileOutputStream(new File(fileName + multipartFile.getName()))) {
            out.write(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return fileName + multipartFile.getName();
    }
}
