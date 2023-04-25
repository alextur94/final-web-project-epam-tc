package com.epam.jwd.dao.api;

import com.epam.jwd.dao.model.mark.Mark;

import java.sql.Connection;
import java.sql.SQLException;

public interface MarkDao extends Dao<Mark, Integer>{
    Mark saveMark(Mark mark, Connection connection) throws SQLException;
    Boolean updateMarkById(Mark mark, Connection connection) throws SQLException;
}
