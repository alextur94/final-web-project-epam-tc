package com.epam.jwd.rental_cars.dao.model.order;
import java.util.Arrays;

public enum Status {
    NEW((byte) 1),
    ACTIVE((byte) 2),
    CLOSE((byte) 3);

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
