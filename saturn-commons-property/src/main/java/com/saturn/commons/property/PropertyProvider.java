package com.saturn.commons.property;


/**
 * Application parameter provider
 * @author raoolio
 */
public interface PropertyProvider {


    /**
     * Retrieves the value associated with the given id
     * @param path Property path
     * @param id Property id
     * @return
     */
    public String getValue(String path,String id);


    /**
     * Retrieves the value associated with the given id
     * @param path Property path
     * @param id Property id
     * @param defaultValue Default value if parameter not found
     * @return
     */
    public String getValue(String path,String id,String defaultValue);


    /**
     * Associates the given value with the id
     * @param path Property path
     * @param id Property id
     * @param value Property value
     * @return
     */
    public boolean setValue(String path,String id,String value);


    /**
     * Called to release resources
     */
    public void release();

}
