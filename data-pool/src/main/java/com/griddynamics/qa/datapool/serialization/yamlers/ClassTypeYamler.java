package com.griddynamics.qa.datapool.serialization.yamlers;

import com.griddynamics.qa.datapool.DataPoolException;
import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.serialization.Marshaller;
import com.griddynamics.qa.datapool.serialization.Yamler;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Alexey Lyanguzov.
 */
public class ClassTypeYamler extends Yamler {
    public ClassTypeYamler() {
        super(new Tag("!DataTypeKey"), Class.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object construct(Node node) {
        String value = (String) Marshaller.CONSTRUCTOR.constructScalar((ScalarNode)node);
        String datatypeClassName = value.replaceFirst("interface ", "");
        try {
            Marshaller.CONSTRUCTOR.setCurrentkeyType((Class<? extends IDataType>)Class.forName(datatypeClassName));
            return Marshaller.CONSTRUCTOR.getCurrentkeyType();
        } catch (ClassNotFoundException e) {
            throw new DataPoolException(e);
        }
    }

    @Override
    public Node representData(Object data) {
        return Marshaller.REPRESENTER.representScalar(getTag(), data.toString());
    }
}
