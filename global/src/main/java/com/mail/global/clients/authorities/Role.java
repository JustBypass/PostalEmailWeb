package com.mail.global.clients.authorities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(new HashSet<>(Arrays.asList(Permission.USER, Permission.SEND))),
    MODERATOR(new HashSet<>(Arrays.asList(Permission.READ, Permission.READ_SECRET, Permission.SEND))),
    ADMIN(new HashSet<>(Arrays.asList(Permission.READ,Permission.SEND,Permission.READ_SECRET,Permission.WRITE_SECRET))),
    BLOCKED(new HashSet<>(Arrays.asList(Permission.READ)));

    final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions=permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return this.permissions.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toSet());
    }

}
