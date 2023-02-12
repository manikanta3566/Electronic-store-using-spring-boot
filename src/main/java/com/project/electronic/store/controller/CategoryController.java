package com.project.electronic.store.controller;

import com.project.electronic.store.dto.CategoryDto;
import com.project.electronic.store.dto.GenericResponse;
import com.project.electronic.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/v0/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponse> create(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(new GenericResponse<>(categoryService.create(categoryDto), HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAll() {
        return new ResponseEntity<>(new GenericResponse<>(categoryService.getAll()), HttpStatus.OK);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<GenericResponse> getDetailsById(@PathVariable String categoryId) {
        return new ResponseEntity<>(new GenericResponse<>(categoryService.getCategoryById(categoryId)), HttpStatus.OK);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<GenericResponse> update(@PathVariable String categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(new GenericResponse<>(categoryService.update(categoryId, categoryDto)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("{categoryId}")
    public ResponseEntity<GenericResponse> delete(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        return new ResponseEntity<>(new GenericResponse<>("deleted successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/images/{categoryId}")
    public ResponseEntity<GenericResponse> uploadImage(@PathVariable String categoryId, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(new GenericResponse<>(categoryService.uploadImage(categoryId, file)), HttpStatus.OK);
    }

    @GetMapping(value = "/images/{categoryId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> serveImage(@PathVariable String categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryResources(categoryId), HttpStatus.OK);
    }
}
