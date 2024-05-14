package com.hoangtien2k3.movieapi.service.movie;

import com.hoangtien2k3.movieapi.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws Exception {
        //get name of the file
        String fileName = file.getOriginalFilename();

        // to get the file path
        String filePath = path + File.separator + fileName; // File.separator: hằng số ngăn cách đường dẫn: vd: '\\' hoặc '/' tuỳ vào hệ điều hành

        // create file object
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs(); // create file object
        }

        // copy the file or update file to the path
        Files.copy(file.getInputStream(), Paths.get(filePath)); // Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String name) throws FileNotFoundException {
        String filePath = path + File.separator + name;
        return new FileInputStream(filePath);
    }
}
