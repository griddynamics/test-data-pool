package com.griddynamics.qa.datapool.examples.aux;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Taken from https://spring.io/guides/gs/consuming-rest/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpringValue {

    private Long id;
    private String quote;

    public SpringValue() {
    }

    public Long getId() {
        return this.id;
    }

    public String getQuote() {
        return this.quote;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "SpringValue{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                '}';
    }
}