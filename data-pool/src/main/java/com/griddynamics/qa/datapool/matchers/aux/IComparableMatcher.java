package com.griddynamics.qa.datapool.matchers.aux;

import com.griddynamics.qa.datapool.DataPoolException;
import com.griddynamics.qa.datapool.IMatcher;
import com.griddynamics.qa.datapool.datatype.IDataType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Builds matchers for different comparison cases.
 *
 * @author ELozovan
 * Created: 2016-07-06.
 */
public interface IComparableMatcher extends IMatcher {
    default Predicate<IDataType> buildMatcher(String propName, Object value, RelationSign sign) {
        return (o) -> {
            Object obj = o.get(propName, Object.class);
            return buildMatcher((Comparable)value, o.getType(propName), sign).test((Comparable)obj);
        };
    }

    default Predicate<Comparable> buildMatcher(final Object value, Class<?> type, RelationSign sign){
        return propertyValue -> {
            if(!Comparable.class.isAssignableFrom(type)){
                throw new DataPoolException("Not comparable type %s", type);
            }
            Object valueToCompare = value;
            if(!type.isAssignableFrom(value.getClass())){
                valueToCompare = IDataType.doCast(type, value);
            }
            if (null != propertyValue) {
                if(Comparable.class.isAssignableFrom(propertyValue.getClass())) {
                    switch (sign) {
                        case GREATER_THAN:
                            return propertyValue.compareTo(valueToCompare) > 0;
                        case EQUAL:
                            return propertyValue.compareTo(valueToCompare) == 0;
                        case LESS_THAN:
                            return propertyValue.compareTo(valueToCompare) < 0;
                    }
                } else {
                    throw  new IllegalArgumentException(String.format("Type of [%s] field is not supported by the matcher. " +
                        "It should implement Comparable", propertyValue.getClass().getName()));
                }
            }
            return false; //Unknown comparison
        };
    }
}
