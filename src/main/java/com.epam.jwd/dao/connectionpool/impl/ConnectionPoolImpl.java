package com.epam.jwd.dao.connectionpool.impl;;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPoolImpl implements ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String BASE = "rent_cars";
    private static final String USER = "root";
    private static final String PASS = "1111";
    private static final int INITIAL_POOL_SIZE = 32;
    private static final int SIMPLE_CONNECTIONS = 8;

    private boolean initialized = false;

    private static final ReentrantLock getInstanceLock = new ReentrantLock();

    private final BlockingDeque<ProxyConnection> availableConnections = new LinkedBlockingDeque<>();
    private final BlockingDeque<ProxyConnection> givenAwayConnections = new LinkedBlockingDeque<>();

    private static ConnectionPoolImpl instance;

    private ConnectionPoolImpl(){}

    public static ConnectionPool getInstance(){
        getInstanceLock.lock();
        if(Objects.isNull(instance)){
            instance = new ConnectionPoolImpl();
            instance.init();
        }
        getInstanceLock.unlock();
        return instance;
    }


    @Override
    public synchronized Connection takeConnection() {
        if (!availableConnections.isEmpty()){
            ProxyConnection connection = availableConnections.poll();
            givenAwayConnections.add(connection);
            logger.info("Connection taken");
            return connection;
        } else if (givenAwayConnections.size() < INITIAL_POOL_SIZE){
            try {
                createConnectionAndAddToPool();
                ProxyConnection connection = availableConnections.poll();
                assert connection != null;
                givenAwayConnections.add(connection);
                logger.info("Connection taken");
                return connection;
            } catch (SQLException | ClassNotFoundException e) {
                logger.error(e);
            }
        }

        while (availableConnections.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error(e);
            }
        }
        ProxyConnection connection = availableConnections.poll();
        givenAwayConnections.add(connection);
        logger.info("Connection taken");
        return connection;
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        if (givenAwayConnections.remove((ProxyConnection) connection)){
            logger.info("Connection returned");
            try {
                if (!connection.getAutoCommit()){
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (availableConnections.size() + givenAwayConnections.size() < SIMPLE_CONNECTIONS
                    || (availableConnections.isEmpty() && givenAwayConnections.size() < INITIAL_POOL_SIZE)){
                availableConnections.add((ProxyConnection) connection);
            } else {
                closeConnection((ProxyConnection) connection);
            }
        }
        notify();
    }

    @Override
    public void shutdown() {
        closeConnection(availableConnections);
        closeConnection(givenAwayConnections);
        availableConnections.clear();
        givenAwayConnections.clear();
        initialized = false;
    }

    @Override
    public void init() {
        if (!initialized){
            try{
                for (int i = 0; i < SIMPLE_CONNECTIONS; i++) {
                    createConnectionAndAddToPool();
                }
                initialized = true;
                logger.info("ConnectionPool initialized");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void createConnectionAndAddToPool() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL + BASE, USER, PASS);
        ProxyConnection proxyConnection = new ProxyConnection(connection, this);
        availableConnections.add(proxyConnection);
    }

    private void closeConnection(Deque<ProxyConnection> connections){
        connections.forEach(this::closeConnection);
    }

    private void closeConnection(ProxyConnection connection){
        logger.info("Connection closed");
        try {
            connection.realClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
