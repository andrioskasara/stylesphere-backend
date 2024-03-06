package com.stylesphere.service;

import com.stylesphere.model.CartItem;
import com.stylesphere.model.Order;
import com.stylesphere.model.dto.AddProductInCartDto;
import com.stylesphere.model.dto.OrderDto;
import com.stylesphere.model.dto.PlaceOrderDto;

import java.util.List;
import java.util.UUID;

public interface CartService {
    //ResponseEntity<?> addProductToCart(AddProductInCartDto productInCartDto)
    CartItem addProductToCart(AddProductInCartDto productInCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto applyCoupon(Long userId, String code);
    OrderDto increaseProductQuantity(AddProductInCartDto productInCartDto);
    OrderDto decreaseProductQuantity(AddProductInCartDto productInCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto> getUsersPlacedOrders(Long userId);
    OrderDto searchOrderByTrackingId(UUID trackingId);
}
