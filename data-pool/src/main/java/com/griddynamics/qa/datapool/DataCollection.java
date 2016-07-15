package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatype.IDataType;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Fetched data container.
 */
public class DataCollection extends LinkedHashMap<Class<? extends IDataType>, List<? extends IDataType>> {
}
