package com.saturn.commons.catalog.dao;

/**
 * Catalog Queries
 */
public enum CatalogQuery {

    /** SQL for retrieving catalog values */
    SELECT_VALUE,

    /** SQL for storing new catalog values */
    INSERT_VALUE,

    /** SQL for updating hit counter */
    UPDATE_COUNTER,

    /** SQL for retrieving most used items */
    MOST_USED_ITEMS,

    /** SQL for retrieving all items */
    ALL_ITEMS

}
