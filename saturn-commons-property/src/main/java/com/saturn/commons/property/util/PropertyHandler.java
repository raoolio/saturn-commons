package com.saturn.commons.property.util;

import com.saturn.commons.property.impl.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Property Handler
 */
public class PropertyHandler implements ResultSetHandler<Map<Key,String>>{

    @Override
    public Map<Key, String> handle(ResultSet rs) throws SQLException {
        Map<Key, String> m= new HashMap();

        while (rs.next()) {
            Key k= new Key(rs.getString(1),rs.getString(2));
            String v= rs.getString(3);
            m.put(k, v);
        }

        return m;
    }

}
