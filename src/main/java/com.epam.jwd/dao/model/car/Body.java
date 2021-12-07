package com.epam.jwd.dao.model.car;

import java.util.Arrays;

public enum Body {
    SEDAN((byte) 1),  //СЕДАН
    HATCHBACK((byte) 2),  //ХЭТЧБЕК
    SVU((byte) 3),    //ВНЕДОРОЖНИК
    STATION_WAGON((byte) 4),  //УНИВЕРСАЛ
    COMPARTMENT((byte) 5), //КУПЕ
    MINIVAN((byte) 6),    //МИНИВЭН
    PICKUP((byte) 7), //ПИКАП
    LIMOUSINE((byte) 8), //ЛИМУЗИН
    VAN((byte) 9), //ФУРГОН
    CABRIOLET((byte) 10); //КАБРИОЛЕТ

    private final Byte id;

    Body(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public static Body getById(Byte id) {
        return Arrays.stream(Body.values())
                .filter(body -> body.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
