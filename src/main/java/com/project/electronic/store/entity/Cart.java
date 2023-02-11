package com.project.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> items=new ArrayList<>();

    public Cart() {
        this.id = UUID.randomUUID().toString();
    }
}
