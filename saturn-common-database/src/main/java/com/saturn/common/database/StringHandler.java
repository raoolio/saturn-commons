package com.saturn.common.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Manejador para obtener resultado String de primera columna devuelta.
 * @author rdelcid
 */
public class StringHandler implements ResultSetHandler<String>
{
    /** Default value */
    private String defaultValue;

    public StringHandler() {
    }

    public StringHandler(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    
    @Override
    public String handle(ResultSet rs) throws SQLException
    {
        String res=defaultValue;
        
        if (rs.next())
            res=rs.getString(1);
        
        return res;
    }
    
}
