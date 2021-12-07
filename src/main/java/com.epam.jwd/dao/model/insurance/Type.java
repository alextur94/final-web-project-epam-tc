package com.epam.jwd.dao.model.insurance;

import java.util.Arrays;

public enum Type {
    STANDART((byte) 1),
    PLUS((byte) 2),
    FULL((byte) 3);

    private final Byte id;

    Type(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public static Type getById(Byte id) {
        return Arrays.stream(Type.values())
                .filter(type -> type.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
