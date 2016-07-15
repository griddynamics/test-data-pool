package com.griddynamics.qa.datapool.datatypes;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.DataTypeFactory;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Alexey Lyanguzov.
 */
public interface Tourist extends IDataType{
    static Tourist newInstance(){
        return DataTypeFactory.create(Tourist.class);
    }

    String getName();
    List<String> getCountries();
    Integer getAge();
    Gender getGender();
    LocalDate getBirthdayLDate();
    Date getBirthdayDate();
    Calendar getBirthdayCalendar();
}
