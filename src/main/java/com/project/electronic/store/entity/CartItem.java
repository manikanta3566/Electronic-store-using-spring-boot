package com.project.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private int totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    public CartItem() {
        this.id = UUID.randomUUID().toString();
    }

}
