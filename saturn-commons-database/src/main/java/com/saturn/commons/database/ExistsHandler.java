package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Handler for validating if row exists
 * @author raoolio
 */
public class ExistsHandler implements ResultSetHandler<Boolean> {

    @Override
    public Boolean handle(ResultSet rs) throws SQLException {
        return rs.next();
    }
    
}
