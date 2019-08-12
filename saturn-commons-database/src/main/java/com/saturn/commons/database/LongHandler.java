package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * LongHandler
 * @author raoolio
 */
public class LongHandler extends BaseHandler<Long> {
    

    /**
     * Constructor that retrieves data from first column
     */
    public LongHandler() {
        super(1);
    }

    
    /**
     * Retrieves a long from the given column
     * @param column Number of column
     */
    public LongHandler(int column) {
        super(column);
    }



    @Override
    public Long handle(ResultSet rs) throws SQLException {
        long res=0;
        if (rs.next())
            res= rs.getLong(column);
        return res;
    }

}
