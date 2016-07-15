package com.griddynamics.qa.datapool.examples.aux.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Just a sugar for pretty collection printing to avoid one more dependency.
 */
public class Printer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Printer.class);
    private static final String NEW_LINE = System.lineSeparator();

    /**
     * Prints out the list with the note, separating entries by new line.
     */
    public static void prettyPrint(Collection collection, String note) {
        LOGGER.info("{}{}{}{}", NEW_LINE, note, NEW_LINE, convertCollectionToString(collection));
    }

    /**
     * Prints out the list, separating entries by new line.
     */
    public static void prettyPrint(Collection collection) {
        prettyPrint(collection, "");
    }

    /**
     * Converts the collection to a single string. Collection entries are separated by new line.
     */
    public static String convertCollectionToString(Collection collection) {
        return String.join(NEW_LINE, (Iterable<? extends CharSequence>) collection.stream().map(o -> o.toString()).collect(Collectors.toList()));
    }
}