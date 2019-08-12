package com.saturn.commons.catalog;

import com.saturn.commons.catalog.dao.CatalogDao;



/**
 * Read Only Parameter cache implementation for any table.
 *
 * @author rdelcid
 */
class CatalogProviderReadOnly extends CatalogProviderBase {


    /**
     * Constructor
     *
     * @param config Parameter configuration object
     * @param dao CatalogDao instance
     */
    public CatalogProviderReadOnly(CatalogConfig config, CatalogDao dao) {
        super(config,dao);
    }



    @Override
    protected Integer fetchValueId(String value) throws Exception {
        return dao.getValueId(value);
    }

}
