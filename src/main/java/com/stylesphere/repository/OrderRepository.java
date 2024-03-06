package com.stylesphere.repository;

import com.stylesphere.model.Order;
import com.stylesphere.model.enumerations.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatuses);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatuses);

    Optional<Order> findByTrackingId(UUID trackingId);
    List<Order> findByDateBetweenAndOrderStatus(Date startOfMonth, Date endOfMonth, OrderStatus status);
    Long countByOrderStatus(OrderStatus status);
}
