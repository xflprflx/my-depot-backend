package com.xflprflx.my_depot_backend.model.dtos.response;

import java.util.List;

public record LoggedInUser(String email, List<String> authorities) {
}
