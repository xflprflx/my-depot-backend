package com.xflprflx.my_depot_backend.exceptions;

public class TenantMismatchException extends RuntimeException {
    public TenantMismatchException(String message) {
        super(message);
    }
}
