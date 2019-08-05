package com.saturn.common.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * ResultSet Handler for retrieving a list of Strings
 * @author rdelcid
 */
public class StringListHandler implements ResultSetHandler<List<String>>
{
    /** Column to retrieve */
    private int column;

    
    /**
     * Retrieves the first column
     */
    public StringListHandler() {
        this(1);
    }

    
    /**
     * Retrieves the given column
     * @param column Column number
     */
    public StringListHandler(int column) {
        this.column = column;
    }
    
    

    @Override
    public List<String> handle(ResultSet rs) throws SQLException {
        List<String> l= new LinkedList<String>();

        while (rs.next()) {
            l.add(rs.getString(column));
        }

        return l;
    }

}
