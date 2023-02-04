package com.project.electronic.store.service;

import com.project.electronic.store.dto.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {
    FileResponse uploadFile(MultipartFile file,Path path);

    Resource getFileResource(Path path);
}
