package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 * ResultSet Handler for retrieving a list of Longs
 * @author rdelcid
 */
public class LongListHandler extends BaseHandler<List<Long>>
{

    /**
     * Constructor that retrieves data from first column
     */
    public LongListHandler() {
        super(1);
    }


    /**
     * Retrieves a long from the given column
     * @param column Column number
     */
    public LongListHandler(int column) {
        super(column);
    }


    @Override
    public List<Long> handle(ResultSet rs) throws SQLException {
        List<Long> l= new LinkedList<Long>();

        while (rs.next()) {
            l.add(rs.getLong(column));
        }

        return l;
    }

}
