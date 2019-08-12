package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Integer number parser
 * @author raoolio
 */
public class IntegerHandler extends BaseHandler<Integer> {


    /**
     * Constructor that retrieves data from first column
     */
    public IntegerHandler() {
        super(1);
    }


    /**
     * Constructor
     * @param column Number of column
     */
    public IntegerHandler(int column) {
        super(column);
    }


    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        int res=0;
        if (rs.next())
            res= rs.getInt(column);
        return res;
    }

}
