package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.matchers.aux.IComparableMatcher;
import com.griddynamics.qa.datapool.matchers.aux.RelationSign;

import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
class IsLessMatcher implements IComparableMatcher {
    @Override @SuppressWarnings("all")
    public Predicate<IDataType> getMatcher(String propName, Object value) {
        return buildMatcher(propName, value, RelationSign.LESS_THAN);
    }
}
