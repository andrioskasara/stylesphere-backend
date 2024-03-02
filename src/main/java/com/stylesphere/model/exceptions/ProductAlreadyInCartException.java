package com.stylesphere.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ProductAlreadyInCartException extends RuntimeException {

    public ProductAlreadyInCartException() {
        super(String.format("Product already exists in shopping cart"));
    }
}
