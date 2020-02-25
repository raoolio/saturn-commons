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
     * @return Value if found, <b>NULL</b> otherwise.
     */
    public String getValue(String path,String id);


    /**
     * Retrieves the value associated with the given id
     * @param path Property path
     * @param id Property id
     * @param defaultValue Default value if parameter not found
     * @return Value if found, <b>defaultValue</b> otherwise.
     */
    public String getValue(String path,String id,String defaultValue);


    /**
     * Associates the given value with the id
     * @param path Property path
     * @param id Property id
     * @param value Property value
     * @return <b>True</b> if operation successful, <b>False</b> otherwise.
     */
    public boolean setValue(String path,String id,String value);


    /**
     * Called to release resources
     */
    public void release();

}
