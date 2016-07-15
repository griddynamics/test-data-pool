package com.griddynamics.qa.datapool.serialization.yamlers;

import com.griddynamics.qa.datapool.serialization.Marshaller;
import com.griddynamics.qa.datapool.serialization.Yamler;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Alexey Lyanguzov.
 */
public class CalendarYamler extends Yamler {
    public CalendarYamler() {
        super(new Tag("!Calendar"), Calendar.class);
        this.setMultiRepresenter(true);
    }

    @Override
    public Object construct(Node node) {
        Instant instant =  Instant.parse(((ScalarNode) node).getValue());
        Calendar result = Calendar.getInstance();
        result.setTime(Date.from(instant));
        return result;
    }

    @Override
    public Node representData(Object data) {
        Calendar calendar = (Calendar) data;
        return Marshaller.REPRESENTER.representScalar(getTag(), calendar.toInstant().toString());
    }
}
