package com.griddynamics.qa.datapool.serialization;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import java.util.Map;

/**
 * @author Alexey Lyanguzov.
 */
public class OurRepresenter extends Representer {

    public OurRepresenter addRepresenter(Class<?> catchClass, Represent represent){
        this.representers.put(catchClass, represent);
        return this;
    }

    public OurRepresenter addMultiRepresenter(Class<?> catchClass, Represent represent){
        this.multiRepresenters.put(catchClass, represent);
        return this;
    }

    @Override
    public Node represent(Object data) {
        return super.represent(data);
    }

    @Override
    public Node representScalar(Tag tag, String value, Character style) {
        return super.representScalar(tag, value, style);
    }

    @Override
    public Node representScalar(Tag tag, String value) {
        return super.representScalar(tag, value);
    }

    @Override
    public Node representSequence(Tag tag, Iterable<?> sequence, Boolean flowStyle) {
        return super.representSequence(tag, sequence, flowStyle);
    }

    @Override
    public Node representMapping(Tag tag, Map<?, ?> mapping, Boolean flowStyle) {
        return super.representMapping(tag, mapping, flowStyle);
    }
}
