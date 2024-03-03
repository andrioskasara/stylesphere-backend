package com.stylesphere.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(String code) {
        super(String.format("Coupon %s doesn't exist", code));
    }
}
