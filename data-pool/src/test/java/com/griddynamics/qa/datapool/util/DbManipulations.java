package com.griddynamics.qa.datapool.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * A DB utility to be used in tests.
 */
public class DbManipulations {
    private static final String CONNECTION_STRING = "jdbc:hsqldb:mem:testdb;shutdown=false";
    private static final String USER_NAME = "SA";
    private static final String PASSWORD = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(DbManipulations.class);

    public void executeScriptsAgainstBuiltInHsqlDb(List<String> queries) {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            Connection holdingConnection = getConnectionImpl();
            for (String sql : queries) {
                PreparedStatement statement = holdingConnection.prepareStatement(sql);
                statement.execute();
            }

            holdingConnection.commit();
            holdingConnection.close();
        } catch (Exception ex) {
            LOGGER.error("Error during database initialization", ex);
            throw new RuntimeException("Error during HSQL database initialization", ex);
        }
    }

    public void executeScripts(List<String> queries, DataSource ds) {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(ds);
        try {
            queries.forEach(jdbcTemplateObject::execute);
        } catch (Exception ex) {
            LOGGER.error("Error upon executing SQL queries.", ex);
            throw new RuntimeException("Error during database interactions", ex);
        }
    }

    private Connection getConnectionImpl() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
    }
}
