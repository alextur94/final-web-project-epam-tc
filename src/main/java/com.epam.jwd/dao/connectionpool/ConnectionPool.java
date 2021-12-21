package com.epam.jwd.dao.connectionpool;

import java.sql.Connection;

public interface ConnectionPool {
    boolean init();
    void shutdown();
    Connection takeConnection();
    void returnConnection(Connection connection);

}
