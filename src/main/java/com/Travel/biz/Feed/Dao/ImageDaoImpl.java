package com.Travel.biz.Feed.Dao;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@PropertySource("classpath:/properties/fileUploader.properties")
public class ImageDaoImpl implements ImageDao {
    @Value("${fileUploader.path}")
    private static String fileName;
    private static final byte[] boundary = "\r\n\r\n".getBytes();
    private final Logger logger = LoggerFactory.getLogger(ImageDaoImpl.class);

    @Override
    public List<String> addImages(MultipartFile[] multipartFiles) {
        List<String> uploadResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles)
            uploadResult.add(addImage(multipartFile));
        return uploadResult;
    }

    @Override
    public String addImage(MultipartFile multipartFile) {
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

    @Override
    public byte[] getImages(List<String> images) {
        byte[] imagesByteArray = new byte[0];
        for(String image : images)
            imagesByteArray = ArrayUtils.addAll(imagesByteArray, ArrayUtils.addAll(getImage(image), boundary));
        return imagesByteArray;
    }

    @Override
    public byte[] getImage(String image) {
        try(InputStream inputStream = getClass().getResourceAsStream(image);){
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }
}
