package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.insurance.Insurance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class InsuranceDaoImpl implements Dao<Insurance, Integer> {
    private static final Logger logger = LogManager.getLogger(InsuranceDaoImpl.class);

    private static final String SQL_SAVE_INSURANCE = "INSERT INTO insurance (type, number, company, amount) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE_INSURANCE_BY_ID = "UPDATE insurance SET type = ?, number = ?, company = ?, amount = ? WHERE id = ?";
    private static final String SQL_DELETE_INSURANCE_BY_ID = "DELETE FROM insurance WHERE id = ?";
    private static final String SQL_FIND_INSURANCE_BY_ID = "SELECT id, type, number, company, amount FROM insurance WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Insurance save(Insurance insurance) {
        logger.info("save method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveInsurance(insurance, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Insurance insurance) {
        logger.info("update method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateInsuranceById(insurance, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Insurance insurance) {
        logger.info("delete method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteInsurancetById(insurance.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Insurance> findAll() {
        return null;
    }

    @Override
    public Insurance findById(Integer id) {
        logger.info("find by id method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Insurance insurance = null;
        try {
            insurance = findInsuranceById(id, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return insurance;
    }

    private Insurance saveInsurance(Insurance insurance, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE_INSURANCE, new String[] {"id"});
        statement.setByte(1, insurance.getType());
        statement.setString(2, insurance.getNumber());
        statement.setString(3, insurance.getCompany());
        statement.setDouble(4, insurance.getAmount());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        insurance.setId(id);
        statement.close();
        resultSet.close();
        return insurance;
    }

    private Boolean updateInsuranceById(Insurance insurance, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INSURANCE_BY_ID);
        statement.setByte(1, insurance.getType());
        statement.setString(2, insurance.getNumber());
        statement.setString(3, insurance.getCompany());
        statement.setDouble(4, insurance.getAmount());
        statement.setInt(5, insurance.getId());
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Boolean deleteInsurancetById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_INSURANCE_BY_ID);
        statement.setInt(1, id);
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Insurance findInsuranceById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_INSURANCE_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Insurance insurance;
        if (resultSet.next()) {
            insurance = new Insurance(
                    resultSet.getInt(1),
                    resultSet.getByte(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5));
        } else {
            insurance = null;
        }
        statement.close();
        resultSet.close();
        return insurance;
    }
}
