package com.griddynamics.qa.datapool.examples.seriliazation;

import com.griddynamics.qa.datapool.serialization.Marshaller;
import com.griddynamics.qa.datapool.serialization.Yamler;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.awt.*;

/**
 * @author Alexey Lyanguzov.
 */
public class PointYamler extends Yamler {
    public PointYamler() {
        super(new Tag("!java.awt.Point"), Point.class);
    }

    @Override
    public Object construct(Node node) {
        String strPoint = ((ScalarNode) node).getValue();
        String[] coords = strPoint.split("::");
        return new Point(Double.valueOf(coords[0]).intValue(), Double.valueOf(coords[1]).intValue());
    }

    @Override
    public Node representData(Object data) {
        Point point = (Point)data;
        return Marshaller.REPRESENTER.representScalar(getTag(), String.format("%s::%s", point.getX(), point.getY()));
    }
}
