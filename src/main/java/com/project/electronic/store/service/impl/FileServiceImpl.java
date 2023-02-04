package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.FileResponse;
import com.project.electronic.store.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Log4j2
public class FileServiceImpl implements FileService {
    @Override
    public FileResponse uploadFile(MultipartFile file, Path path) {
        try {
            Files.createDirectories(path);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return new FileResponse(String.valueOf(path.getFileName()), file.getSize(), file.getContentType());
        } catch (Exception e) {
            log.error("error while uploading file {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Resource getFileResource(Path path) {
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            log.error("error while reading resources from file {}", e.getMessage());
        }
        return resource;
    }
}
