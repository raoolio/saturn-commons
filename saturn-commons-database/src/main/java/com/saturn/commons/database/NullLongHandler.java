package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Long Handler that returns NULL if no result found
 */
public class NullLongHandler extends BaseHandler<Long> {


    /**
     * Constructor that retrieves data from first column
     */
    public NullLongHandler() {
        super(1);
    }


    /**
     * Constructor
     * @param column Column number to retrieve
     */
    public NullLongHandler(int column) {
        super(column);
    }


    @Override
    public Long handle(ResultSet rs) throws SQLException {
        Long res=null;
        if (rs.next())
            res= rs.getLong(column);
        return res;
    }

}
