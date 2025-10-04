package com.xflprflx.my_depot_backend.model.dtos.request;

import com.xflprflx.my_depot_backend.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serial;
import java.io.Serializable;

public class NewUserRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private Long accessProfileId;
    @NotNull
    private Long branchId;

    public NewUserRequest(String name, String email, String password, Long accessProfileId, Long branchId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accessProfileId = accessProfileId;
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getAccessProfileId() {
        return accessProfileId;
    }

    public Long getBranchId() {
        return branchId;
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accessProfileId=" + accessProfileId +
                ", branchId=" + branchId +
                '}';
    }

    public User toModel() {
        return new User(this);
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
