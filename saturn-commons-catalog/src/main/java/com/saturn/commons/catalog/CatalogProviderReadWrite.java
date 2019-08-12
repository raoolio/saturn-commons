package com.saturn.commons.catalog;

import com.saturn.commons.catalog.dao.CatalogDao;
import java.sql.SQLException;



/**
 * Read-Write cache implementation for any table.
 */
class CatalogProviderReadWrite extends CatalogProviderBase {


    /**
     * Constructor
     *
     * @param config Parameter configuration object
     * @param dao CatalogDao instance
     */
    public CatalogProviderReadWrite(CatalogConfig config, CatalogDao dao) {
        super(config,dao);
    }



    /**
     * Retrieves the id of the item
     *
     * @param value Catalog item value
     * @return Item ID
     * @throws SQLException
     */
    @Override
    protected Integer fetchValueId(String value) throws Exception {
        return dao.getOrCreateValueId(value);
    }

}
