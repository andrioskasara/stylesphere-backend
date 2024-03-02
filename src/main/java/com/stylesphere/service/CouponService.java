package com.stylesphere.service;

import com.stylesphere.model.Coupon;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getAllCoupons();
}
