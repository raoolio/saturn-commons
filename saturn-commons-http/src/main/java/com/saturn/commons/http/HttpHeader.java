package com.saturn.commons.http;

import java.util.List;


/**
 * HTTP HttpHeader bean
 * @author rdelcid
 */
public interface HttpHeader
{

    /**
     * Returns the Header ID
     * @return
     */
    public String getId();


    /**
     * Returns the first value
     * @return
     */
    public String getFirstValue();


    /**
     * Returns the Header values
     * @return
     */
    public List<String> getValues();


    /**
     * Adds header value
     * @param val
     */
    public void addValue(String... val);

}
