package com.saturn.commons.catalog;

import com.saturn.commons.catalog.dao.CatalogDao;
import com.saturn.commons.catalog.dao.CatalogQuery;
import com.saturn.commons.catalog.id.CachedId;
import com.saturn.commons.catalog.id.CachedIdCountFactory;
import com.saturn.commons.catalog.id.CachedIdFactory;
import com.saturn.commons.catalog.id.CachedIdPlainFactory;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



/**
 * Base class
 * @author raoolio
 */
abstract class CatalogProviderBase implements CatalogProvider {

    /** Logger */
    protected Logger LOG;

    /** CatalogProvider configuration */
    protected CatalogConfig conf;

    /** DataSource */
    protected CatalogDao dao;

    /** Parameter Cache */
    private LoadingCache<String, CachedId> cache;

    /** CachedId factory */
    private CachedIdFactory idFactory;

    /** Expired Item Handler */
    private ItemRemovedHandler removedHandler;

    /** Expired Consumer */
    private Thread removedHandlerThread;



    /**
     * Constructor
     * @param conf
     * @param dataSource
     */
    public CatalogProviderBase(CatalogConfig conf, CatalogDao dao) {
        this.conf = conf;
        this.dao = dao;
        this.idFactory= conf.isStatsEnabled()? new CachedIdCountFactory() : new CachedIdPlainFactory();
        this.LOG= LogManager.getLogger( conf.getCatalogId()!=null? conf.getCatalogId() : this.getClass().getName() );

        // Fill cache ?
        if (conf.isStatsEnabled()) {
            initRemovedHandler();
        }
        buildCache(conf);
    }



    //<editor-fold defaultstate="collapsed" desc=" Init Methods ">
    /**
     * Initializes the Expired Item Handler
     */
    private void initRemovedHandler() {
        removedHandler= new ItemRemovedHandler(conf,dao);
        removedHandlerThread= new Thread(removedHandler);
        removedHandlerThread.start();
    }



    /**
     * Creates the parameters cache
     */
    private void buildCache(CatalogConfig config) {
        CacheBuilder cb= CacheBuilder.newBuilder()
                .maximumSize(config.getMaxSize())
                .expireAfterWrite(config.getDuration(), config.getDurationUnit());

        // Add listener for expired entries?
        if (removedHandler!=null) {
            cb= cb.removalListener(removedHandler);
        }

        cache = cb.build(new CacheLoader<String, CachedId>() {
            @Override
            public CachedId load(String value) throws Exception {
                return fetchId(value);
            }
        });

        CatalogQuery initCatalog= conf.isStatsEnabled()? CatalogQuery.MOST_USED_ITEMS : CatalogQuery.ALL_ITEMS;
        dao.loadItems(initCatalog, new CatalogItemHandler(this));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Release Method ">
    /**
     * Free resources
     */
    @Override
    public void release() {
        if (cache != null) {
            cache.cleanUp();
            cache = null;
        }
        if (removedHandlerThread!=null) {
            removedHandlerThread.interrupt();
            removedHandlerThread=null;
        }
        if (removedHandler!=null) {
            removedHandler.release();
            removedHandler=null;
        }
        if (dao!=null) {
            dao.release();
            dao=null;
        }
        idFactory=null;
    }
    //</editor-fold>



    /**
     * Retrieves the parameter value from the DataSource
     *
     * @param value GatewayTelcoId
     * @return Parameter value or NULL if it doesn't exist.
     * @throws SQLException
     */
    private CachedId fetchId(String value) throws Exception {
        Integer id = fetchValueId(value);
        return id==null? null : idFactory.makeCachedId(id);
    }



    /**
     * Fetch the given's value ID
     * @param value Value to find
     * @return
     * @throws SQLException
     */
    protected abstract Integer fetchValueId(String value) throws Exception;



    /**
     * Returns the value associated with the given id.
     *
     * @param id Parameter ID
     * @return Associated value or defaultValue if it doesn't exist.
     */
    @Override
    public Integer getId(String value) {
        Integer id = 0;
        try {
            KeyFilter filter=conf.getKeyFilter();
            if (filter!=null)
                value= filter.filter(value);

            CachedId cid = cache.get(value);
            if (cid!=null) {
                cid.incrementCount();
                id= cid.getId();
            }

        } catch (Exception e) {
            LOG.warn(e.getMessage());
        }

        return id;
    }


    @Override
    public long getSize() {
        return cache.size();
    }



    @Override
    public void addItem(Integer id, String value) {
        cache.put(value, idFactory.makeCachedId(id));
    }



}
