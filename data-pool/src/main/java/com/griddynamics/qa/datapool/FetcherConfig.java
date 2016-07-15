package com.griddynamics.qa.datapool;

import java.util.Map;
import java.util.UUID;

/**
 * A fetcher's config container object.
 *
 * @author ELozovan
 * Created: 2016-06-22.
 */
public class FetcherConfig {
    private String name = UUID.randomUUID().toString();
    private Map<String, Object> properties;

    public String getName() {
        return name;
    }

    public void setName(String fetcherName) {
        this.name = fetcherName;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> newProperties) {
        this.properties = newProperties;
    }
}