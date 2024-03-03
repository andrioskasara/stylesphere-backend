package com.stylesphere.repository;

import com.stylesphere.model.Order;
import com.stylesphere.model.enumerations.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatuses);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatuses);
}
