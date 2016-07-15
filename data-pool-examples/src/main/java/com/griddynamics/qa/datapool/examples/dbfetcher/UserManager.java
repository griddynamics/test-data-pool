package com.griddynamics.qa.datapool.examples.dbfetcher;

import com.griddynamics.qa.datapool.FetcherConfig;
import com.griddynamics.qa.datapool.examples.aux.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

/**
 * Sample Manager for concrete DataType (User).
 *
 * Created: 2016-06-22.
 */
public class UserManager extends AbstractRelationalDbAwareManager<User> {
    public UserManager(DataSource ds) { super(ds); }

    public List<User> fetch() {
        String queryString = "Select * from User";
        FetcherConfig fc = buildConfigWithQueryAndDataSource(queryString);
        SqlRowSet resultSet = runDbFetching(fc);

        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            users.add(User.newUser()
                    .set("id", resultSet.getInt("id"))
                    .set("loginName", resultSet.getString("loginName"))
                    .set("firstUserName", resultSet.getString("firstUserName"))
                    .set("middleUserName", resultSet.getString("middleUserName"))
                    .set("lastUserName", resultSet.getString("lastUserName"))
                    .set("age", resultSet.getInt("age")));
        }

        return users;
    }
}