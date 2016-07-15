package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatype.IDataType;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alexey Lyanguzov.
 */
class DataTypeProxy implements InvocationHandler, IDataType {
    private static final List<String> myMethods = Stream.of(IDataType.class.getDeclaredMethods())
        .map(Method::getName).collect(Collectors.toList());
    private static final List<String> objectMethods = Stream.of(Object.class.getDeclaredMethods())
        .map(Method::getName).collect(Collectors.toList());
    private final SortedMap<String, Object> properties = new TreeMap<>();
    private final Class<? extends IDataType> targetClass;

    DataTypeProxy(Class<? extends IDataType> targetClass) {
        this.targetClass = targetClass;
    }

    DataTypeProxy(Class<? extends IDataType> targetClass, Map<Object, Object> props) {
        this.targetClass = targetClass;
        props.keySet().forEach(k -> properties.put(k.toString(), props.get(k)));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if(myMethods.contains(methodName) || objectMethods.contains(methodName)){
            return method.invoke(this, args);
        }
        return getByGetter(method);
    }

    @Override @SuppressWarnings("unchecked")
    public <R extends IDataType> R set(String name, Object value) {
        Method getter = findGetter(name);
        if(value == null){
            properties.remove(name);
        }
        else{
            if(getter == null){
                //TODO: warn about setting property without getter (return type is unknown)
            }
            else{
                Class<?> returnType = getter.getReturnType();
                Class<?> realType = value.getClass();
                if(!returnType.isAssignableFrom(realType)){
                    throw new DataPoolException("Attempt to set incompatible value of type '%s' into '%s'. '%s' is expected",
                        realType, name, returnType);
                }
            }
            String propName = name.toLowerCase();
            properties.put(propName, value);
        }
        return DataTypeFactory.returnFluent(this, (Class<R>)targetClass);
    }

    @Override @SuppressWarnings("unchecked")
    public <R extends IDataType> R add(String name, Object value) {
        //TODO: might be different class casts.
        Collection collection = (Collection)properties.get(name);
        if(collection == null){
            collection = new ArrayList();
            properties.put(name, collection);
        }
        collection.add(value);
        return DataTypeFactory.returnFluent(this, (Class<R>)targetClass);
    }

    @Override @SuppressWarnings("unchecked")
    public <T> T get(String name, Class<T> returnClass) {
        String propName = name.toLowerCase();
        return (T)properties.get(propName);
    }

    @Override
    public List<String> listProperties() {
        Method[] methods = targetClass.getMethods();
        return Stream.of(methods)
            .filter(m -> m.getName().startsWith("get") && m.getName().length() > 3)
            .map(m -> getPropName(m.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public List<String> listAllProperties() {
        return new ArrayList(properties.keySet());
    }

    @Override
    public Map<String, Object> serialize() {
        return properties;
    }

    @Override
    public Class<?> getType(String name) {
        Class<?> returnType = Object.class;
        Method getter = findGetter(name);
        if(getter != null){
            returnType = getter.getReturnType();
        }
        return returnType;
    }

    @SuppressWarnings("unchecked")
    private <T> T getByGetter(Method method){
        String methodName = method.getName();
        Class<T> returnType = (Class<T>)method.getReturnType();
        return get(getPropName(methodName), returnType);
    }

    private Method findGetter(String methodName) {
        try {
            return targetClass.getMethod("get" + StringUtils.capitalize(methodName));
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s:%s", targetClass.getSimpleName(), properties.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override @SuppressWarnings("all")
    public boolean equals(Object obj) {
        if(obj == null || !targetClass.isAssignableFrom(obj.getClass())){
            return false;
        }
        return this.toString().equals(obj.toString());
    }

    private String getPropName(String name){
        return name.replace("get", "");
    }
}
