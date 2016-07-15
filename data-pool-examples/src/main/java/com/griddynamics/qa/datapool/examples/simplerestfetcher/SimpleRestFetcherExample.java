package com.griddynamics.qa.datapool.examples.simplerestfetcher;

import com.griddynamics.qa.datapool.DataPool;
import com.griddynamics.qa.datapool.datatype.IDataTypeManager;
import com.griddynamics.qa.datapool.examples.aux.QuoteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.griddynamics.qa.datapool.examples.aux.util.Printer.prettyPrint;

/**
 * Standard SimpleRestFetcher usage example.
 * Answers the question: "How-to fetch data from via a Web-Service".
 * NOTE: Based on https://spring.io/guides/gs/consuming-rest/
 * and uses resources described there.
 *
 * @author ELozovan.
 * Created: 2016-07-04.
 */
public class SimpleRestFetcherExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRestFetcherExample.class);

    /**
     * There is a Spring quote Web-Service described at https://spring.io/guides/gs/consuming-rest/
     * Task - fetch list of quotes.
     */
    public static void main(String[] args) throws Exception {
        LOGGER.info("START.");

        // A *Manager instance shall know "how-to" get corresponding data from data-source.
        IDataTypeManager<QuoteData> quoteManager = new QuoteManager("http://gturnquist-quoters.cfapps.io/api/");

        // Register manager instances, so  DataPool "knows" whom to call.
        DataPool.registerManagerForDataType(quoteManager, QuoteData.class);

        // Fetch the data actually.
        DataPool.fetch();

        // Let's see what was fetched.
        prettyPrint(DataPool.find(QuoteData.class), "DataPool.find(QuoteData.class) =>");

        LOGGER.info("FINISHED.");
    }
}