package com.saturn.common.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Long Handler that returns NULL if no result found
 */
public class NullLongHandler implements ResultSetHandler<Long> {

    /** Column to retrieve */
    private int column;

    
    /**
     * Constructor
     */
    public NullLongHandler() {
        this(1);
    }

    
    /**
     * Constructor
     * @param column Column number to retrieve
     */
    public NullLongHandler(int column) {
        this.column = column;
    }
    
    
    
    @Override
    public Long handle(ResultSet rs) throws SQLException {
        Long res=null;
        if (rs.next())
            res= rs.getLong(column);
        return res;
    }
    
}
