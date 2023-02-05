package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.FileResponse;
import com.project.electronic.store.dto.ProductDto;
import com.project.electronic.store.entity.Category;
import com.project.electronic.store.entity.Product;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.CategoryRepository;
import com.project.electronic.store.repository.ProductRepository;
import com.project.electronic.store.service.FileService;
import com.project.electronic.store.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${image.path.product}")
    private String basePath;

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(String categoryId, ProductDto productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GlobalException("product not found with given id!!", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        List<Product> products = productRepository.findByCategory(category);
        return products.stream().map(product -> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDto update(String id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GlobalException("product not found with given id!!", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductWithCategory(String productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException("product not found with given id!!", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GlobalException("category not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public void delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GlobalException("product not found with given id!!", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED));
        if (Objects.nonNull(product.getProductImagePath())) {
            try {
                Path path = Paths.get(product.getProductImagePath());
                Files.delete(path);
            } catch (Exception e) {
                log.error("error while deleting product image {}", e.getMessage());
                throw new GlobalException("failed to delete product ", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED);
            }
        }
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> search(String keyword) {
        return productRepository.findByNameContaining(keyword)
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FileResponse uploadImage(String id, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GlobalException("product not found with given id!!", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED));
        String extension = CommonUtil.getExtensionFromFile(file);
        if (!CommonUtil.imageFileExtensions.contains(extension)) {
            throw new GlobalException("file extension " + extension + " not allowed", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED);
        }
        String fileName = CommonUtil.getFileNameWithTimestamp(file) + extension;
        Path path = Paths.get(basePath).resolve(fileName);
        product.setProductImagePath(String.valueOf(path));
        productRepository.save(product);
        return fileService.uploadFile(file, path);
    }

    @Override
    public byte[] getProductResources(String id) {
        byte[] bytes = null;
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("product not found with given id!!", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED));
            Resource fileResource = fileService.getFileResource(Paths.get(product.getProductImagePath()));
            bytes = FileCopyUtils.copyToByteArray(fileResource.getInputStream());
        } catch (Exception e) {
            log.error("error while reading product image resources");
        }
        return bytes;
    }
}
