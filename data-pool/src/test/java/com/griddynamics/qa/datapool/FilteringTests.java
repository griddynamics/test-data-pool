package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatypes.AbsentDataType;
import com.griddynamics.qa.datapool.datatypes.Tourist;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.griddynamics.qa.datapool.FilterCriterion.by;
import static com.griddynamics.qa.datapool.matchers.StandardMatchers.*;
import static com.griddynamics.qa.datapool.datatypes.Gender.Female;
import static com.griddynamics.qa.datapool.datatypes.Gender.Male;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNull;

/**
 * @author Alexey Lyanguzov.
 */
public class FilteringTests {
    private final static LocalDate LD_19571004 = LocalDate.of(1957, Month.OCTOBER, 4);
    private final static LocalDate LD_19571005 = LocalDate.of(1957, Month.OCTOBER, 5);
    private final static LocalDate LD_19571103 = LocalDate.of(1957, Month.NOVEMBER, 3);
    private static Date d_19571004 = null;
    private static Date d_19571005 = null;
    private static Date d_19571103 = null;
    private static Calendar c_19571004 = Calendar.getInstance();
    private static Calendar c_19571005 = Calendar.getInstance();
    private static Calendar c_19571103 = Calendar.getInstance();

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d_19571004 = sdf.parse("1957-10-04");
            d_19571005 = sdf.parse("1957-10-05");
            d_19571103 = sdf.parse("1957-11-03");

            c_19571004.setTime(d_19571004);
            c_19571005.setTime(d_19571005);
            c_19571103.setTime(d_19571103);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void fillData() throws Exception {
        Field fetchedData = DataPool.class.getDeclaredField("FETCHED_DATA");
        fetchedData.setAccessible(true);
        DataCollection data = (DataCollection) fetchedData.get(null);
        data.put(Tourist.class, generateTourists());
        DataPool.store();
        DataPool.load();
    }

    @Test
    public void testAllReturned(){
        assertEquals(DataPool.find(Tourist.class).size(), 3);
    }

    @Test
    public void testEmptyCollectionReturnedWhenFilterByAbsentProperty(){
        assertEquals(DataPool.find(Tourist.class, by("something", IS, "anything")).size(), 0);
    }

    @Test
    public void testNullIsReturnedWhenAbsentDataTypeIsRequested(){
        assertNull(DataPool.find(AbsentDataType.class));
    }

    @Test
    public void testAllReturnedWhenFilterByNullAbsentProperty(){
        assertEquals(DataPool.find(Tourist.class, by("something", IS, null)).size(), 3);
    }

