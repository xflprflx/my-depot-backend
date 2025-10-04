package com.xflprflx.my_depot_backend.model.dtos.response;

import com.xflprflx.my_depot_backend.model.User;

public record NewUserResponse(Long id, String name, String email) {

    public NewUserResponse(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }

}
