package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 * ResultSet Handler for retrieving a list of Integers
 * @author rdelcid
 */
public class IntegerListHandler extends BaseHandler<List<Integer>>
{

    /**
     * Constructor that retrieves data from first column
     */
    public IntegerListHandler() {
        super(1);
    }
    
    
    /**
     * Retrieves the given column number
     * @param column
     */
    public IntegerListHandler(int column) {
        super(column);
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
