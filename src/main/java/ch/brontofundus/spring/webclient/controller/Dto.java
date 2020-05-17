package ch.brontofundus.spring.webclient.controller;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Dto {

    private String key;
    private String value;

    @JsonCreator
    public Dto() {
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
