package com.saturn.commons.property.impl;

import com.saturn.commons.property.PropertyConfig;
import com.saturn.commons.property.util.PropertyHandler;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;



/**
 * ParamProvider with key pattern that pre-loads all keys at construction time
 * @author raoolio
 */
public class PropertyProviderPrefix extends PropertyProviderWriter {

    /** SQL for retrieving all matching parameter values */
    private String sqlGetAll;

    /** Parameter cache */
    private Map<Key,String> cache;

    /** Next cache reload time */
    private long nextReload;



    /**
     * Constructor
     * @param config Cache configuration
     * @param dataSource DataSource instance
     */
    public PropertyProviderPrefix(PropertyConfig config, DataSource dataSource) {
        super(config,dataSource);
        this.sqlGetAll = getSqlGetAll(config);
        loadCache();
    }



    /**
     * Builds the SQL for retrieving values
     * @param conf
     * @return
     */
    private String getSqlGetAll(PropertyConfig conf) {
        StringBuilder sql= new StringBuilder()
            .append("SELECT ").append(conf.getPathColumnName())
            .append(",").append(conf.getIdColumnName())
            .append(",").append(conf.getValueColumnName())
            .append(" FROM ").append(conf.getTableName())
            .append(" WHERE ");

        // Add base path
        sql.append(conf.getPathColumnName()).append(" LIKE '").append(conf.getBasePath()).append("%'");

        return sql.toString();
    }



    /**
     * Loads all parameters that match the prefix
     * @param conf
     */
    private void loadCache() {
        try {
            QueryRunner qr= new QueryRunner(dataSource);
            Map<Key,String> m= qr.query(sqlGetAll, new PropertyHandler());
            setCache(m);
            LOG.debug("["+m.size()+"] parameters loaded!");
        } catch (Exception ex) {
            LOG.error("Error fetching all parameters", ex);
        }
    }



    /**
     * Replaces the current cache
     * @param map New reloaded cache
     */
    private void setCache(Map map) {
        Map oldMap= cache;
        cache= map;
        nextReload= System.currentTimeMillis()+config.getDurationUnit().toMillis(config.getDuration());

        if (oldMap!=null) {
            oldMap.clear();
            oldMap=null;
        }
    }



    @Override
    protected String getCacheValue(Key key) throws Exception {

        // Reload the cache?
        if (System.currentTimeMillis() > nextReload) {
            loadCache();
        }

        return cache.get(key);
    }



    @Override
    protected void setCacheValue(Key key, String value) {
        cache.put(key, value);
    }



    @Override
    public void release() {
        super.release();
        this.sqlGetAll=null;
    }


}
