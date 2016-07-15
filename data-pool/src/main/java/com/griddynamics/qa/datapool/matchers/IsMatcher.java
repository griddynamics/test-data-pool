package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.IMatcher;

import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
class IsMatcher implements IMatcher {
    @Override @SuppressWarnings("all")
    public Predicate<IDataType> getMatcher(String propName, Object value) {
        return (o) -> {
            Object prop = o.get(propName, Object.class);
            if(prop == null && value != null) return false;
            if(prop == null && value == null) return true;
            return prop.equals(value);
        };
    }
}
