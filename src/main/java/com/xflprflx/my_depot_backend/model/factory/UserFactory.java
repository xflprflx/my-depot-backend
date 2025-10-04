package com.xflprflx.my_depot_backend.model.factory;

import com.xflprflx.my_depot_backend.model.Branch;
import com.xflprflx.my_depot_backend.model.User;
import com.xflprflx.my_depot_backend.model.access_control.AccessProfile;
import com.xflprflx.my_depot_backend.model.dtos.request.NewUserRequest;

public class UserFactory {

    public static User from(NewUserRequest newUserRequest) {
        AccessProfile accessProfile = new AccessProfile();
        accessProfile.setId(newUserRequest.getAccessProfileId());

        Branch branch = new Branch();
        branch.setId(newUserRequest.getBranchId());

        return new User(
                newUserRequest.getName(),
                newUserRequest.getEmail(),
                newUserRequest.getPassword(),
                accessProfile,
                branch
        );
    }
}
