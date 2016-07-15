package com.griddynamics.qa.datapool.fetchers;

import com.griddynamics.qa.datapool.FetcherConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * An extremely simple fetcher to get data via REST WSes.
 * Spring's RestTemplate based.
 * NOTE: No SSL/HTTPS, no headers support, no PUT,POST,DELETE support etc. Just plain GET.
 *
 * @author ELozovan
 * Created: 2016-07-04.
 */
public class SimpleRestFetcher<ENTITY> extends AbstractFetcher<ResponseEntity<ENTITY>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRestFetcher.class);
    private RestTemplate restTemplateObject;

    public SimpleRestFetcher(FetcherConfig config) { super(config); }

    @Override
    protected void handleFetcherConfigReset() {
        restTemplateObject = null;
    }

    @Override
    public ResponseEntity<ENTITY> run() {
        return getRestTemplate().getForEntity(getUrl(), getResponseEntityClass());
    }

    public RestTemplate getRestTemplate() {
        if (null == restTemplateObject) {
            restTemplateObject = new RestTemplate();
        }

        return restTemplateObject;
    }

    private String getUrl() {
        String url = "";
        try {
            url = (String)getSettings();
        } catch (ClassCastException e) {
            LOGGER.error("Could not get target URL (as String) from the fetcher's configuration due to ", e);
        }

        return url;
    }

    private Class getResponseEntityClass() {
        Class klazz = null;
        try {
           klazz = (Class)getAdditionalSettings();
        } catch (ClassCastException e) {
            LOGGER.error("Could not get Response entity class from the fetcher's configuration due to ", e);
        }

        return klazz;
    }
}