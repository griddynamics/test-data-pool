package com.griddynamics.qa.datapool.fetchers;

import com.griddynamics.qa.datapool.FetcherConfig;

/**
 * A common interface for Data Pool's fetchers.
 * @author ELozovan
 * Created: 2016-06-21.
 */
public interface IFetcher<RESULT_TYPE> {
    /**
     * Standard name of the key to access a settings object in the fetcher configuration properties.
     * Implementation shall cast the object to a proper type.
     */
    String KEY_NAME_SETTINGS = "FC_SETTINGS";

    /**
     * Standard name of the key to access an additional settings object in the fetcher configuration properties.
     * Implementation shall cast the object to a proper type.
     */
    String KEY_NAME_ADDITIONAL_SETTINGS = "FC_ADDITIONAL";

    void setFetcherConfig(FetcherConfig fetcherConfig);

    /**
     * Triggers the fetching.
     */
    RESULT_TYPE run();
}