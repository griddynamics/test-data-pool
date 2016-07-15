package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.matchers.aux.IComparableMatcher;
import com.griddynamics.qa.datapool.matchers.aux.RelationSign;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
class CountIsLessMatcher implements IComparableMatcher {
    @Override @SuppressWarnings("all")
    public Predicate<IDataType> getMatcher(String propName, Object value) {
        return (o) -> {
            Object obj = o.get(propName, Object.class);
            if(obj == null){
                return true; // Questionable but needed to find empty collections
            }
            else if(Collection.class.isAssignableFrom(obj.getClass()) && Integer.class.isAssignableFrom(value.getClass())){
                Collection collection = (Collection)obj;
                return buildMatcher((Comparable)value, Integer.class, RelationSign.LESS_THAN).test((Comparable)collection.size());
            }
            return false; //Unknown comparison
        };
    }
}
