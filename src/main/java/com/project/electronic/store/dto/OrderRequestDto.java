package com.project.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDto {
    private String userId;

//    private String cartId;

    private String orderStatus;

    private String paymentStatus;

    private String billingAddress;

    private String billingPhoneNumber;

    private String billingName;
}
