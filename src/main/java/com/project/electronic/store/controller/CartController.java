package com.project.electronic.store.controller;

import com.project.electronic.store.dto.CartItemRequest;
import com.project.electronic.store.dto.GenericResponse;
import com.project.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v0/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("{userId}")
    public ResponseEntity<GenericResponse> addItemsToCart(@PathVariable String userId,
                                                          @RequestBody CartItemRequest cartItemRequest) {
        return new ResponseEntity<>(new GenericResponse<>(cartService.addItemsToCart(userId, cartItemRequest), HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("{userId}/cartItems/{cartItemId}")
    public ResponseEntity<GenericResponse> removeItem(@PathVariable String userId,
                                                      @PathVariable String cartItemId) {
        return new ResponseEntity<>(new GenericResponse<>(cartService.removeItemFromCart(userId, cartItemId)), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<GenericResponse> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return new ResponseEntity<>(new GenericResponse<>("cart cleared successfully"), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<GenericResponse> getCartByUser(@PathVariable String userId) {
        return new ResponseEntity<>(new GenericResponse<>(cartService.getCartByUser(userId)), HttpStatus.OK);
    }
}
