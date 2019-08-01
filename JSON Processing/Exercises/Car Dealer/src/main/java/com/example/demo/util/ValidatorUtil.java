package com.example.demo.util;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatorUtil {
    <E> boolean isValid(E entity);

    <E> void printErrors(E entity);
}
