package com.xflprflx.my_depot_backend.model.factory;

import com.xflprflx.my_depot_backend.model.Branch;
import com.xflprflx.my_depot_backend.model.User;
import com.xflprflx.my_depot_backend.model.access_control.AccessProfile;
import com.xflprflx.my_depot_backend.model.dtos.request.NewUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFactory {

    public static User newUserFrom(NewUserRequest newUserRequest, PasswordEncoder passwordEncoder) {
        AccessProfile accessProfile = new AccessProfile();
        accessProfile.setId(newUserRequest.accessProfileId());

        Branch branch = new Branch();
        branch.setId(newUserRequest.branchId());

        return new User(
                newUserRequest.name(),
                newUserRequest.email(),
                passwordEncoder.encode(newUserRequest.password()),
                accessProfile,
                branch
        );
    }
}
