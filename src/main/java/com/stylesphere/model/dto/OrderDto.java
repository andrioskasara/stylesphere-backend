package com.stylesphere.model.dto;

import com.stylesphere.model.CartItem;
import com.stylesphere.model.User;
import com.stylesphere.model.enumerations.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id;
    private String orderDescription;
    private Date date;
    private Double amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Double totalAmount;
    private Double discount;
    private UUID trackingId;
    private String username;
    private String couponName;
    private List<CartItemDto> cartItems;
}
