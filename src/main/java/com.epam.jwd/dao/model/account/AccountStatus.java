package com.epam.jwd.dao.model.account;

import java.util.Arrays;

public enum AccountStatus {
    NOT_ACTIVE(0),
    ACTIVE(1);

    private final Integer id;

    AccountStatus(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static AccountStatus getById(Integer id) {
        return Arrays.stream(AccountStatus.values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
