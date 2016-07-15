package com.griddynamics.qa.datapool.examples.aux;

import com.griddynamics.qa.datapool.DataTypeFactory;
import com.griddynamics.qa.datapool.datatype.IDataType;

/**
 * A DataType to be used in SimpleRestFetcher example.
 * Based on https://spring.io/guides/gs/consuming-rest/ .
 */
public interface QuoteData extends IDataType {
    static QuoteData newQuote(){
        return DataTypeFactory.create(QuoteData.class);
    }

    Long getId();
    String getType();
    String getQuote();
}