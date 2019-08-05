package com.saturn.commons.params;


/**
 * Application parameter provider
 * @author raoolio
 */
public interface ParamProvider {


    /**
     * Retrieves the value associated with the given id
     * @param id Parameter id
     * @return
     */
    public String getValue(String id);


    /**
     * Retrieves the value associated with the given id
     * @param id Parameter id
     * @param defaultValue Default value if parameter not found
     * @return
     */
    public String getValue(String id,String defaultValue);


    /**
     * Associates the given value with the id
     * @param id Parameter id
     * @param value Value
     * @return
     */
    public boolean setValue(String id,String value);


    /**
     * Called to release resources
     */
    public void release();

}
