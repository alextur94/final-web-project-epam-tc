package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.InsuranceDao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.insurance.Insurance;
import com.epam.jwd.dao.sql.SqlQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class InsuranceDaoImpl implements InsuranceDao {
    private static final Logger logger = LogManager.getLogger(InsuranceDaoImpl.class);
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Insurance save(Insurance insurance) throws DaoException {
        logger.info("save method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveInsurance(insurance, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.SAVE_INSURANCE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Insurance insurance) throws DaoException {
        logger.info("update method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateInsuranceById(insurance, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.UPDATE_INSURANCE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Insurance insurance) throws DaoException {
        logger.info("delete method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteInsuranceById(insurance.getId(), connection);
        } catch (SQLException e) {
            throw new DaoException(Message.DELETE_INSURANCE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Insurance> findAll() {
        return null;
    }

    @Override
    public Insurance findById(Integer id) throws DaoException {
        logger.info("find by id method " + InsuranceDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Insurance insurance;
        try {
            insurance = findInsuranceById(id, connection);
            if (insurance != null) {
                return insurance;
            }
            throw new DaoException(Message.FIND_BY_ID_INSURANCE_ERROR);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_BY_ID_INSURANCE_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Insurance saveInsurance(Insurance insurance, Connection connection) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_SAVE_INSURANCE, new String[]{"id"})) {
            statement.setByte(1, insurance.getType());
            statement.setString(2, insurance.getNumber());
            statement.setString(3, insurance.getCompany());
            statement.setDouble(4, insurance.getAmount());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Integer id = resultSet.getInt(1);
            insurance.setId(id);
        }
        resultSet.close();
        return insurance;
    }

    private Boolean updateInsuranceById(Insurance insurance, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_INSURANCE_BY_ID)) {
            statement.setByte(1, insurance.getType());
            statement.setString(2, insurance.getNumber());
            statement.setString(3, insurance.getCompany());
            statement.setDouble(4, insurance.getAmount());
            statement.setInt(5, insurance.getId());
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private Boolean deleteInsuranceById(Integer id, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_DELETE_INSURANCE_BY_ID)) {
            statement.setInt(1, id);
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private Insurance findInsuranceById(Integer id, Connection connection) throws SQLException {
        ResultSet resultSet;
        Insurance insurance;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_INSURANCE_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
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
        }
        resultSet.close();
        return insurance;
    }
}
