package com.traveler.api.infra.security;

import org.springframework.security.core.userdetails.UserDetails;

abstract public class CustomUserDetails implements UserDetails {

    private Long id;

    public CustomUserDetails() {
    }

    public CustomUserDetails(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
