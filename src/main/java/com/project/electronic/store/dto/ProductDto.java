package com.project.electronic.store.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private String id;

    private String name;

    private int price;

    private int quantity;

    private String description;

    private String productImagePath;

    private LocalDateTime createdDate;

    private boolean live;

    private boolean stock;

    private CategoryDto category;
}
