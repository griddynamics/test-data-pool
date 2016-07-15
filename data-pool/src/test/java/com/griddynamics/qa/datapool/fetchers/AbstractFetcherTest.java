package com.griddynamics.qa.datapool.fetchers;

import com.griddynamics.qa.datapool.FetcherConfig;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFetcherTest {

    protected FetcherConfig buildStandardConfig(Object settings, Object additionalSettings) {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put(IFetcher.KEY_NAME_ADDITIONAL_SETTINGS, additionalSettings);
        propertiesMap.put(IFetcher.KEY_NAME_SETTINGS, settings);

        FetcherConfig fc = new FetcherConfig();
        fc.setProperties(propertiesMap);

        return fc;
    }
}