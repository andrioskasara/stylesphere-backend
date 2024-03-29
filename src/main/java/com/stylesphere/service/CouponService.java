package com.stylesphere.service;

import com.stylesphere.model.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupons();

    Coupon createCoupon(Coupon coupon);
}