    @Test
    public void filterByStringProperty(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("name", IS, "Tourist 1"));
        assertEquals(filtered.size(), 1);
        Tourist tourist = filtered.get(0);
        assertEquals(tourist.getName(), "Tourist 1");
    }

    @Test
    public void filterByIntPropertyLess(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("age", IS_LESS, 33));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertTrue(t.getAge() < 33, "Age "+ t.getAge() +" expected to be less than 33"));
    }

    @Test
    public void filterByIntPropertyGreater(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("age", IS_GREATER, 10));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertTrue(t.getAge() > 10, "Age "+ t.getAge() +" expected to be greater than 10"));
    }

    @Test
    public void filterByEnumProperty(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("gender", IS_NOT, Female));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertEquals(t.getGender(), Male));
    }

    @Test
    public void filterByContainsInCollectionProperty(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("countries", CONTAINS, "Italy"));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertTrue(t.getCountries().contains("Italy")));
    }

    @Test
    public void filterByNotContainsInCollectionProperty(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("countries", DOES_NOT_CONTAIN, "Finland"));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertTrue(t.getCountries() == null || !t.getCountries().contains("Finland")));
    }

    @Test
    public void filterByCollectionSizeLess(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("countries", COUNT_IS_LESS, 1));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getCountries() == null));
    }

    @Test
    public void filterByCollectionSizeGreater(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("countries", COUNT_IS_GREATER, 2));
        System.out.println(filtered.get(0).getCountries());
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertEquals(t.getCountries().size(), 3));
    }

    @Test
    public void filterByStringContains(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("name", CONTAINS, "st 2"));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertEquals(t.getName(), "Tourist 2"));
    }

    @Test
    public void filterByStringDoesNotContain(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("name", DOES_NOT_CONTAIN, "st 2"));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertFalse(t.getName().equals("Tourist 2")));
    }

    @Test
    public void filterByTwoValuesOfSameProperty(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("age", IS_GREATER, 10), by("age", IS_LESS, 30));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getAge() == 20, "Age "+ t.getAge() +" expected to be 20"));
    }

    @Test
    public void filterByTwoValuesOfDifferentProperties(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("age", IS_GREATER, 10), by("name", IS_NOT, "Tourist 1"));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getAge() == 20, "Age "+ t.getAge() +" expected to be 20"));
    }

    @Test
    public void filterByBeforeDate(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("birthdayLDate", BEFORE_DATE, LD_19571005));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayLDate().isBefore(LD_19571005), "BirthDay_LD "+ t.getBirthdayLDate() +" expected to be before 1957-10-05"));

        filtered = DataPool.find(Tourist.class, by("birthdayDate", BEFORE_DATE, d_19571005));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayDate().before(d_19571005), "BirthDay_Date"+ t.getBirthdayDate() +" expected to be before 1957-10-05"));

        filtered = DataPool.find(Tourist.class, by("birthdayCalendar", BEFORE_DATE, c_19571005));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayCalendar().before(c_19571005), "BirthDay_Calendar"+ t.getBirthdayCalendar() +" expected to be before 1957-10-05"));
    }

    @Test
    public void filterByAfterDate(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("birthdayLDate", AFTER_DATE, LD_19571004));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayLDate().isAfter(LD_19571004), "BirthDay_LD "+ t.getBirthdayLDate() +" expected to be after 1957-10-04"));

        filtered = DataPool.find(Tourist.class, by("birthdayDate", AFTER_DATE, d_19571004));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayDate().after(d_19571004), "BirthDay_Date"+ t.getBirthdayDate() +" expected to be after 1957-10-04"));

        filtered = DataPool.find(Tourist.class, by("birthdayCalendar", AFTER_DATE, c_19571004));
        assertEquals(filtered.size(), 2);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayCalendar().after(c_19571004), "BirthDay_Calendar"+ t.getBirthdayCalendar() +" expected to be after 1957-10-04"));
    }

    @Test
    public void filterByExactDate(){
        List<Tourist> filtered = DataPool.find(Tourist.class, by("birthdayLDate", IS_DATE, LD_19571005));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayLDate().equals(LD_19571005), "BirthDay_LD "+ t.getBirthdayLDate() +" expected to be 1957-10-05"));

        filtered = DataPool.find(Tourist.class, by("birthdayDate", IS_DATE, d_19571004));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayDate().equals(d_19571004), "BirthDay_Date"+ t.getBirthdayDate() +" expected to be 1957-10-04"));

        filtered = DataPool.find(Tourist.class, by("birthdayCalendar", IS_DATE, c_19571103));
        assertEquals(filtered.size(), 1);
        filtered.stream().forEach(t -> assertTrue(t.getBirthdayCalendar().equals(c_19571103), "BirthDay_Calendar"+ t.getBirthdayCalendar() +" expected to be 1957-11-03"));
    }

    private List<Tourist> generateTourists(){
        return new LinkedList<Tourist>(){{
            add(Tourist.newInstance().set("name", "Tourist 1").set("age", 40).set("gender", Male)
                .add("countries", "Italy").add("countries", "Sweden")
                .set("birthdayLDate", LD_19571004)
                .set("birthdayDate", d_19571004)
                .set("birthdayCalendar", c_19571004));
            add(Tourist.newInstance().set("name", "Tourist 2").set("age", 10).set("gender", Male)
                .set("birthdayLDate", LD_19571005)
                .set("birthdayDate", d_19571005)
                .set("birthdayCalendar", c_19571005));
            add(Tourist.newInstance().set("name", "Tourist 3").set("age", 20).set("gender", Female)
                .add("countries", "Italy").add("countries", "Finland").add("countries", "Sweden")
                .set("birthdayLDate", LD_19571103)
                .set("birthdayDate", d_19571103)
                .set("birthdayCalendar", c_19571103));
        }};
    }
}
