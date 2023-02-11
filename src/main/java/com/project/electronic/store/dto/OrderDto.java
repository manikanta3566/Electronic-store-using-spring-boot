package com.project.electronic.store.dto;

import com.project.electronic.store.enums.OrderStatus;
import com.project.electronic.store.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {

    private String id;

    private String orderStatus= OrderStatus.PENDING.toString();

    private String paymentStatus= PaymentStatus.NOT_PAID.toString();

    private String billingAddress;

    private String billingPhoneNumber;

    private String billingName;

    private int orderAmount;

    private LocalDateTime orderedDate;

    private LocalDateTime deliveredDate;

    private List<CartItemDto> cartItems;

}
