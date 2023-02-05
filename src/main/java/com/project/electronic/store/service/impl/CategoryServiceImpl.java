package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.CategoryDto;
import com.project.electronic.store.dto.FileResponse;
import com.project.electronic.store.entity.Category;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.CategoryRepository;
import com.project.electronic.store.service.CategoryService;
import com.project.electronic.store.service.FileService;
import com.project.electronic.store.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Value("${image.path.category}")
    private String basePath;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = new Category();
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category category1 = categoryRepository.save(category);
        return modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(String id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        if (Objects.nonNull(category.getCoverImagePath())) {
            try {
                Path path = Paths.get(category.getCoverImagePath());
                Files.delete(path);
            } catch (Exception e) {
                log.error("error while deleting category image {}", e.getMessage());
                throw new GlobalException("failed to delete category ", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED);
            }
        }
        categoryRepository.delete(category);
    }

    @Override
    public FileResponse uploadImage(String id, MultipartFile file) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        String extension =CommonUtil.getExtensionFromFile(file);
        if (!CommonUtil.imageFileExtensions.contains(extension)) {
            throw new GlobalException("file extension " + extension + " not allowed", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED);
        }
        String fileName =CommonUtil.getFileNameWithTimestamp(file)+ extension;
        Path path = Paths.get(basePath).resolve(fileName);
        category.setCoverImagePath(String.valueOf(path));
        categoryRepository.save(category);
        return fileService.uploadFile(file, path);
    }

    @Override
    public byte[] getCategoryResources(String id) {
        byte[] bytes = null;
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
            Resource fileResource = fileService.getFileResource(Paths.get(category.getCoverImagePath()));
            bytes = FileCopyUtils.copyToByteArray(fileResource.getInputStream());
        } catch (Exception e) {
            log.error("error while reading category image resources");
        }
        return bytes;
    }
}
