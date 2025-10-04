package com.xflprflx.my_depot_backend.model.dtos.request;

import com.xflprflx.my_depot_backend.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewUserRequest(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @ValidPassword
        String password,

        @NotNull
        Long accessProfileId,
        @NotNull
        Long branchId

) {

}