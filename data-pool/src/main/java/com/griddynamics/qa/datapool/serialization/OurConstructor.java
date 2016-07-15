package com.griddynamics.qa.datapool.serialization;

import com.griddynamics.qa.datapool.datatype.IDataType;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexey Lyanguzov.
 */
public class OurConstructor extends Constructor {
    private Class<? extends IDataType> currentkeyType;

    public Class<? extends IDataType> getCurrentkeyType() {
        return currentkeyType;
    }

    public OurConstructor addConstructor(Tag catchTag, Construct construct) {
        this.yamlConstructors.put(catchTag, construct);
        return this;
    }

    public void setCurrentkeyType(Class<? extends IDataType> currentkeyType) {
        this.currentkeyType = currentkeyType;
    }

    @Override
    public Object constructObject(Node node) {
        return super.constructObject(node);
    }

    @Override
    public Object constructScalar(ScalarNode node) {
        return super.constructScalar(node);
    }

    @Override
    public List<? extends Object> constructSequence(SequenceNode node) {
        return super.constructSequence(node);
    }

    @Override
    public Set<? extends Object> constructSet(SequenceNode node) {
        return super.constructSet(node);
    }

    @Override
    public Object constructArray(SequenceNode node) {
        return super.constructArray(node);
    }

    @Override
    public Set<Object> constructSet(MappingNode node) {
        return super.constructSet(node);
    }

    @Override
    public Map<Object, Object> constructMapping(MappingNode node) {
        return super.constructMapping(node);
    }
}
