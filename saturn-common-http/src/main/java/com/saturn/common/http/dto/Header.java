package com.saturn.common.http.dto;


/**
 * HTTP Header bean
 * @author rdelcid
 */
public class Header
{
    private String id;
    private String value;

    /**
     * Constructor
     * @param id Header ID
     * @param value Header value
     */
    public Header(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return id+"="+value;
    }


}
