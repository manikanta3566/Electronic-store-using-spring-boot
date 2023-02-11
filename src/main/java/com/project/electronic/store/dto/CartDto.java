package com.project.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {

    private String id;

    private LocalDateTime createdDate;

    private List<CartItemDto> items;

}
