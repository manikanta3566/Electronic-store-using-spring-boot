package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.OrderDto;
import com.project.electronic.store.dto.OrderRequestDto;
import com.project.electronic.store.entity.Cart;
import com.project.electronic.store.entity.CartItem;
import com.project.electronic.store.entity.Order;
import com.project.electronic.store.entity.User;
import com.project.electronic.store.enums.OrderStatus;
import com.project.electronic.store.enums.PaymentStatus;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.CartItemRepository;
import com.project.electronic.store.repository.CartRepository;
import com.project.electronic.store.repository.OrderRepository;
import com.project.electronic.store.repository.UserRepository;
import com.project.electronic.store.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDto createOrder(OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId()).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new GlobalException("cart not found for given user", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        if (CollectionUtils.isEmpty(cart.getItems())) {
            throw new GlobalException("cart items are empty", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED);
        }
        List<CartItem> cartItems=cart.getItems().stream().filter(cartItem -> !cartItem.isDeleted()).collect(Collectors.toList());
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING.toString());
        order.setPaymentStatus(PaymentStatus.NOT_PAID.toString());
        order.setBillingAddress(orderRequestDto.getBillingAddress());
        order.setBillingPhoneNumber(orderRequestDto.getBillingPhoneNumber());
        order.setBillingName(orderRequestDto.getBillingName());
        order.setCartItems(cartItems);
        order.setOrderAmount(cartItems.stream().mapToInt(cartItem -> cartItem.getTotalAmount()).sum());
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);
        cartItems= cartItems.stream().map(cartItem -> {
            cartItem.setDeleted(true);
            cartItem.setOrder(savedOrder);
            return cartItem;
        }).collect(Collectors.toList());
        cartItemRepository.saveAll(cartItems);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrderByUser(String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        List<Order> userOrders = orderRepository.findByUser(user);
        return userOrders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void removeOrder(String orderId, String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new GlobalException("order not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        orderRepository.delete(order);
    }

    @Override
    public OrderDto updateOrder(OrderRequestDto orderRequestDto, String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new GlobalException("order not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        order.setOrderStatus(orderRequestDto.getOrderStatus());
        order.setPaymentStatus(orderRequestDto.getPaymentStatus());
        order.setBillingAddress(orderRequestDto.getBillingAddress());
        order.setBillingName(orderRequestDto.getBillingName());
        order.setBillingPhoneNumber(orderRequestDto.getBillingPhoneNumber());
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }
}
