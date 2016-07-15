package com.griddynamics.qa.datapool.serialization.yamlers;

import com.griddynamics.qa.datapool.DataTypeFactory;
import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.serialization.Marshaller;
import com.griddynamics.qa.datapool.serialization.Yamler;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Alexey Lyanguzov.
 */
public class DataTypeYamler extends Yamler {
    public DataTypeYamler() {
        super(new Tag("!IDataType"), null);
    }

    @Override
    public Object construct(Node node) {
        Map<Object, Object> map = Marshaller.CONSTRUCTOR.constructMapping((MappingNode)node);
        return DataTypeFactory.create(Marshaller.CONSTRUCTOR.getCurrentkeyType(), map);
    }

    @Override
    public Node representData(Object data) {
        if(IDataType.class.isAssignableFrom(data.getClass())){
            return Marshaller.REPRESENTER.representMapping(getTag(), ((IDataType)data).serialize(), true);
        }
        return Marshaller.REPRESENTER.represent(data);
    }
}
