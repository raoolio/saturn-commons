package com.saturn.common.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Integer number parser
 * @author raoolio
 */
public class IntegerHandler implements ResultSetHandler<Integer> {

    /** Column to retrieve */
    private int column;


    /**
     * Constructor with column 1
     */
    public IntegerHandler() {
        this(1);
    }


    /**
     * Constructor
     * @param column Number of column
     */
    public IntegerHandler(int column) {
        this.column = column;
    }


    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        int res=0;
        if (rs.next())
            res= rs.getInt(column);
        return res;
    }

}
