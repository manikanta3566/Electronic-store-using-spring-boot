package com.project.electronic.store.service;

import com.project.electronic.store.dto.FileResponse;
import com.project.electronic.store.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto productDto);

    ProductDto createProductWithCategory(String categoryId,ProductDto productDto);

    ProductDto getProductById(String id);

    List<ProductDto> getAll();

    List<ProductDto> getAllProductsByCategory(String categoryId);

    ProductDto update(String id,ProductDto productDto);

    ProductDto updateProductWithCategory(String productId,String categoryId);

    void delete(String id);

    List<ProductDto> search(String keyword);

    FileResponse uploadImage(String id, MultipartFile file);

    byte[] getProductResources(String id);
}
