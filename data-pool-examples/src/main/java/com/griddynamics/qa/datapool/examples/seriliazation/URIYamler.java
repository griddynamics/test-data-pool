package com.griddynamics.qa.datapool.examples.seriliazation;

import com.griddynamics.qa.datapool.serialization.Marshaller;
import com.griddynamics.qa.datapool.serialization.Yamler;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.net.URI;

/**
 * @author Alexey Lyanguzov.
 */
public class URIYamler extends Yamler {
    public URIYamler() {
        super(new Tag("!URI"), URI.class);
    }

    @Override
    public Object construct(Node node) {
        return URI.create(((ScalarNode) node).getValue());
    }

    @Override
    public Node representData(Object data) {
        return Marshaller.REPRESENTER.representScalar(getTag(), data.toString());
    }
}
