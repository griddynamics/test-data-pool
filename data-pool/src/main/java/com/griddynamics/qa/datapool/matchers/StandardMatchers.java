package com.griddynamics.qa.datapool.matchers;

import com.griddynamics.qa.datapool.DataPoolException;
import com.griddynamics.qa.datapool.IMatcher;

import java.lang.reflect.Field;

/**
 * @author Alexey Lyanguzov.
 */
public class StandardMatchers{
    public static final IMatcher IS = new IsMatcher();
    public static final IMatcher IS_GREATER = new IsGreaterMatcher();
    public static final IMatcher IS_LESS = new IsLessMatcher();
    public static final IMatcher IS_NOT = new IsNotMatcher();
    public static final IMatcher CONTAINS = new ContainsMatcher();
    public static final IMatcher DOES_NOT_CONTAIN = new DoesNotContainMatcher();
    public static final IMatcher BEFORE_DATE = new BeforeIComparableMatcher();
    public static final IMatcher AFTER_DATE = new AfterIComparableMatcher();
    public static final IMatcher IS_DATE = new IsIComparableMatcher();
    public static final IMatcher COUNT_IS_GREATER = new CountIsGreaterMatcher();
    public static final IMatcher COUNT_IS_LESS = new CountIsLessMatcher();

    public static IMatcher byName(String matcherName){
        Field[] fields = StandardMatchers.class.getDeclaredFields();
        for(Field f : fields){
            if(f.getName().equalsIgnoreCase(matcherName)){
                try {
                    return (IMatcher) f.get(null);
                } catch (IllegalAccessException e) {
                    throw new DataPoolException(e);
                }
            }
        }
        throw new DataPoolException("Unable to find standard matcher " + matcherName);
    }
}
