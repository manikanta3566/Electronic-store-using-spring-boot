package com.project.electronic.store.controller;


import com.project.electronic.store.dto.GenericResponse;
import com.project.electronic.store.dto.OrderRequestDto;
import com.project.electronic.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v0/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<GenericResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(new GenericResponse<>(orderService.createOrder(orderRequestDto), HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GenericResponse> getAllUserOrders(@PathVariable String userId) {
        return new ResponseEntity<>(new GenericResponse<>(orderService.getAllOrderByUser(userId)), HttpStatus.OK);
    }

    @PutMapping("{orderId}")
    public ResponseEntity<GenericResponse> updateOrder(@RequestBody OrderRequestDto orderRequestDto, @PathVariable String orderId) {
        return new ResponseEntity<>(new GenericResponse<>(orderService.updateOrder(orderRequestDto, orderId)), HttpStatus.OK);
    }

    @DeleteMapping("{orderId}/user/{userId}")
    public ResponseEntity<GenericResponse> deleteOrder(@PathVariable String orderId, @PathVariable String userId) {
        orderService.removeOrder(orderId, userId);
        return new ResponseEntity<>(new GenericResponse<>("order deleted successfully"), HttpStatus.OK);
    }
}
