package com.griddynamics.qa.datapool.examples.aux;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.DataTypeFactory;

import java.time.LocalDate;

/**
 * @author Alexey Lyanguzov.
 */
public interface User extends IDataType {
    static User newUser(){
        return DataTypeFactory.create(User.class);
    }

    Integer getId();
    String getFirstUserName();
    String getMiddleUserName();
    String getLastUserName();
    Integer getAge();
    LocalDate getBirthday();
}
