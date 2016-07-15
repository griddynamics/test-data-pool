package com.griddynamics.qa.datapool.fetchers;

import com.griddynamics.qa.datapool.datatypes.Tourist;
import com.griddynamics.qa.datapool.util.DbManipulations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author ELozovan
 * Created: 2016-06-29.
 */
public class DbFetcherTest extends AbstractFetcherTest {
    private DataSource testDbDataSource;

    @BeforeClass
    public void setUp() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("data_pool_test_config.xml");
        testDbDataSource = (DataSource)context.getBean("testDataSource");

        List<String> queries = new LinkedList<>();

        queries.add("create table TOURISTS (name varchar(45));");
        queries.add("insert into TOURISTS  values ('Perv'); ");
        queries.add("insert into TOURISTS  values ('Vtor'); ");

        new DbManipulations().executeScripts(queries, testDbDataSource);
    }

    @Test
    public void testRun() throws Exception {
        SqlRowSet resultSet = new DbFetcher(buildStandardConfig(testDbDataSource, "select * from tourists")).run();
        assertNotNull(resultSet);

        List<Tourist> tourists = new LinkedList<>();
        while (resultSet.next()) {
            tourists.add(Tourist.newInstance()
                    .set("name", resultSet.getString("name")));
        }
        assertEquals(tourists.size(), 2);
        assertEquals(tourists.get(0).get("name", String.class), "Perv");
        assertEquals(tourists.get(1).get("name", String.class), "Vtor");
    }
}