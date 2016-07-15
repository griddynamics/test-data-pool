package com.griddynamics.qa.datapool.datatype;

import com.griddynamics.qa.datapool.DataPoolException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * A common interface for client-defined data types.
 */
public interface IDataType {
    static Object doCast(Class<?> toType, Object value){
        try{
            return toType.cast(value);
        }
        catch(ClassCastException exc){
            try {
                Method valueOf = toType.getDeclaredMethod("valueOf", String.class);
                return valueOf.invoke(null, String.valueOf(value));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new DataPoolException(e);
            }
        }
    }

    /**
     * Sets the property of DataType object.
     * E.g.
     *   <code>user.set("firstName", "Alexey")</code>
     *   <code>product.set("price", new Price(20, USD))</code>
     *
     * Note: Setting null as a value will remove the property from collection (unset property).
     *
     * @param name - name of data-type property.
     * @param value - value of data-type property.
     * @param <R> - the actual data-type.
     * @return instance of object of class R extending IDataType.
     */
    <R extends IDataType> R set(String name, Object value);

    /**
     * Adds a value to collection property.
     * E.g.
     *   <code>
     *       user.add("nicknames", "BlackTester").add("EvilTester")
     *       List<String> nicknames = user.get("nicknames") // returns ["BlackTester", "EvilTester"]
     *   </code>
     *
     * @param name - name of data-type property of <b>Collection</b> type.
     * @param value - value of data-type property.
     * @param <R> - the actual data-type.
     * @return instance of object of class R extending IDataType.
     */
    <R extends IDataType> R add(String name, Object value);

    /**
     * Returns the property of data-type object.
     * E.g.
     *   <code>Integer age = user.get("age", Integer.class)</code>
     *   <code>Price price = product.get("price", Price.class)</code>
     *
     * @param name - the name of property.
     * @param returnClass - class of returnType.
     * @param <T> - return type (the type of returnClass).
     * @return the value of property or null if absent.
     */
    <T> T get(String name, Class<T> returnClass);

    /**
     * @return list of capitalized property names. Only those names which has appropriate getter will be returned.
     */
    List<String> listProperties();

    /**
     * @return list of all available property names (capitalized).
     */
    List<String> listAllProperties();

    /**
     * @return map of key, value entries used to serialize current data-type
     */
    Map<String, Object> serialize();

    Class<?> getType(String name);
}
