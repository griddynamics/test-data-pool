package com.griddynamics.qa.datapool.examples.dbfetcher;

import com.griddynamics.qa.datapool.DataPool;
import com.griddynamics.qa.datapool.datatype.IDataTypeManager;
import com.griddynamics.qa.datapool.examples.aux.Address;
import com.griddynamics.qa.datapool.examples.aux.User;
import com.griddynamics.qa.datapool.examples.aux.util.DbScriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

import static com.griddynamics.qa.datapool.examples.aux.util.Printer.prettyPrint;

/**
 * Standard DbFetcher usage example.
 * Answers the question: "How-to fetch data from a relational DB".
 * @author ELozovan.
 */
public class DbFetcherExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbFetcherExample.class);

    /**
     * There is a relational DB storing data we are interested in.
     * In particular - User and Address data.
     * Task - fetch corresponding records from the DB.
     */
    public static void main(String[] args) throws Exception {
        setUpExamplesDb();
        LOGGER.info("START.");

        DataSource targetDbDataSource = getDataSource();

        // A *Manager instance shall know "how-to" get corresponding data from data-source.
        IDataTypeManager<User> userManager = new UserManager(targetDbDataSource);
        IDataTypeManager<Address> addressManager = new AddressManager(targetDbDataSource);

        // Register manager instances, so  DataPool "knows" whom to call.
        DataPool.registerManagerForDataType(userManager, User.class);
        DataPool.registerManagerForDataType(addressManager, Address.class);

        // Fetch the data actually.
        DataPool.fetch();

        // Let's see what was fetched.
        prettyPrint(DataPool.find(User.class), "DataPool.find(User.class) = >");
        prettyPrint(DataPool.find(Address.class), "DataPool.find(Address.class) =>");

        LOGGER.info("FINISHED.");
    }

    private static DataSource getDataSource() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("data_pool_app_config.xml");
        return (DataSource)context.getBean("exmplsDataSource");
    }

    private static void setUpExamplesDb() {
        List<String> queries = getPredefinedQueries();

        new DbScriptExecutor().executeScripts(queries, getDataSource());
    }

    private static List<String> getPredefinedQueries() {
        List<String> queries = new LinkedList<>();

        queries.add("CREATE TABLE User (id bigint, age bigint, firstUserName varchar(255), lastUserName varchar(255), loginName varchar(255), middleUserName varchar(255), psswd varchar(255));");
        queries.add("INSERT INTO User(id, age, firstUserName, lastUserName, loginName, middleUserName, psswd) VALUES(0, 33, 'Svjatosslav', 'Rybov', 'sr', '-', 'qaz');");
        queries.add("INSERT INTO User(id, age, firstUserName, lastUserName, loginName, middleUserName, psswd) VALUES(1, 20, 'Mlad', 'Prozvon', 'mp', '-', 'wasd');");
        queries.add("INSERT INTO User(id, age, firstUserName, lastUserName, loginName, middleUserName, psswd) VALUES(2, 22, 'Dvad', 'Vtorok', 'dv', '-', 'wasd');");
        queries.add("INSERT INTO User(id, age, firstUserName, lastUserName, loginName, middleUserName, psswd) VALUES(3, 23, 'jUnka', 'Trinaddvats', 'jUT', '-', 'wasd');");
        queries.add("INSERT INTO User(id, age, firstUserName, lastUserName, loginName, middleUserName, psswd) VALUES(4, 24, 'Brazina', 'Litsev', 'bl', '_', 'wasd');");
        queries.add("INSERT INTO User(id, age, firstUserName, lastUserName, loginName, middleUserName, psswd) VALUES(5, 14, 'Podro', 'Dvasjuzh', 'pd', '-', 'wasd');");
        queries.add("INSERT INTO User(id, age, firstUserName, lastUserName, loginName, middleUserName, psswd) VALUES(6, 12, 'Vjun', 'Djuzhin', 'vd', '-', 'wasd');");

        queries.add("CREATE TABLE Address (\n" +
                "  id bigint,\n" +
                "  city varchar(255),\n" +
                "  email varchar(255),\n" +
                "  ownerId bigint,\n" +
                "  phone varchar(255),\n" +
                "  postCode varchar(255),\n" +
                "  region varchar(255),\n" +
                "  street1 varchar(255));");

        queries.add("INSERT INTO Address\n" +
                "(id, city, email, ownerId, phone, postCode, region, street1)\n" +
                "VALUES(0, 'Lihograd', 'lh@gr.dd', 0, '+79008007060', '445336', 'Tajga', 'ul. Lenina');");
        queries.add("INSERT INTO Address\n" +
                "(id, city, email, ownerId, phone, postCode, region, street1)\n" +
                "VALUES(1, 'Svijazhzk', 'sv@ya.ru', 5, '+79018007060', '145336', 'Valdaj', 'pr. Pravdy');");
        queries.add("INSERT INTO Address\n" +
                "(id, city, email, ownerId, phone, postCode, region, street1)\n" +
                "VALUES(2, 'Zadonsk', 'zd@leto.rf', 6, '+79028007060', '245336', 'Donnino', 'per. Vesennij');");
        queries.add("INSERT INTO Address\n" +
                "(id, city, email, ownerId, phone, postCode, region, street1)\n" +
                "VALUES(3, 'Ljotovo', 'samo@let.vo', 4, '+79038007060', '345336', 'Ra', 'bul. Vintov');");
        return queries;
    }
}