package com.example.demo.utils;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Required {
    String[] allowedValues() default "";
    boolean allowedValuesCaseSensitive() default true;
    String[] requiredKeys() default "";
    int maxSize() default 0;
    int minSize() default 0;
    String[] allowedFileNameExtensions() default "";
    boolean showAllowedFileNameExtensions() default false;
    boolean validateEmail() default false;
    boolean mandatory() default true;
    boolean validateBase64Encoding() default false;
    String regexPatternString() default "";
}