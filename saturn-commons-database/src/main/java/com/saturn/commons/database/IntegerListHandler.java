package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * ResultSet Handler for retrieving a list of Integers
 * @author rdelcid
 */
public class IntegerListHandler implements ResultSetHandler<List<Integer>>
{
    /** Column to retrieve */
    private int column;



    /**
     * Retrieves the first column
     */
    public IntegerListHandler() {
        this(1);
    }



    /**
     * Retrieves the given column number
     * @param column
     */
    public IntegerListHandler(int column) {
        this.column = column;
    }



    @Override
    public List<Integer> handle(ResultSet rs) throws SQLException {
        List<Integer> l= new LinkedList<Integer>();

        while (rs.next()) {
            l.add(rs.getInt(column));
        }

        return l;
    }

}
