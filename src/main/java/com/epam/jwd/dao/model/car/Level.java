package com.epam.jwd.dao.model.car;

import java.util.Arrays;

public enum Level {
    ECONOMY((byte) 1),
    AVERAGE((byte) 2),
    BUSINESS((byte) 3),
    SPORT((byte) 4);

    private final Byte id;

    Level(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public static Level getById(Byte id) {
        return Arrays.stream(Level.values())
                .filter(level -> level.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
