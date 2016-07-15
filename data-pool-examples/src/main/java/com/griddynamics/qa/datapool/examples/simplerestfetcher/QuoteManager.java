package com.griddynamics.qa.datapool.examples.simplerestfetcher;

import com.griddynamics.qa.datapool.FetcherConfig;
import com.griddynamics.qa.datapool.datatype.IDataTypeManager;
import com.griddynamics.qa.datapool.examples.aux.QuoteData;
import com.griddynamics.qa.datapool.examples.aux.SpringQuote;
import com.griddynamics.qa.datapool.fetchers.IFetcher;
import com.griddynamics.qa.datapool.fetchers.SimpleRestFetcher;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Am example of RestFetcher backed manager.
 *
 * @author ELozovan
 * Created: 2016-07-04.
 */
public class QuoteManager implements IDataTypeManager<QuoteData> {
    private String targetURL;

    public QuoteManager(String url) { this.targetURL = url; }

    public String getTargetURL() { return targetURL; }

    @Override
    public List<QuoteData> fetch() {
        SimpleRestFetcher<SpringQuote[]> quoteRestFetcher =
                new SimpleRestFetcher<>(buildConfig(SpringQuote[].class));
        ResponseEntity<SpringQuote[]> response = quoteRestFetcher.run();

        SpringQuote[] quotes = response.getBody();
        List<QuoteData> result = new LinkedList<>();

        for (SpringQuote quote : quotes) {
            result.add(QuoteData.newQuote()
                    .add("id", quote.getValue().getId())
                    .add("type", quote.getType())
                    .add("quote", quote.getValue().getQuote())
            );
        }
        return result;
    }

    private FetcherConfig buildConfig(Object additionalSettings) {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put(IFetcher.KEY_NAME_SETTINGS, getTargetURL());
        propertiesMap.put(IFetcher.KEY_NAME_ADDITIONAL_SETTINGS, additionalSettings);

        FetcherConfig fc = new FetcherConfig();
        fc.setProperties(propertiesMap);

        return fc;
    }
}