package com.xflprflx.my_depot_backend.repositories;

import com.xflprflx.my_depot_backend.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {
            "accessProfile",
            "accessProfile.permissions",
            "accessProfile.permissions.resource"
    })
    Optional<User> findByEmail(String email);
}
