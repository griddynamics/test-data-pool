package com.griddynamics.qa.datapool.serialization;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.serialization.yamlers.CalendarYamler;
import com.griddynamics.qa.datapool.serialization.yamlers.ClassTypeYamler;
import com.griddynamics.qa.datapool.serialization.yamlers.DataTypeYamler;
import com.griddynamics.qa.datapool.serialization.yamlers.LocalDateYamler;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Alexey Lyanguzov.
 */
public class Marshaller {
    public static final OurRepresenter REPRESENTER = new OurRepresenter();
    public static final OurConstructor CONSTRUCTOR = new OurConstructor();

    private static final Set<Yamler> yamlers = new HashSet<>();

    static {
        Marshaller.addCustomYamler(new ClassTypeYamler());
        Marshaller.addCustomYamler(new DataTypeYamler());
        Marshaller.addCustomYamler(new LocalDateYamler());
        Marshaller.addCustomYamler(new CalendarYamler());
    }

    public static Yaml getYamlToStore(){
        yamlers.forEach(y -> {
            if(y.isMultiRepresenter()){
                REPRESENTER.addMultiRepresenter(y.getCatchType(), y);
            } else{
                REPRESENTER.addRepresenter(y.getCatchType(), y);
            }
        });
        return new Yaml(REPRESENTER);
    }

    public static Yaml getYamlToLoad(){
        yamlers.forEach(y -> {
            CONSTRUCTOR.addConstructor(y.getTag(), y);
        });
        return new Yaml(CONSTRUCTOR);
    }

    public static void addCustomYamler(Yamler yamler){
        yamlers.add(yamler);
    }

}
