package com.stylesphere.service.impl;

import com.stylesphere.model.Coupon;
import com.stylesphere.model.exceptions.CouponAlreadyExists;
import com.stylesphere.repository.CouponRepository;
import com.stylesphere.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon createCoupon(Coupon coupon) {
        if(couponRepository.existsByCode(coupon.getCode())) {
            throw  new CouponAlreadyExists(coupon.getCode());
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}
