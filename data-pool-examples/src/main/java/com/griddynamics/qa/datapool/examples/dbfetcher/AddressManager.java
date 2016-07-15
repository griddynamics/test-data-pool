package com.griddynamics.qa.datapool.examples.dbfetcher;

import com.griddynamics.qa.datapool.FetcherConfig;
import com.griddynamics.qa.datapool.examples.aux.Address;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

/**
 * Sample Manager for concrete DataType (Address).
 *
 * @author ELozovan.
 * Created: 2016-06-28.
 */
public class AddressManager extends AbstractRelationalDbAwareManager<Address> {
    public AddressManager(DataSource ds) { super(ds); }

    public List<Address> fetch() {
        String queryString = "Select * from Address";

        FetcherConfig fc = buildConfigWithQueryAndDataSource(queryString);
        SqlRowSet resultSet = runDbFetching(fc);

        List<Address> records = new LinkedList<>();
        while (resultSet.next()) {
            records.add(Address.newAddress()
                    .set("id", resultSet.getInt("id"))
                    .set("city", resultSet.getString("city"))
                    .set("phone", resultSet.getString("phone"))
                    .set("street1", resultSet.getString("street1"))
                    .set("ownerId", resultSet.getInt("ownerId")));
        }

        return records;
    }
}