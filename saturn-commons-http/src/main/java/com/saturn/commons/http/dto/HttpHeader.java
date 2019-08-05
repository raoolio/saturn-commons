package com.saturn.commons.http.dto;

import com.saturn.commons.utils.ArrayUtils;
import java.util.List;


/**
 * HTTP HttpHeader bean
 * @author rdelcid
 */
public class HttpHeader
{
    /** Paramter ID */
    private String id;

    /** Parameter values */
    private List<String> values;



    /**
     * Constructor
     * @param id Header ID
     * @param value Header value
     */
    public HttpHeader(String id, String ... value) {
        this.id = id;
        this.values = ArrayUtils.array2List(value);
    }



    /**
     * Constructor
     * @param id Parameter ID
     * @param values Parameter values
     */
    public HttpHeader(String id, List<String> values) {
        this.id = id;
        this.values = values;
    }


    /**
     * Add header value
     * @param value
     */
    public void addValue(String value) {
        this.values.add(value);
    }


    public String getId() {
        return id;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return id+"="+values;
    }


}
