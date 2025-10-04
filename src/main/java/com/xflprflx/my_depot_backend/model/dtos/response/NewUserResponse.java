package com.xflprflx.my_depot_backend.model.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xflprflx.my_depot_backend.model.User;

import java.io.Serial;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewUserResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String email;

    public NewUserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
