package com.bloggio.api.bloggio.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bloggio.api.bloggio.persistence.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private UUID UserId;

    private String UserEmail;

    private String UserNickname;

    @JsonIgnore
    private String UserPassword;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Users users) {
        List<GrantedAuthority> authorities = users.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                users.getUserId(),
                users.getUserEmail(),
                users.getUserNickname(),
                users.getUserPassword(),
                authorities);
    }

    public UUID getUserId() {
        return UserId;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getUserNickname() {
        return UserNickname;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return UserPassword;
    }

    @Override
    public String getUsername() {
        return UserNickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(UserId, user.UserId);
    }

}
