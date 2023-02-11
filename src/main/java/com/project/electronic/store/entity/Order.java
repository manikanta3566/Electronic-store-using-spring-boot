package com.project.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;

    private String orderStatus;

    private String paymentStatus;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhoneNumber;

    private String billingName;

    private int orderAmount;

    @CreationTimestamp
    private LocalDateTime orderedDate;

    private LocalDateTime deliveredDate;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "order",cascade = CascadeType.REMOVE)
    private List<CartItem> cartItems;

    @ManyToOne
    @JoinTable(name="user_orders",
            joinColumns={
            @JoinColumn(name="order_id")
            },
            inverseJoinColumns={
            @JoinColumn(name="user_id")
    })
    private User user;

    public Order(){
        this.id= UUID.randomUUID().toString();
    }
}
