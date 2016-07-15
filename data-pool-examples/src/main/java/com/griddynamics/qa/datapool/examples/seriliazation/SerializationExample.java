package com.griddynamics.qa.datapool.examples.seriliazation;

import com.griddynamics.qa.datapool.DataCollection;
import com.griddynamics.qa.datapool.DataPool;
import com.griddynamics.qa.datapool.DataTypeFactory;
import com.griddynamics.qa.datapool.serialization.Marshaller;

import java.awt.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates ability to create custom responder/constructor for complex type
 *  that will be used during serialization of DataType.
 *
 * @author Alexey Lyanguzov.
 */
public class SerializationExample {

    public static void main(String[] args) throws Exception {
        System.out.println("START");

        fillExampleData();

        // We need to add custom 'yamlers' which serialize/deserialize our type into/from yaml
        // Try to comment out any of them to see the difference
        Marshaller.addCustomYamler(new BigDecimalYamler());
        Marshaller.addCustomYamler(new URIYamler());
        Marshaller.addCustomYamler(new PointYamler());

        DataPool.store();
        DataPool.load();

        ComplexDataType complexData = DataPool.find(ComplexDataType.class).get(0);
        System.out.println(complexData.getBigDecimal());
        System.out.println(complexData.getURI());
        System.out.println(complexData.getPoint());

        System.out.println("END");
    }

    private static void fillExampleData() throws Exception {
        injectData(new ArrayList<ComplexDataType>(){{
            add(DataTypeFactory.create(ComplexDataType.class)
                .set("BigDecimal", new BigDecimal("12345678901234567890"))
                .set("URI", new URI("http://somehost:3456/path"))
                .set("Point", new Point(11,21)));
        }});
    }

    private static void injectData(List<ComplexDataType> complexData) throws NoSuchFieldException, IllegalAccessException {
        Field fetchedData= DataPool.class.getDeclaredField("FETCHED_DATA");
        fetchedData.setAccessible(true);
        DataCollection data = (DataCollection)fetchedData.get(null);
        data.put(ComplexDataType.class, complexData);
    }

}

