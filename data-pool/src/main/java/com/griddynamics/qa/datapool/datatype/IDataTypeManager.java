package com.griddynamics.qa.datapool.datatype;

import java.util.List;

/**
 * A DataType managers shall know how to fetch(which fetchers to use), build etc instances of concrete data type.
 *
 * @author ELozovan
 * Created: 2016-06-21.
 */
public interface IDataTypeManager<DATA_TYPE> {
    /**
     * Fetches data from remote data source.
     * This method is called by DataPool#fetch() for all registered DataType manager types.
     */
    List<DATA_TYPE> fetch();
}
