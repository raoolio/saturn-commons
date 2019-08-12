package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Integer Handler that returns NULL if not result found
 */
public class NullIntegerHandler extends BaseHandler<Integer> {


    /**
     * Constructor that retrieves data from first column
     */
    public NullIntegerHandler() {
        super(1);
    }


    /**
     * Constructor
     * @param column Column number to retrieve
     */
    public NullIntegerHandler(int column) {
        super(column);
    }



    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        Integer res=null;
        if (rs.next())
            res= rs.getInt(column);
        return res;
    }

}
