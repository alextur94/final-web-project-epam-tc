package com.epam.jwd.dao.api;

import com.epam.jwd.dao.model.price.Price;

import java.sql.Connection;
import java.sql.SQLException;

public interface PriceDao extends Dao<Price, Integer> {
    Price savePrice(Price price, Connection connection) throws SQLException;
}
