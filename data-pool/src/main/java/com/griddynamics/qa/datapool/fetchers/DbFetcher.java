package com.griddynamics.qa.datapool.fetchers;

import com.griddynamics.qa.datapool.FetcherConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

/**
 * A simple fetcher which fetches data from a relational DB.
 *
 * @author ELozovan
 * Created: 2016-06-22.
 */
public class DbFetcher extends AbstractFetcher<SqlRowSet> implements IFetcher<SqlRowSet> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbFetcher.class);
    private JdbcTemplate jdbcTemplateObject;

    public DbFetcher(FetcherConfig fetcherConfig) {
        super(fetcherConfig);
    }

    public SqlRowSet run() {
        return getJdbcTemplateObject().queryForRowSet(getQuery());
    }

    @Override
    protected void handleFetcherConfigReset() {
        jdbcTemplateObject = null;
    }

    /**
     * "Additional settings" object is expected to be an SQL-query to use in case od DBFetcher.
     */
    private String getQuery() {
        try {
            return (String)getAdditionalSettings();
        } catch (ClassCastException e) { // A client might have injected not a String instance into the settings.
            LOGGER.error("Could not get DB query data from the fetcher's configuration due to ", e);
        }

        return null;
    }

    private JdbcTemplate getJdbcTemplateObject() {
        if (null == jdbcTemplateObject) {
            jdbcTemplateObject = new JdbcTemplate(getDbDataSource());
        }

        return jdbcTemplateObject;
    }

    private DataSource getDbDataSource() {
        DataSource ds = null;
        try {
            ds = (DataSource)getSettings();
        } catch (ClassCastException e) { // A client might have injected not a DataSource instance into the settings.
            LOGGER.error("Could not get DB connection data from the fetcher's configuration due to ", e);
        }

        return ds;
    }
}