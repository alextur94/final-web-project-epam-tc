package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.mark.Mark;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MarkDaoImpl implements Dao<Mark, Integer> {
    private static final Logger logger = LogManager.getLogger(InsuranceDaoImpl.class);

    private static final String SQL_SAVE_MARK = "INSERT INTO mark (description) VALUES (?)";
    private static final String SQL_UPDATE_MARK_BY_ID = "UPDATE mark SET description = ? WHERE id = ?";
    private static final String SQL_DELETE_MARK_BY_ID = "DELETE FROM mark WHERE id = ?";
    private static final String SQL_FIND_MARK_BY_ID = "SELECT id, description  FROM mark WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Mark save(Mark mark) {
        logger.info("save method " + MarkDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveMark(mark, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Mark mark) {
        logger.info("update method " + MarkDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateMarkById(mark, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Mark mark) {
        logger.info("delete method " + MarkDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteMarkById(mark.getId(), connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Mark> findAll() {
        return null;
    }

    @Override
    public Mark findById(Integer id) {
        logger.info("find by id method " + MarkDaoImpl.class);
        Connection connection = connectionPool.takeConnection();
        Mark mark = null;
        try {
            mark = findMarkById(id, connection);
        } catch (SQLException throwables) {
            logger.error(throwables);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return mark;

    }

    private Mark saveMark(Mark mark, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE_MARK, new String[]{"id"});
        statement.setString(1, mark.getDescription());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        mark.setId(id);
        statement.close();
        resultSet.close();
        return mark;
    }

    private Boolean updateMarkById(Mark mark, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MARK_BY_ID);
        statement.setString(1, mark.getDescription());
        statement.setInt(2, mark.getId());
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Boolean deleteMarkById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MARK_BY_ID);
        statement.setInt(1, id);
        Boolean result = Objects.equals(statement.executeUpdate(), 1);
        statement.close();
        return result;
    }

    private Mark findMarkById(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_MARK_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Mark mark;
        if (resultSet.next()) {
            mark = new Mark(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
        } else {
            mark = null;
        }
        statement.close();
        resultSet.close();
        return mark;
    }
}
