package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatypes.Gender;
import com.griddynamics.qa.datapool.datatypes.Tourist;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * @author Alexey Lyanguzov.
 */
public class DataTypeTests {

    @Test
    public void testSameEmptyEquals(){
        Tourist tourist1 = Tourist.newInstance();
        assertEquals(tourist1, tourist1);
        Tourist tourist2 = tourist1;
        assertEquals(tourist1, tourist2);
        assertEquals(tourist2, tourist1);
    }

    @Test
    public void testEqualsForNull(){
        Tourist tourist1 = Tourist.newInstance();
        assertNotEquals(tourist1, null);
        assertNotEquals(null, tourist1);
    }

    @Test
    public void testEqualsSimilar(){
        Tourist tourist1 = Tourist.newInstance().set("name", "a name").set("age", 31);
        Tourist tourist2 = Tourist.newInstance().set("name", "a name").set("age", 31);
        assertEquals(tourist1, tourist2);
        assertEquals(tourist2, tourist1);
    }

    @Test
    public void testEqualsSimilarWithOtherOrder(){
        Tourist tourist1 = Tourist.newInstance().set("age", 31).set("name", "a name").set("other", "property");
        Tourist tourist2 = Tourist.newInstance().set("name", "a name").set("age", 31).set("other", "property");
        assertEquals(tourist1, tourist2);
        assertEquals(tourist2, tourist1);
    }

    @Test
    public void testEqualsSimilarWithNullProperty(){
        Tourist tourist1 = Tourist.newInstance().set("age", 31).set("name", "a name").set("other", null);
        Tourist tourist2 = Tourist.newInstance().set("name", "a name").set("age", 31);
        assertEquals(tourist1, tourist2);
        assertEquals(tourist2, tourist1);
    }

    @Test
    public void testNotEquals(){
        Tourist tourist1 = Tourist.newInstance().set("age", 31).set("name", "a name");
        Tourist tourist2 = Tourist.newInstance().set("age", 32).set("name", "a name");
        assertNotEquals(tourist1, tourist2);
        assertNotEquals(tourist2, tourist1);
    }

    @Test
    public void testGetType(){
        Tourist tourist1 = Tourist.newInstance().set("age", 31).set("name", "a name")
            .set("gender", Gender.Male).set("other", "some other property");
        assertEquals(tourist1.getType("age"), Integer.class);
        assertEquals(tourist1.getType("gender"), Gender.class);
        assertEquals(tourist1.getType("name"), String.class);
        assertEquals(tourist1.getType("other"), Object.class);
        assertEquals(tourist1.getType("BirthdayDate"), Date.class);
        assertEquals(tourist1.getType("BirthdayCalendar"), Calendar.class);
        assertEquals(tourist1.getType("BirthdayLDate"), LocalDate.class);
    }

}
