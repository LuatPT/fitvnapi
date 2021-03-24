package com.api.common;

import org.springframework.security.core.GrantedAuthority;

public enum LoginRole implements GrantedAuthority {

    ADMIN("ROLE_ADMIN", 0),
    USER("ROLE_USER", 1),
    STAFF("ROLE_STAFF", 2);

    private final String name;
    private final int key;

    private LoginRole(String name, int key) {
		this.name = name;
		this.key = key;
	}

	@Override
    public String getAuthority() {
        return name;
    }
}
