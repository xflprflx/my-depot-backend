package com.xflprflx.my_depot_backend.configs.security.customgrant;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserAuthorities {

    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private Long tenantId;

    public CustomUserAuthorities(String username, Collection<? extends GrantedAuthority> authorities, Long tenantId) {
        this.username = username;
        this.authorities = authorities;
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getTenantId() {
        return tenantId;
    }
}
