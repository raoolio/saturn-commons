package com.saturn.commons.property.impl;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.LoadingCache;
import com.saturn.commons.property.PropertyConfig;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;



/**
 * Parameter cache implementation for any table.
 * @author rdelcid
 */
public class PropertyProviderGuava extends PropertyProviderWriter {

    /** Parameter Cache */
    private LoadingCache<Key,String> cache;

    /** SQL for retrieving one parameter values */
    private String sqlSelect;



    /**
     * Constructor
     * @param config Property configuration instance
     * @param dataSource Database connection
     */
    public PropertyProviderGuava(PropertyConfig config,DataSource dataSource) {
        super(config,dataSource);
        this.sqlSelect= getFetchSql(config);
        buildCache(config);
    }



    /**
     * Builds the SQL for retrieving values
     * @param config Property configuration instance
     * @return SQL string.
     */
    private String getFetchSql(PropertyConfig conf) {
        StringBuilder sql= new StringBuilder()
            .append("SELECT ").append(conf.getValueColumnName())
            .append(" FROM ").append(conf.getTableName())
            .append(" WHERE ");

        // Path value present?
        if (StringUtils.isNotBlank(conf.getBasePath()))
            sql.append("path='").append(conf.getBasePath()).append("' AND ");

        sql.append(conf.getIdColumnName()).append("=?");

        return sql.toString();
    }




    /**
     * Creates the parameters cache
     * @param config Property configuration instance
     */
    protected final void buildCache(PropertyConfig config) {
        cache= CacheBuilder.newBuilder().
                maximumSize(config.getMaxSize()).
                expireAfterWrite(config.getDuration(),config.getDurationUnit()).
                build(new CacheLoader<Key,String>(){
                    //<editor-fold defaultstate="collapsed" desc=" Fetch param ">
                    @Override
                    public String load(Key k) throws Exception {
                        return fetchValue(k);
                    }
                    //</editor-fold>
                });
    }



    /**
     * Retrieves the parameter value from the DataSource
     * @param k Property key
     * @return Parameter value or NULL if it doesn't exist.
     * @throws SQLException
     */
    private String fetchValue(Key k) throws SQLException {
        QueryRunner qr=new QueryRunner(dataSource);
        String v= qr.query(sqlSelect, new ScalarHandler<>(),
                k.getPath(), k.getId());
        return v;
    }



    @Override
    protected String getCacheValue(Key k) throws Exception {
        String v=null;
        try {
            v= cache.get(k);
        } catch (InvalidCacheLoadException e) {
        }
        return v;
    }


    @Override
    protected void setCacheValue(Key k, String v) {
        cache.put(k, v);
    }


    @Override
    public void release() {
        super.release();
        if (cache!=null) {
            cache.cleanUp();
            cache=null;
        }
        this.sqlSelect=null;
    }

}
