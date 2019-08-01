package com.example.demo.util.impl;

import com.example.demo.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Validator;

public class ValidatorUtilImpl implements ValidatorUtil {

    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).size() == 0;
    }

    @Override
    public <E> void printErrors(E entity) {
        this.validator.validate(entity).forEach(e -> System.out.println(e.getMessage()));
    }
}
