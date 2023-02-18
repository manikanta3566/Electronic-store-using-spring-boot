package com.project.electronic.store.controller;

import com.project.electronic.store.dto.GenericResponse;
import com.project.electronic.store.dto.ProductDto;
import com.project.electronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("v0/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponse> create(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(new GenericResponse<>(productService.create(productDto), HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<GenericResponse> createProductWithCategory(@RequestBody ProductDto productDto,@PathVariable String categoryId){
        return new ResponseEntity<>(new GenericResponse<>(productService.createProductWithCategory(categoryId,productDto),HttpStatus.CREATED.value()),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAll() {
        return new ResponseEntity<>(new GenericResponse<>(productService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<GenericResponse> getAllProductsByCategory(@PathVariable String categoryId) {
        return new ResponseEntity<>(new GenericResponse<>(productService.getAllProductsByCategory(categoryId)), HttpStatus.OK);
    }
    @GetMapping("{productId}")
    public ResponseEntity<GenericResponse> getProductDetailsById(@PathVariable String productId) {
        return new ResponseEntity<>(new GenericResponse<>(productService.getProductById(productId)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("{productId}")
    public ResponseEntity<GenericResponse> update(@PathVariable String productId, @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(new GenericResponse<>(productService.update(productId, productDto)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("{productId}/categories/{categoryId}")
    public ResponseEntity<GenericResponse> updateProductWithCategory(@PathVariable String productId,@PathVariable String categoryId){
        return new ResponseEntity<>(new GenericResponse<>(productService.updateProductWithCategory(productId,categoryId)),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("{productId}")
    public ResponseEntity<GenericResponse> delete(@PathVariable String productId) {
        productService.delete(productId);
        return new ResponseEntity<>(new GenericResponse<>("product deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<GenericResponse> searchProducts(@PathVariable String keyword) {
        return new ResponseEntity<>(new GenericResponse<>(productService.search(keyword)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("{productId}/images")
    public ResponseEntity<GenericResponse> uploadProductImage(@PathVariable String productId, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(new GenericResponse(productService.uploadImage(productId, file), HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping(value = "{productId}/images", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> serveUserImage(@PathVariable String productId) {
        return ResponseEntity
                .ok(productService.getProductResources(productId));
    }

}
