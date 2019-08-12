package com.saturn.commons.catalog.dao;

import com.saturn.commons.catalog.CatalogItemHandler;
import java.util.List;


/**
 * Catalog DAO
 */
public interface CatalogDao {


    /**
     * Retrieves the ID associated with the given value
     * @param value Value to search
     * @return
     */
    public Integer getValueId(String value) throws Exception;


    /**
     * Retrieves the ID associated with the given value. If it doesn't exist, it
     * creates it
     * @param value Value to search
     * @return
     */
    public Integer getOrCreateValueId(String value) throws Exception;


    /**
     * Loads the items returned by the given query to the handler
     * @param query CatalogQuery to execute
     * @param handler Item handler
     */
    public void loadItems(CatalogQuery query,CatalogItemHandler handler);


    /**
     * Updates the given list of counters in batch
     * @param params Counter list
     * @return
     */
    public int[] updateHitCount(List<Object[]> params) throws Exception;


    /**
     * Release resources
     */
    public void release();

}
