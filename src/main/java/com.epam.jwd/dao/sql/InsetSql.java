package com.epam.jwd.dao.sql;

public class InsetSql {
    String a = "(brand, model, year, level, body, engine_volume, transmission, doors, color, available, price_id)";
    public final static String INSERT_CARS = "INSERT INTO car VALUES " +
            "(1, 'Volkswagen', 'Polo', 2021, 1, 4, 36, 1, 5, 'white', 1, 1)," +
            "(2, 'Audi', 'A7', 2013, 3, 2, 38, 1, 5, 'gray', 1, 2)," +
            "(3, 'BMW', 'X6', 2013, 3, 2, 38, 1, 5, 'gray', 1, 3)," +
            "(4, 'Geely', 'Emgrand 7', 2016, 2, 4, 39, 1, 5, 'black', 1, 4)," +
            "(5, 'Hyundai', 'i40', 2017, 2, 3, 25, 1, 5, 'brow', 0, 5)," +
            "(6, 'Infiniti', 'FX FX35', 2007, 3, 3, 45, 1, 5, 'black', 1, 6)," +
            "(7, 'Chevrolet', 'Corvette Z06 C7', 2014, 4, 5, 53, 0, 3, 'gray', 1, 7)," +
            "(8, 'Mercedes-Benz', 'CL-Класс AMG 63', 2007, 3, 5, 23, 1, 3, 'black', 1, 8)," +
            "(9, 'Audi', 'R8', 2011, 4, 5, 58, 1, 3, 'white', 0, 9)," +
            "(10, 'Ford', 'Focus', 2016, 1, 4, 16, 0, 5, 'gray', 1, 10)," +
            "(11, 'Opel', 'Astra', 2012, 1, 4, 38, 0, 5, 'gray', 1, 11)," +
            "(12, 'Ford', 'Mondeo', 2007, 1, 1, 15, 0, 5, 'gray', 1, 12)," +
            "(13, 'Renault', 'Logan', 2010, 1, 1, 26, 0, 5, 'gray', 1, 13)," +
            "(14, 'Skoda', 'Octavia', 2012, 1, 1, 28, 0, 5, 'white', 1, 14)," +
            "(15, 'Skoda', 'Superb', 2010, 2, 1, 49, 1, 5, 'white', 0, 15)," +
            "(16, 'Volkswagen', 'Passat B6', 2008, 2, 1, 51, 1, 5, 'black', 1, 16)";
    public final static String INSERT_PRICE = "INSERT INTO price VALUES " +
            "(1, 80, 15)," +
            "(2, 150, 27)," +
            "(3, 145, 24)," +
            "(4, 110, 16)," +
            "(5, 110, 16)," +
            "(6, 120, 18)," +
            "(7, 200, 50)," +
            "(8, 180, 45)," +
            "(9, 245, 70)," +
            "(10, 60, 14)," +
            "(11, 55, 13)," +
            "(12, 70, 16)," +
            "(13, 65, 15)," +
            "(14, 90, 18)," +
            "(15, 95, 19)," +
            "(16, 85, 16);";

}
