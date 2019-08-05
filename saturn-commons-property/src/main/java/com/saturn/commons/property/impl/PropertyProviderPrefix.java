package com.saturn.commons.property.impl;

import com.saturn.commons.database.MapStringHandler;
import com.saturn.commons.property.PropertyProviderConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;



/**
 * ParamProvider with key pattern that pre-loads all keys at construction time
 * @author raoolio
 */
public class PropertyProviderPrefix extends PropertyProviderWriter {


    /** SQL for retrieving all matching parameter values */
    private String sqlGetAll;

    /** Parameter cache */
    private Map<String,String> cache;

    /** Next cache reload time */
    private long nextReload;



    /**
     * Constructor
     * @param config Cache configuration
     * @param dataSource DataSource instance
     */
    public PropertyProviderPrefix(PropertyProviderConfig config, DataSource dataSource) {
        super(config,dataSource);
        this.sqlGetAll = getSqlGetAll(config);
        loadCache();
    }



    /**
     * Builds the SQL for retrieving values
     * @param conf
     * @return
     */
    private String getSqlGetAll(PropertyProviderConfig conf) {
        if (conf.getIdPrefix()==null)
            return null;
        else {
            StringBuilder sql= new StringBuilder()
                .append("SELECT ").append(conf.getIdColumnName()).append(",").append(conf.getValueColumnName())
                .append(" FROM ").append(conf.getTableName())
                .append(" WHERE ");

            // Path value present?
            if (StringUtils.isNotBlank(conf.getPathValue()))
                sql.append("path='").append(conf.getPathValue()).append("' AND ");

            sql.append(conf.getIdColumnName()).append(" LIKE '").append(conf.getIdPrefix()).append("%'");

            return sql.toString();
        }
    }



    /**
     * Loads all parameters that match the prefix
     * @param conf
     */
    private void loadCache() {
        Map<String,String> tmpPars= new ConcurrentHashMap<String,String>(config.getMaxSize());
        try {
            QueryRunner qr= new QueryRunner(dataSource);
            qr.query(sqlGetAll, new MapStringHandler(tmpPars));
            setCache(tmpPars);
            LOG.debug("["+tmpPars.size()+"] parameters loaded!");
        } catch (Exception ex) {
            LOG.error("Error fetching all parameters", ex);
        }
    }



    /**
     * Replaces the current cache
     * @param freshMap New reloaded cache
     */
    private void setCache(Map freshMap) {
        Map oldMap= cache;
        cache= freshMap;
        nextReload= System.currentTimeMillis()+config.getDurationUnit().toMillis(config.getDuration());

        if (oldMap!=null) {
            oldMap.clear();
            oldMap=null;
        }
    }



    @Override
    protected String getCacheValue(String id) throws Exception {

        // Reload the cache?
        if (System.currentTimeMillis() > nextReload) {
            loadCache();
        }

        return cache.get(id);
    }



    @Override
    protected void setCacheValue(String id, String value) {
        cache.put(id, value);
    }



    @Override
    public void release() {
        super.release();
        this.sqlGetAll=null;
    }


}
