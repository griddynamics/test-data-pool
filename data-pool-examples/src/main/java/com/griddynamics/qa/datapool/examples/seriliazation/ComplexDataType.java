package com.griddynamics.qa.datapool.examples.seriliazation;

import com.griddynamics.qa.datapool.datatype.IDataType;

import java.awt.*;
import java.math.BigDecimal;
import java.net.URI;

/**
 * @author Alexey Lyanguzov.
 */
public interface ComplexDataType extends IDataType {

    BigDecimal getBigDecimal();
    URI getURI();
    Point getPoint();

}

