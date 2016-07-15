package com.griddynamics.qa.datapool.fetchers;

import com.google.common.base.Preconditions;
import com.griddynamics.qa.datapool.FetcherConfig;

/**
 * Abstract implementation of the IFetcher. Contains common methods for fetchers.
 *
 * @author ELozovan
 * Created: 2016-06-22.
 */
public abstract class AbstractFetcher<RESULT_TYPE> implements IFetcher<RESULT_TYPE>{
    private FetcherConfig fetcherConfig;

    /**
     * @param config cannot be null and shall contain not-null properties.
     */
    public AbstractFetcher(FetcherConfig config) {
        setFetcherConfig(config);
    }

    /**
     * @param config cannot be null and shall contain not-null properties.
     */
    public void setFetcherConfig(FetcherConfig config) {
        Preconditions.checkNotNull(config, "Fetcher's configuration cannot be null.");
        Preconditions.checkNotNull(config.getProperties(), "Fetcher configuration's properties cannot be null.");

        this.fetcherConfig = config;
        handleFetcherConfigReset();
    }

    public FetcherConfig getFetcherConfig() {
        return fetcherConfig;
    }

    /**
     * This method is to be called by #setFetcherConfig() one in order to re-set concrete fetcher instance configuration.
     * Inherited classes shall define how to handle "set-new configuration" event.
     *
     * E.g. if a client injected new SqlDataSource (new connection string etc) then a DbFetcher may need to re-create DB connection(s).
     */
    protected abstract void handleFetcherConfigReset();

    /**
     * Just a "sugar" to simplify access to the standard "ADDITIONAL_SETTINGS" property.
     */
    protected Object getAdditionalSettings() {
        return fetcherConfig.getProperties().get(KEY_NAME_ADDITIONAL_SETTINGS);
    }

    /**
     * Just a "sugar" to simplify access to the standard "SETTINGS" property.
     */
    protected Object getSettings() {
        return fetcherConfig.getProperties().get(KEY_NAME_SETTINGS);
    }
}