package com.epam.jwd.dao.api;

import com.epam.jwd.dao.model.insurance.Insurance;

import java.sql.Connection;
import java.sql.SQLException;

public interface InsuranceDao extends Dao<Insurance, Integer>{
    Insurance saveInsurance(Insurance insurance, Connection connection) throws SQLException;
}
