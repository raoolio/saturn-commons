package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * Handler for validating if row exists
 * @author raoolio
 */
public class TimestampHandler extends BaseHandler<Timestamp> {


    /**
     * Constructor that retrieves data from first column
     */
    public TimestampHandler() {
        super(1);
    }


    /**
     * Constructor for given column
     * @param column
     */
    public TimestampHandler(int column) {
        super(column);
    }


    @Override
    public Timestamp handle(ResultSet rs) throws SQLException {
        Timestamp t=null;
        if (rs.next())
            t=rs.getTimestamp(column);

        return t;
    }

}
