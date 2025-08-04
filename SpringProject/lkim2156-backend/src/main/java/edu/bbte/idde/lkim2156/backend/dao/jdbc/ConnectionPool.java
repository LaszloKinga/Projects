package edu.bbte.idde.lkim2156.backend.dao.jdbc;

import edu.bbte.idde.lkim2156.backend.dao.NotFoundException;
import edu.bbte.idde.lkim2156.backend.util.PropertyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;

public final class ConnectionPool {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private final Deque<Connection> pool = new LinkedList<>();
    private static Integer poolSize = Integer.valueOf(PropertyProvider.getProperty("jdbc_pool_size"));


    private ConnectionPool(Integer poolSize, String connectionUrl, String jdbcUserName, String jdbcPassword) {
        // pool felépítése
        try {
            for (int i = 0; i < poolSize; ++i) {
                pool.push(DriverManager.getConnection(connectionUrl, jdbcUserName, jdbcPassword));
            }
            LOG.info("Initialized pool of size {}", poolSize);
        } catch (SQLException e) {
            LOG.error("Connection could not be established", e);
            throw new NotFoundException("Connection could not be established", e);
        }
    }

    public static synchronized ConnectionPool create() {
        if (instance == null) {
            try {
                Class.forName(PropertyProvider.getProperty("jdbc_driver_class"));
                String connectionUrl = PropertyProvider.getProperty("jdbc_db_url");
                String jdbcUserName = PropertyProvider.getProperty("jdbc_name");
                String jdbcPassword = PropertyProvider.getProperty("jdbc_pass");
                instance = new ConnectionPool(poolSize, connectionUrl, jdbcUserName, jdbcPassword);
            } catch (ClassNotFoundException e) {
                LOG.error("Connection pool could not be initialized", e);
                throw new IllegalStateException("Connection pool could not be initialized", e);
            }
        }
        return instance;
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            return create();
        }
        return instance;
    }

    public void closeConnections() {
        try {
            for (Connection connection : pool) {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            LOG.error("Could not close connections", e);
            throw new NotFoundException("Could not close connections", e);
        }
    }

    public synchronized Connection getConnection() {
        if (pool.isEmpty()) {
            LOG.warn("No available connection in the pool.");
            throw new NotFoundException("No connections in pool");
        }
        LOG.info("Giving out connection from pool");
        LOG.info("poolSize: {}, pool.size: {}", poolSize, pool.size());
        return pool.pop();
    }

    public synchronized void returnConnection(Connection connection) {
        if (pool.size() < poolSize) {
            LOG.info("Returning connection to pool.");
            pool.push(connection);
        }
    }
}
