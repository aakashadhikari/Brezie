package com.ascentbrezie.brezie.data;

/**
 * Created by ADMIN on 09-11-2015.
 */
public class KeyValuePairData {

    String key,value;

    public KeyValuePairData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
