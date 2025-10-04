package com.xflprflx.my_depot_backend.controllers;

import com.xflprflx.my_depot_backend.controllers.util.LocationHelper;
import com.xflprflx.my_depot_backend.model.dtos.request.NewUserRequest;
import com.xflprflx.my_depot_backend.model.dtos.response.NewUserResponse;
import com.xflprflx.my_depot_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<NewUserResponse> createNewUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        NewUserResponse newUserResponse = userService.createNewUser(newUserRequest);
        URI location = LocationHelper.buildLocation(newUserResponse);
        return ResponseEntity.created(location).body(newUserResponse);
    }
}
