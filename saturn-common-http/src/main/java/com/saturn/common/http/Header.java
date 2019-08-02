package com.saturn.common.http;



/**
 * HTTP Header bean
 * @author rdelcid
 */
class Header
{
    private String id;
    private String value;

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
