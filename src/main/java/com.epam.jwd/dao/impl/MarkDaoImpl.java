package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.api.MarkDao;
import com.epam.jwd.dao.api.Message;
import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.model.mark.Mark;
import com.epam.jwd.dao.sql.SqlQueries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MarkDaoImpl implements MarkDao {
    private static final Logger logger = LogManager.getLogger(InsuranceDaoImpl.class);
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    @Override
    public Mark save(Mark mark) throws DaoException {
        logger.info("save method " + MarkDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveMark(mark, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.SAVE_MARK_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Mark mark) throws DaoException {
        logger.info("update method " + MarkDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateMarkById(mark, connection);
        } catch (SQLException e) {
            throw new DaoException(Message.UPDATE_MARK_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Mark mark) throws DaoException {
        logger.info("delete method " + MarkDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteMarkById(mark.getId(), connection);
        } catch (SQLException e) {
            throw new DaoException(Message.DELETE_MARK_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Mark> findAll() {
        return null;
    }

    @Override
    public Mark findById(Integer id) throws DaoException {
        Connection connection = connectionPool.takeConnection();
        Mark mark;
        try {
            mark = findMarkById(id, connection);
            if (mark != null) {
                return mark;
            }
            throw new DaoException(Message.FIND_ALL_MARKS_ERROR);
        } catch (SQLException e) {
            throw new DaoException(Message.FIND_ALL_MARKS_ERROR);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Mark saveMark(Mark mark, Connection connection) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_SAVE_MARK, new String[]{"id"})) {
            statement.setString(1, mark.getDescription());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Integer id = resultSet.getInt(1);
            mark.setId(id);
        }
        resultSet.close();
        return mark;
    }

    @Override
    public Boolean updateMarkById(Mark mark, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_MARK_BY_ID)) {
            statement.setString(1, mark.getDescription());
            statement.setInt(2, mark.getId());
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private Boolean deleteMarkById(Integer id, Connection connection) throws SQLException {
        Boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_DELETE_MARK_BY_ID)) {
            statement.setInt(1, id);
            result = Objects.equals(statement.executeUpdate(), 1);
        }
        return result;
    }

    private Mark findMarkById(Integer id, Connection connection) throws SQLException {
        ResultSet resultSet;
        Mark mark;
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.SQL_FIND_MARK_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mark = new Mark(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
            } else {
                mark = null;
            }
        }
        resultSet.close();
        return mark;
    }
}
