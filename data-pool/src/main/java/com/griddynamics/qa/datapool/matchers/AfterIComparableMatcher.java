package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.matchers.aux.IComparableMatcher;
import com.griddynamics.qa.datapool.matchers.aux.RelationSign;

import java.util.function.Predicate;

/**
 * Compares date type fields (AFTER case). Supported date types:
 * java.util.Date
 * java.util.Calendar
 * java.time.chrono.ChronoLocalDate
 *
 * @author ELozovan
 * Created: 2016-07-05.
 */
class AfterIComparableMatcher implements IComparableMatcher {
    @Override
    public Predicate<IDataType> getMatcher(String propName, Object value) {
        return buildMatcher(propName, value, RelationSign.GREATER_THAN);
    }
}
