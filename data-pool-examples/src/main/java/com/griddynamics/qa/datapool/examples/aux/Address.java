package com.griddynamics.qa.datapool.examples.aux;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.DataTypeFactory;

public interface Address extends IDataType {
    static Address newAddress(){
        return DataTypeFactory.create(Address.class);
    }

    Integer getId();
    String getCity();
    String getPhone();
    String getStreet1();
    Integer getOwnerId();
}
