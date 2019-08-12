package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 * ResultSet Handler for retrieving a list of Strings
 * @author rdelcid
 */
public class StringListHandler extends BaseHandler<List<String>>
{

    /**
     * Retrieves data from the first column
     */
    public StringListHandler() {
        super(1);
    }


    /**
     * Retrieves the given column
     * @param column Column number
     */
    public StringListHandler(int column) {
        super(column);
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
