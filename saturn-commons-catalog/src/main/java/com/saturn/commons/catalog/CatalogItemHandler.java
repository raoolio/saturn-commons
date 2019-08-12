package com.saturn.commons.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.ResultSetHandler;


/**
 * CatalogProvider handler
 * @author raoolio
 */
public class CatalogItemHandler implements ResultSetHandler<Integer> {

    /** Catalog Provider */
    private CatalogProvider provider;


    /**
     * Constructor
     * @param provider CatalogProvider instance
     */
    public CatalogItemHandler(CatalogProvider provider) {
        this.provider = provider;
    }



    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        int n=0;
        rs.setFetchSize(100);
        while (rs.next()) {
            int id= rs.getInt(1);
            String value= rs.getString(2);
            provider.addItem(id,value);
            n++;
        }
        return n;
    }



}
