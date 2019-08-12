package com.saturn.commons.catalog;

import com.saturn.commons.catalog.dao.CatalogDao;
import com.saturn.commons.catalog.dao.CatalogDaoImpl;
import javax.sql.DataSource;


/**
 *
 * @author raoolio
 */
public class CatalogProviderFactory {

    /** Private constructor */
    private CatalogProviderFactory() {
    }



    /**
     * Returns a CatalogProvider
     * @param cnf
     * @param ds
     * @return
     */
    public static final CatalogProvider getCatalog(CatalogConfig cnf,DataSource ds) {
        CatalogDao dao= new CatalogDaoImpl(cnf,ds);
        
        if (cnf.isCreateIfNotFound())
            return new CatalogProviderReadWrite(cnf,dao);
        else
            return new CatalogProviderReadOnly(cnf,dao);
    }


}
