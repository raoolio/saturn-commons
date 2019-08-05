package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * ResultSet Handler for retrieving a list of Longs
 * @author rdelcid
 */
public class LongListHandler implements ResultSetHandler<List<Long>>
{
    /** Column to retrieve */
    private int column;



    /**
     * Retrieves a long from the first column
     */
    public LongListHandler() {
    }



    /**
     * Retrieves a long from the given column
     * @param column Column number
     */
    public LongListHandler(int column) {
        this.column = column;
    }


    @Override
    public List<Long> handle(ResultSet rs) throws SQLException {
        List<Long> l= new LinkedList<Long>();

        while (rs.next()) {
            l.add(rs.getLong(1));
        }

        return l;
    }

}
