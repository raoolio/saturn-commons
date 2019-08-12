package com.saturn.commons.database;

import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Base Data Handler
 */
abstract class BaseHandler<T> implements ResultSetHandler<T> {

    /** Column to fetch */
    protected int column;


    /**
     * Constructor
     * @param column
     */
    public BaseHandler(int column) {
        this.column = column;
    }

}
