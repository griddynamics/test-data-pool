package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.matchers.StandardMatchers;

import java.util.function.Predicate;

/**
 * @author Alexey Lyanguzov.
 */
public class FilterCriterion {
    private final String propName;
    private final IMatcher matcher;
    private final Object value;

    /**
     * Utility method to shortcut calling of new FilterCriterion(...)
     */
    public static FilterCriterion by(String propName, String strMatcher, Object value){
        return new FilterCriterion(propName, strMatcher, value);
    }

    /**
     * Utility method to shortcut calling of new FilterCriterion(...)
     */
    public static FilterCriterion by(String propName, IMatcher matcher, Object value){
        return new FilterCriterion(propName, matcher, value);
    }

    /**
     * Utility method to shortcut calling of new FilterCriterion(...)
     */
    public static FilterCriterion by(FilterCriterion criterion){
        return criterion;
    }

    /**
     * Creates new instance of filtering criterion for given matcher name
     * @param propName - property to filter by
     * @param strMatcher - one of matchers registered in {@link StandardMatchers} class
     * @param value - value that will be used by matcher to compare with
     */
    public FilterCriterion(String propName, String strMatcher, Object value) {
        this(propName, StandardMatchers.byName(strMatcher), value);
    }

    /**
     * Creates new instance of filtering criterion for given matcher instance
     * @param propName - property to filter by
     * @param matcher - matcher for the property
     * @param value - value that will be used by matcher to compare with
     */
    public FilterCriterion(String propName, IMatcher matcher, Object value) {
        this.propName = propName;
        this.matcher = matcher;
        this.value = value;
    }

    /**
     * @return matcher implementation as a Predicate class to be run
     */
    public Predicate<IDataType> getPredicate(){
        return matcher.getMatcher(propName, value);
    }
}
