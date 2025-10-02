package com.xflprflx.my_depot_backend.model.enums;

public enum Action {
    READ(1),
    WRITE(2);

    private final int cod;

    Action(int cod) {
        this.cod = cod;
    }
}
