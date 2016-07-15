package com.griddynamics.qa.datapool.examples.seriliazation;

import com.griddynamics.qa.datapool.serialization.Marshaller;
import com.griddynamics.qa.datapool.serialization.Yamler;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.math.BigDecimal;

/**
 * @author Alexey Lyanguzov.
 */
public class BigDecimalYamler extends Yamler {
    public BigDecimalYamler() {
        super(new Tag("!BigDecimal"), BigDecimal.class);
    }

    @Override
    public Object construct(Node node) {
        return new BigDecimal(((ScalarNode) node).getValue());
    }

    @Override
    public Node representData(Object data) {
        return Marshaller.REPRESENTER.representScalar(getTag(), data.toString());
    }
}
