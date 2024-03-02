package com.stylesphere.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CouponAlreadyExists extends RuntimeException {

    public CouponAlreadyExists(String code) {
        super(String.format("Coupon %s already exists", code));
    }
}