package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Integer Handler that returns NULL if not result found
 */
public class NullIntegerHandler implements ResultSetHandler<Integer> {

    /** Column to retrieve */
    private int column;


    /**
     * Constructor with column 1
     */
    public NullIntegerHandler() {
        this(1);
    }


    /**
     * Constructor
     * @param column Column number to retrieve
     */
    public NullIntegerHandler(int column) {
        this.column = column;
    }


    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        Integer res=null;
        if (rs.next())
            res= rs.getInt(column);
        return res;
    }

}
