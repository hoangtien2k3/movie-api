package com.hoangtien2k3.movieapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileService {

    String uploadFile(String path, MultipartFile file) throws Exception;
    InputStream getResourceFile(String path, String name) throws FileNotFoundException;

}
