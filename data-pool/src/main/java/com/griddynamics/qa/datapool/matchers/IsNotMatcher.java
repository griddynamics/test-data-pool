package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.IMatcher;

import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
class IsNotMatcher implements IMatcher {
    @Override
    public Predicate<IDataType> getMatcher(String propName, Object value) {
        return (o) -> {
            Object prop = o.get(propName, Object.class);
            return !prop.equals(value);
        };
    }
}
