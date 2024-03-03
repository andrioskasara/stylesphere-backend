package com.stylesphere.model;

import com.stylesphere.model.dto.OrderDto;
import com.stylesphere.model.enumerations.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItem> cartItems;

    public Order() {
    }

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setAddress(address);
        orderDto.setTrackingId(trackingId);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUsername(user.getName());
        if (coupon != null)
            orderDto.setCouponName(coupon.getName());
        return orderDto;
    }
}
