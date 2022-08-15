package ru.turaev.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.turaev.user.enums.Permission.*;

@Getter
@AllArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(USERS_WRITE, USERS_READ, USERS_UPDATE, USERS_DELETE));

    private Set<Permission> permissions;

    public Set<GrantedAuthority> getAuthorities() {
        return permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}