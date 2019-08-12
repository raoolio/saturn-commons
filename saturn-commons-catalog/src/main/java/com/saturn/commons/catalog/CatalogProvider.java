package com.saturn.commons.catalog;


/**
 * CatalogProvider interface
 * @author raoolio
 */
public interface CatalogProvider {


    /**
     * Retrieves the ID associated with the given string value
     * @param value String to look
     * @return
     */
    public Integer getId(String value);


    /**
     * Adds the given item to the cache
     * @param id Item ID
     * @param value Item value
     */
    public void addItem(Integer id,String value);


    /**
     * Returns the cache size
     * @return
     */
    public long getSize();


    /**
     * Called to release resources
     */
    public void release();

}
