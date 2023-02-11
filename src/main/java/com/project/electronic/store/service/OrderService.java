package com.project.electronic.store.service;

import com.project.electronic.store.dto.OrderDto;
import com.project.electronic.store.dto.OrderRequestDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderDto> getAllOrderByUser(String userId);

    void removeOrder(String orderId,String userId);

    OrderDto updateOrder(OrderRequestDto orderRequestDto,String orderId);

}
