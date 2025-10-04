package com.xflprflx.my_depot_backend.services;

import com.xflprflx.my_depot_backend.model.User;
import com.xflprflx.my_depot_backend.model.dtos.request.NewUserRequest;
import com.xflprflx.my_depot_backend.model.dtos.response.NewUserResponse;
import com.xflprflx.my_depot_backend.model.factory.UserFactory;
import com.xflprflx.my_depot_backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public NewUserResponse createNewUser(NewUserRequest newUserRequest) {
        User user = userRepository.save(UserFactory.newUserFrom(newUserRequest, passwordEncoder));
        return user.toNewUserResponse();
    }
}
