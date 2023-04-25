package com.epam.jwd.dao.model.account;

import java.util.Arrays;
import java.util.List;

public enum Role {
    UNKNOWN(0),
    USER(1),
    ADMIN(2);

    private final Integer id;
    public static final List<Role> ALL_AVAILABLE_ROLES = Arrays.asList(values());

    Role(Integer id) { this.id = id; }

    public Integer getRoleId() {
        return id;
    }

    public static Role getById(Integer id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getRoleId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Role> valuesAsList(){
        return ALL_AVAILABLE_ROLES;
    }
}
