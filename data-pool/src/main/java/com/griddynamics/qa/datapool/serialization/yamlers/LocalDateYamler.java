package com.griddynamics.qa.datapool.serialization.yamlers;

import com.griddynamics.qa.datapool.serialization.Marshaller;
import com.griddynamics.qa.datapool.serialization.Yamler;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.time.LocalDate;

/**
 * @author Alexey Lyanguzov.
 */
public class LocalDateYamler extends Yamler {
    public LocalDateYamler() {
        super(new Tag("!LocalDate"), LocalDate.class);
    }

    @Override
    public Object construct(Node node) {
        return LocalDate.parse(((ScalarNode) node).getValue());
    }

    @Override
    public Node representData(Object data) {
        return Marshaller.REPRESENTER.representScalar(getTag(), data.toString());
    }
}
