package com.griddynamics.qa.datapool.examples.dbfetcher;

import com.griddynamics.qa.datapool.FetcherConfig;
import com.griddynamics.qa.datapool.datatype.IDataTypeManager;
import com.griddynamics.qa.datapool.fetchers.DbFetcher;
import com.griddynamics.qa.datapool.fetchers.IFetcher;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Manager, contains common methods for dealing with relational DB.
 *
 * @author ELozovan.
 * Created: 2016-06-28.
 */
public abstract class AbstractRelationalDbAwareManager<DATA_TYPE> implements IDataTypeManager<DATA_TYPE> {
    private final DataSource dataSource;

    public AbstractRelationalDbAwareManager(DataSource ds) {
        this.dataSource = ds;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    protected FetcherConfig buildConfigWithQueryAndDataSource(String queryString) {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put(IFetcher.KEY_NAME_ADDITIONAL_SETTINGS, queryString);
        propertiesMap.put(IFetcher.KEY_NAME_SETTINGS, dataSource);

        FetcherConfig fc = new FetcherConfig();
        fc.setProperties(propertiesMap);

        return fc;
    }

    protected SqlRowSet runDbFetching(FetcherConfig fc) {
        IFetcher<SqlRowSet> dbFetcher = new DbFetcher(fc);
        return dbFetcher.run();
    }
}