package com.stylesphere.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CouponHasExpiredException extends RuntimeException {
    public CouponHasExpiredException(String code) {
        super(String.format("Coupon %s has expired", code));
    }
}
