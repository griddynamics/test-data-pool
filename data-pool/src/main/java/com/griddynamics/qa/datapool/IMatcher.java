package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatype.IDataType;

import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
public interface IMatcher{
    /**
     * @param propName - property name
     * @param value - property value
     * @return matcher implementation as a Predicate class to be run
     */
    Predicate<IDataType> getMatcher(String propName, Object value);

    /**
     * @return name of the matcher
     */
    default String name(){
        return this.getClass().getSimpleName();
    }
}
