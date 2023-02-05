package com.project.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@Data
public class Product {

    @Id
    private String id;

    private String name;

    private int price;

    private int quantity;

    private String description;

    private String productImagePath;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private boolean live;

    private boolean stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
        this.id = UUID.randomUUID().toString();
    }
}
