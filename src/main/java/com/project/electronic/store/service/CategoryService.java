package com.project.electronic.store.service;

import com.project.electronic.store.dto.CategoryDto;
import com.project.electronic.store.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    CategoryDto getCategoryById(String id);

    CategoryDto update(String id,CategoryDto categoryDto);

    List<CategoryDto> getAll();

    void delete(String id);

    FileResponse uploadImage(String id, MultipartFile file);

    byte[] getCategoryResources(String id);

}
