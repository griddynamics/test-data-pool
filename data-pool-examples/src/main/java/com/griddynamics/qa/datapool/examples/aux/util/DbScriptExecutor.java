package com.griddynamics.qa.datapool.examples.aux.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * An auxiliary DB utility.
 */
public class DbScriptExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbScriptExecutor.class);

    public void executeScripts(List<String> queries, DataSource ds) {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(ds);
        try {
            queries.forEach(jdbcTemplateObject::execute);
        } catch (Exception ex) {
            LOGGER.error("Error upon executing SQL queries.", ex);
            throw new RuntimeException("Error during database interactions", ex);
        }
    }
}