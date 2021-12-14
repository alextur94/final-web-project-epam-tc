package com.epam.jwd.dao.model.order;
import java.util.Arrays;

public enum Status {
    WAITING_PAYMENT((byte)1),
    NEW((byte) 2),
    ACTIVE((byte) 3),
    BLOCK((byte) 4),
    CLOSE((byte) 5);

    private final Byte id;

    Status(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public static Status getById(Byte id) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
