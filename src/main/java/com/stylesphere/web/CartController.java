package com.stylesphere.web;

import com.stylesphere.model.dto.AddProductInCartDto;
import com.stylesphere.model.dto.OrderDto;
import com.stylesphere.model.dto.PlaceOrderDto;
import com.stylesphere.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CartController {
    public final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<OrderDto> addProductToCart(@RequestBody AddProductInCartDto productInCartDto) {
        OrderDto orderDto = cartService.addProductToCart(productInCartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @PostMapping("/cart/apply-coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        OrderDto orderDto = cartService.applyCoupon(userId, code);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/cart/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductInCartDto productInCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(productInCartDto));
    }

    @PostMapping("/cart/deduction")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddProductInCartDto productInCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(productInCartDto));
    }

    @PostMapping("/cart/place-order")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }

    @GetMapping("cart/{userId}/placed-orders")
    public ResponseEntity<List<OrderDto>> getUsersPlacedOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getUsersPlacedOrders(userId));
    }

}
