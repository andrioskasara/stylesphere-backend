package com.stylesphere.web;

import com.stylesphere.model.dto.OrderDto;
import com.stylesphere.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TrackingController {

    private final CartService cartService;

    public TrackingController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/order/{trackingId}")
    public ResponseEntity<OrderDto> searchOrderByTrackingId(@PathVariable UUID trackingId){
        OrderDto orderDto = cartService.searchOrderByTrackingId(trackingId);
        if (orderDto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderDto);
    }
}
