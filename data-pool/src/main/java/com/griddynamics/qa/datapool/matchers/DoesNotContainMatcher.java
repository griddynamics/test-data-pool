package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.DataPoolException;
import com.griddynamics.qa.datapool.IMatcher;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
class DoesNotContainMatcher implements IMatcher {
    @Override
    public Predicate<IDataType> getMatcher(String propName, Object value) {
        return (o) -> {
            Object obj = o.get(propName, Object.class);
            if(obj == null) return true;
            if(Collection.class.isAssignableFrom(obj.getClass())){
                return !((Collection) obj).contains(value);
            }
            else if(String.class.isAssignableFrom(obj.getClass())){
                return !((String) obj).contains(String.valueOf(value));
            }
            throw new DataPoolException("Unable to apply matcher %s for type %s", name(), obj.getClass());
        };
    }
}
