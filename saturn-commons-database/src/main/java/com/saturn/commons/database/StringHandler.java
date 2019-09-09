package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Manejador para obtener resultado String de primera columna devuelta.
 * @author rdelcid
 */
public class StringHandler extends BaseHandler<String>
{
    /** Default value */
    private String defaultValue;


    /**
     * Constructor with column=1
     */
    public StringHandler() {
        super(1);
    }


    public StringHandler(int column) {
        this(column,null);
    }


    public StringHandler(String defaultValue) {
        this(1,defaultValue);
    }


    public StringHandler(int column,String defaultValue) {
        super(column);
        this.defaultValue = defaultValue;
    }


    @Override
    public String handle(ResultSet rs) throws SQLException {
        String res=defaultValue;

        if (rs.next())
            res=rs.getString(column);

        return res;
    }

}
