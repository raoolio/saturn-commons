package com.saturn.commons.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * Handler para mapa de Strings
 * @author rdelcid
 */
public class MapStringHandler implements ResultSetHandler<Map<String,String>>
{
    /** Map of strings */
    private Map<String,String> map;

    /** Key column to retrieve */
    private int keyCol;

    /** Value column to retrieve */
    private int valCol;



    /**
     * Empty map handler
     */
    public MapStringHandler() {
        this(new HashMap(),1,2);
    }



    /**
     * Handler for retrieving first two columns
     * @param map Destination map
     */
    public MapStringHandler(Map<String, String> map) {
        this(map,1,2);
    }



    /**
     * Constructor
     * @param map Destination map
     * @param keyColumn Column used to retrieve map key
     * @param valueColumn Column used to retrieve map value
     */
    public MapStringHandler(Map<String, String> map,int keyColumn,int valueColumn) {
        this.map = map;
        this.keyCol= keyColumn;
        this.valCol= valueColumn;
    }



    @Override
    public Map<String, String> handle(ResultSet rs) throws SQLException {
        while (rs.next()) {
            map.put(rs.getString(keyCol), rs.getString(valCol));
        }
        return map;
    }

}
