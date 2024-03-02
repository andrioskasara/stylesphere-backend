package com.stylesphere.service.impl;

import com.stylesphere.model.*;
import com.stylesphere.model.dto.AddProductInCartDto;
import com.stylesphere.model.dto.CartItemDto;
import com.stylesphere.model.dto.OrderDto;
import com.stylesphere.model.dto.PlaceOrderDto;
import com.stylesphere.model.enumerations.OrderStatus;
import com.stylesphere.model.exceptions.CouponHasExpiredException;
import com.stylesphere.model.exceptions.CouponNotFoundException;
import com.stylesphere.model.exceptions.ProductAlreadyInCartException;
import com.stylesphere.model.exceptions.ProductNotFoundException;
import com.stylesphere.repository.*;
import com.stylesphere.service.CartService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    public CartServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.couponRepository = couponRepository;
    }

    //public ResponseEntity<?> addProductToCart(AddProductInCartDto productInCartDto)
    public OrderDto addProductToCart(AddProductInCartDto productInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(productInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItem> cartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(productInCartDto.getProductId(), activeOrder.getId(), productInCartDto.getUserId());
        if (cartItem.isPresent()) {
            //return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
            throw new ProductAlreadyInCartException();
        }
        Optional<Product> product = productRepository.findById(productInCartDto.getProductId());
        Optional<User> user = userRepository.findById(productInCartDto.getUserId());

        if (product.isPresent() && user.isPresent()) {
            CartItem item = new CartItem();
            item.setProduct(product.get());
            item.setPrice(product.get().getPrice());
            item.setQuantity(1L);
            item.setUser(user.get());
            item.setOrder(activeOrder);
            cartItemRepository.save(item);

            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + item.getPrice());
            activeOrder.setAmount(activeOrder.getAmount() + item.getPrice());
            activeOrder.getCartItems().add(item);
            orderRepository.save(activeOrder);
            //return ResponseEntity.status(HttpStatus.CREATED).body(item);

            OrderDto orderDto = new OrderDto();
            orderDto.setId(activeOrder.getId());
            orderDto.setOrderDescription(activeOrder.getOrderDescription());
            orderDto.setDate(activeOrder.getDate());
            orderDto.setAmount(activeOrder.getAmount());
            orderDto.setAddress(activeOrder.getAddress());
            orderDto.setPayment(activeOrder.getPayment());
            orderDto.setOrderStatus(activeOrder.getOrderStatus());
            orderDto.setTotalAmount(activeOrder.getTotalAmount());
            orderDto.setDiscount(activeOrder.getDiscount());
            orderDto.setTrackingId(activeOrder.getTrackingId());
            orderDto.setUsername(user.get().getName());
            List<CartItemDto> cartItemDtos = activeOrder.getCartItems().stream().map(CartItem::getCartDto).collect(Collectors.toList());
            orderDto.setCartItems(cartItemDtos);
            return orderDto;
        } else {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            throw new ProductNotFoundException();
        }
    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemDto> cartItemDtos = activeOrder.getCartItems().stream().map(CartItem::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemDtos);
        if (activeOrder.getCoupon() != null) {
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }
        return orderDto;
    }

    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new CouponNotFoundException(code));

        if (couponIsExpired(coupon)) {
            throw new CouponHasExpiredException(coupon.getCode());
        }
        double discount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double net = activeOrder.getTotalAmount() - discount;

        activeOrder.setAmount(net);
        activeOrder.setDiscount(discount);
        activeOrder.setCoupon(coupon);
        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && currentDate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddProductInCartDto productInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(productInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> p = productRepository.findById(productInCartDto.getProductId());
        Optional<CartItem> cartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                productInCartDto.getProductId(), activeOrder.getId(), productInCartDto.getUserId());

        if (p.isPresent() && cartItem.isPresent()) {
            CartItem item = cartItem.get();
            Product product = p.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            item.setQuantity(item.getQuantity() + 1);
            if (activeOrder.getCoupon() != null) {
                double discount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double net = activeOrder.getTotalAmount() - discount;

                activeOrder.setAmount(net);
                activeOrder.setDiscount(discount);
            }
            cartItemRepository.save(item);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto decreaseProductQuantity(AddProductInCartDto productInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(productInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> p = productRepository.findById(productInCartDto.getProductId());
        Optional<CartItem> cartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                productInCartDto.getProductId(), activeOrder.getId(), productInCartDto.getUserId());

        if (p.isPresent() && cartItem.isPresent()) {
            CartItem item = cartItem.get();
            Product product = p.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            item.setQuantity(item.getQuantity() - 1);
            if (activeOrder.getCoupon() != null) {
                double discount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double net = activeOrder.getTotalAmount() - discount;

                activeOrder.setAmount(net);
                activeOrder.setDiscount(discount);
            }
            cartItemRepository.save(item);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> user = userRepository.findById(placeOrderDto.getUserId());
        if (user.isPresent()) {
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            Order order = new Order();
            order.setAmount(0.0);
            order.setTotalAmount(0.0);
            order.setDiscount(0.0);
            order.setUser(user.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getUsersPlacedOrders(Long userId) {
        return orderRepository.findByUserIdAndOrderStatusIn(userId,
                        List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered))
                .stream()
                .map(Order::getOrderDto)
                .collect(Collectors.toList());
    }
}
