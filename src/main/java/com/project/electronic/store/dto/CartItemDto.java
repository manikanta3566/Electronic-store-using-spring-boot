package com.project.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {

    private String id;

    private ProductDto product;

    private int quantity;

    private int totalAmount;
}
