package com.project.electronic.store.service;

import com.project.electronic.store.dto.CartDto;
import com.project.electronic.store.dto.CartItemRequest;

public interface CartService {
    CartDto addItemsToCart(String userId, CartItemRequest cartItemRequest);

    CartDto removeItemFromCart(String userId, String cartItemId);

    void clearCart(String userId);

    CartDto getCartByUser(String userId);


}
