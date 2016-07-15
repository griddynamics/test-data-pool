package com.griddynamics.qa.datapool.serialization;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Alexey Lyanguzov.
 */
public abstract class Yamler extends AbstractConstruct implements Represent{
    private final Tag tag;
    private final Class<?> catchType;
    private boolean multiRepresenter = false;

    public Yamler(Tag tag, Class<?> catchType) {
        this.tag = tag;
        this.catchType = catchType;
    }

    public Tag getTag() {
        return tag;
    }

    public Class<?> getCatchType() {
        return catchType;
    }

    public boolean isMultiRepresenter() {
        return multiRepresenter;
    }

    public void setMultiRepresenter(boolean multiRepresenter) {
        this.multiRepresenter = multiRepresenter;
    }

    public Representer getRepresenter(){
        return null;
    }
}
