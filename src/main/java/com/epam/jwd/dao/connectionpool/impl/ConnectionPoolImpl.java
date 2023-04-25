package com.epam.jwd.dao.connectionpool.impl;

import com.epam.jwd.dao.connectionpool.ConnectionPool;
import com.epam.jwd.dao.connectionpool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ConnectionPoolImpl implements ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);
    private static final ConnectionPool INSTANCE = new ConnectionPoolImpl();
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/rent_cars";
    private static final String USER = "postgres";
    private static final String PASS = "ADMIN";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final Integer CONNECTION_POOL_SIZE = 25;
    private final Queue<ProxyConnection> availableConnection = new ArrayBlockingQueue<>(CONNECTION_POOL_SIZE);
    private final List<ProxyConnection> usedConnection = new CopyOnWriteArrayList<>();
    private static boolean initialized = false;

    private ConnectionPoolImpl() {
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean init() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (!initialized) {
            initialized = addConnectionPool(CONNECTION_POOL_SIZE);
        }
        return initialized;
    }


    @Override
    public void shutdown() {
        closeConnections(availableConnection);
        closeConnections(usedConnection);
    }

    @Override
    public synchronized Connection takeConnection() {
        if (!initialized){
            init();
        }
        while (availableConnection.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
               logger.error(e);
            }
        }
        ProxyConnection connection = availableConnection.poll();
        usedConnection.add(connection);
        return connection;
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        if (usedConnection.remove(connection)) {
            availableConnection.add((ProxyConnection) connection);
            this.notifyAll();
        }
    }

    private boolean addConnectionPool(int amount) {
        for (int i = 0; i < amount; i++) {
            try {
                Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
                final ProxyConnection proxyConnection = new ProxyConnection(this, connection);
                availableConnection.add(proxyConnection);
            } catch (SQLException e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }

    private void closeConnections(Collection<ProxyConnection> connections) {
        connections.forEach(this::closeConnection);
    }

    private void closeConnection(ProxyConnection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
