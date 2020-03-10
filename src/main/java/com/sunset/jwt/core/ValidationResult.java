package com.sunset.jwt.core;

import com.sunset.jwt.configuration.JWTProperties;
import lombok.Getter;

/**
 * 验证结果
 *
 * @author jik.shu
 * @since 2012-01-06
 */
@Getter
public class ValidationResult {
    private boolean doChain;
    private JWTProperties.ValidationState validationState;

    public ValidationResult(boolean doChain, JWTProperties.ValidationState validationState) {
        this.doChain = doChain;
        this.validationState = validationState;
    }

    public static ValidationResult success() {
        return new ValidationResult(true, JWTProperties.ValidationState.SUCCESS);
    }

    public static ValidationResult error() {
        return new ValidationResult(true, JWTProperties.ValidationState.ERROR);
    }

    public static ValidationResult expired() {
        return new ValidationResult(true, JWTProperties.ValidationState.EXPIRED);
    }
}