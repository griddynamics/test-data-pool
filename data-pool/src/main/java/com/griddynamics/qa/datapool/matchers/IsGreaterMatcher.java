package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.IMatcher;
import com.griddynamics.qa.datapool.matchers.aux.IComparableMatcher;
import com.griddynamics.qa.datapool.matchers.aux.RelationSign;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
class IsGreaterMatcher implements IComparableMatcher {
    @Override @SuppressWarnings("all")
    public Predicate<IDataType> getMatcher(String propName, Object rawValue) {
        return buildMatcher(propName, rawValue, RelationSign.GREATER_THAN);
    }
}
