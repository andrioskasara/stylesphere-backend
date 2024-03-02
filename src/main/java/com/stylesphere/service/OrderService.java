package com.stylesphere.service;

import com.stylesphere.model.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllPlacedOrders();
    OrderDto changeOrderStatus(Long orderId, String status);
}
