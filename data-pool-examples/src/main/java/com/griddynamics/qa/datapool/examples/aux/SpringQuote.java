package com.griddynamics.qa.datapool.examples.aux;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Taken from https://spring.io/guides/gs/consuming-rest/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpringQuote {
    private String type;
    private SpringValue value;

    public SpringQuote() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SpringValue getValue() {
        return value;
    }

    public void setValue(SpringValue value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SpringQuote{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}