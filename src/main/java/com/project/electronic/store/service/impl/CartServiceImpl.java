package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.CartDto;
import com.project.electronic.store.dto.CartItemRequest;
import com.project.electronic.store.entity.Cart;
import com.project.electronic.store.entity.CartItem;
import com.project.electronic.store.entity.Product;
import com.project.electronic.store.entity.User;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.CartItemRepository;
import com.project.electronic.store.repository.CartRepository;
import com.project.electronic.store.repository.ProductRepository;
import com.project.electronic.store.repository.UserRepository;
import com.project.electronic.store.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemsToCart(String userId, CartItemRequest cartItemRequest) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new GlobalException("product not found with given id!!", HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED));
        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        Cart cart = null;
        if (cartOptional.isEmpty()) {
            cart = new Cart();
            cart.setUser(user);
        } else {
            cart = cartOptional.get();
        }

        AtomicBoolean isItemPresent = new AtomicBoolean(false);
        List<CartItem> cartItems = cart.getItems().stream().map(cartItem -> {
            if (cartItem.getProduct().getId().equals(cartItemRequest.getProductId())) {
                cartItem.setQuantity(cartItem.getQuantity());
                cartItem.setTotalAmount(cartItemRequest.getQuantity() * product.getPrice());
                isItemPresent.set(true);
            }
            return cartItem;
        }).collect(Collectors.toList());

        if (!isItemPresent.get()) {
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setProduct(product);
            cartItem.setTotalAmount(cartItemRequest.getQuantity() * product.getPrice());
            cartItem.setCart(cart);
            cartItems.add(cartItem);
        }
        cart.setItems(cartItems);
        //calculate total amount of cart
        int totalCartAmount = 0;
        for (CartItem cartItem : cartItems) {
            totalCartAmount += cartItem.getTotalAmount();
        }
        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartDto.class);
    }

    @Override
    public CartDto removeItemFromCart(String userId, String cartItemId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new GlobalException("cart not found for given user", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new GlobalException("cart item not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        List<CartItem> removedCartItems = cart.getItems().stream().filter(item -> !cartItemId.equals(item.getId())).collect(Collectors.toList());
        cart.setItems(removedCartItems);
        cartItemRepository.delete(cartItem);
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new GlobalException("cart not found for given user", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        List<String> items = cartItemRepository.findByCartId(cart.getId());
        cartItemRepository.deleteCartItems(items);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new GlobalException("cart not found for given user", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        return modelMapper.map(cart, CartDto.class);
    }
}
