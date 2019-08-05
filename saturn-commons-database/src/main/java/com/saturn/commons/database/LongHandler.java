package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * LongHandler
 * @author raoolio
 */
public class LongHandler implements ResultSetHandler<Long> {

    private int column;

    
    /**
     * Retrieves a long from the first column
     */
    public LongHandler() {
        this(1);
    }

    
    /**
     * Retrieves a long from the given column
     * @param column Number of column
     */
    public LongHandler(int column) {
        this.column = column;
    }
    
    
    
    @Override
    public Long handle(ResultSet rs) throws SQLException {
        long res=0;
        if (rs.next())
            res= rs.getLong(column);
        return res;
    }
    
}
