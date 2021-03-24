package com.api.common;

import org.springframework.security.core.GrantedAuthority;

public enum LoginRole implements GrantedAuthority {

    ADMIN("ADMIN", 0),
    USER("USER", 1),
    STAFF("STAFF", 2);

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
