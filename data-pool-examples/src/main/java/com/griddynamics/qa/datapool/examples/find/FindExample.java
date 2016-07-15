package com.griddynamics.qa.datapool.examples.find;

import com.griddynamics.qa.datapool.DataCollection;
import com.griddynamics.qa.datapool.DataPool;
import com.griddynamics.qa.datapool.DataTypeFactory;
import com.griddynamics.qa.datapool.FilterCriterion;
import com.griddynamics.qa.datapool.examples.aux.SuperUser;
import com.griddynamics.qa.datapool.examples.aux.User;
import com.griddynamics.qa.datapool.matchers.StandardMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

import static com.griddynamics.qa.datapool.examples.aux.util.Printer.prettyPrint;
import static com.griddynamics.qa.datapool.FilterCriterion.by;
import static com.griddynamics.qa.datapool.matchers.StandardMatchers.*;


/**
 * Demonstrates different ways to
 * - access entity properties
 * - filter fetched data by different criteria.
 * - store fetched data.
 *
 * @author Alexey Lyanguzov.
 */
public class FindExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindExample.class);
    private final static LocalDate DATE_1958_01_01 = LocalDate.of(1958, Month.JANUARY, 01);

    public static void main(String[] args) throws Exception {
        LOGGER.info("START");

        List<User> users = createUsers();
        List<SuperUser> superusers = createSuperUsers();
        injectData(users, superusers);

        showFieldValuesAccessExample(users);

        prettyPrint(DataPool.find(User.class), "All fetched User entities -> .find(User.class) =>");

        prettyPrint(DataPool.find(User.class, new FilterCriterion("firstUserName", "is", "User 2")),
                    "All Users having specific first name -> .find(User.class, new FilterCriterion('firstUserName', 'is', 'User 2')) =>");

        prettyPrint(DataPool.find(User.class, by("name2", "is", null)),
                    "Filter by absent field -> .find(User.class, by('name2', 'is', null)) =>");

        prettyPrint(DataPool.find(User.class, by("age", StandardMatchers.IS_GREATER, 12)),
                    "All users older that 12 -> .find(User.class, by('age', StandardMatchers.IS_GREATER, 12)) =>");

        prettyPrint(DataPool.find(User.class,
                    FilterCriterion.by("age", IS_LESS, 42),
                    FilterCriterion.by("lastUserName", IS, "Male")),
                    "All users younger than 42 and having specific last name -> .find(User.class," +
                     " FilterCriterion.by('age', IS_LESS, 42)," +
                     " FilterCriterion.by('lastUserName', IS, 'Male')) =>");

        prettyPrint(DataPool.find(User.class, by("lastname", CONTAINS, "er")),
                "All users which lastname contains 'er' -> .find(User.class, by('lastname', CONTAINS, 'er') =>");

       prettyPrint(DataPool.find(User.class, by("birthday", BEFORE_DATE, DATE_1958_01_01)),
                "All users having birthday before 1958-01-01 -> .find(User.class, by('birthday', BEFORE_DATE, DATE_1958_01_01) =>");

       prettyPrint(DataPool.find(User.class, by("birthday", AFTER_DATE, DATE_1958_01_01)),
                "All users having birthday after 1958-01-01 -> .find(User.class, by('birthday', AFTER_DATE, DATE_1958_01_01) =>");

        LOGGER.info("Storing data fetched to default location.");
        DataPool.store();
        LOGGER.info("---");

        LOGGER.info("FINISH");
    }

    private static void injectData(List<User> users, List<SuperUser> superusers) throws NoSuchFieldException, IllegalAccessException {
        Field fetchedData= DataPool.class.getDeclaredField("FETCHED_DATA");
        fetchedData.setAccessible(true);
        DataCollection data = (DataCollection)fetchedData.get(null);
        data.put(User.class, users);
        data.put(SuperUser.class, superusers);
    }

    private static void showFieldValuesAccessExample(List<User> users) {
        User user = users.get(0);

        LOGGER.info("Different ways to get entity field values: via generic getter or by specific getters");
        LOGGER.info("Id={} | user.getId()={}", user.get("id", Integer.class), user.getId());
        LOGGER.info("First name = {} | user.getFirstUserName()={}", user.get("firstUserName", String.class), user.getFirstUserName());
        LOGGER.info("Last name={} | user.getLastUserName()={}", user.get("lastUserName", String.class), user.getLastUserName());
        LOGGER.info("Age={} | user.getAge()={}", user.get("age", Integer.class), user.getAge());
        LOGGER.info("Birthday={} | user.getBirthday()={}", user.get("birthday", LocalDate.class), user.getBirthday());

        LOGGER.info("Different ways to list entity properties.");
        LOGGER.info("User, listProperties: {}", user.listProperties());
        LOGGER.info("DataTypeFactory.....listProperties()={}", DataTypeFactory.create(SuperUser.class).listProperties());
    }

    private static List<SuperUser> createSuperUsers() {
        return new LinkedList<SuperUser>(){{
            add(DataTypeFactory.create(SuperUser.class).set("name", "SuperName"));
        }};
    }

    private static List<User> createUsers() {
        final LocalDate date_1957_10_04 = LocalDate.of(1957, Month.OCTOBER, 04);
        final LocalDate date_1957_11_03 = LocalDate.of(1957, Month.NOVEMBER, 03);
        final LocalDate date_1958_05_15 = LocalDate.of(1958, Month.MAY, 15);

        User user = DataTypeFactory.create(User.class)
            .set("id", 123)
            .set("firstUserName", "First User")
            .set("lastUserName", "Male")
            .set("age", 10)
            .set("birthday", date_1957_11_03);

        User user2 = User.newUser().set("firstName", "User 2").set("age", 22).set("lastName", "Female");
        User user3 = User.newUser().set("firstName", "User 3").set("age", 42);
        User customer1 = User.newUser().set("firstName", "Cust").set("age", 55).set("lastName", "Mer");
        User customer2 = User.newUser().set("firstName", "Cust Jr").set("age", 50).set("lastName", "Mer");
        User customer3 = User.newUser().set("firstName", "Thirddy").set("age", 48).set("lastName", "Ustem");

        User sp1 = User.newUser().set("firstName", "Sputnik-1").set("lastName", "R7").set("birthday", date_1957_10_04);
        User sp2 = User.newUser().set("firstName", "Sputnik-2").set("lastName", "R7").set("birthday", date_1957_11_03);
        User sp3 = User.newUser().set("firstName", "Sputnik-3").set("lastName", "R7").set("birthday", date_1958_05_15);

        return new LinkedList<User>(){{
            add(user);
            add(user2);
            add(user3);
            add(customer1);
            add(customer2);
            add(customer3);
            add(sp1);
            add(sp2);
            add(sp3);
        }};
    }
}
