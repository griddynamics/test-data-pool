package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatype.IDataType;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author Alexey Lyanguzov.
 */
public class DataTypeFactory{

    /**
     * Creates a proxy instance of given data-type
     * @param typeClass - class of actual data-type
     * @param <T> - actual data-type
     * @return - new proxy instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends IDataType> T create(Class<T> typeClass){
        return (T) Proxy.newProxyInstance(
            typeClass.getClassLoader(),
            new Class<?>[]{typeClass},
            new DataTypeProxy(typeClass)
        );
    }

    /**
     * Creates a proxy instance of given data-type filling by given properties
     * @param typeClass - class of actual data-type
     * @param <T> - actual data-type
     * @param propsMap - map of properties to fill for new proxied object
     * @return - new proxy instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends IDataType> T create(Class<T> typeClass, Map propsMap){
        return (T) Proxy.newProxyInstance(
            typeClass.getClassLoader(),
            new Class<?>[]{typeClass},
            new DataTypeProxy(typeClass, propsMap)
        );
    }

    /**
     * @return given proxy object. Necessary to organize fluent interface
     */
    @SuppressWarnings("unchecked")
    static <T extends IDataType> T returnFluent(DataTypeProxy proxy, Class<T> typeClass){
        return (T) Proxy.newProxyInstance(
            typeClass.getClassLoader(),
            new Class<?>[]{typeClass},
            proxy
        );
    }

}
