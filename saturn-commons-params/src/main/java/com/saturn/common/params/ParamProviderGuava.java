package com.saturn.common.params;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.LoadingCache;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;



/**
 * Parameter cache implementation for any table.
 * @author rdelcid
 */
class ParamProviderGuava extends ParamProviderWriter {

    /** Parameter Cache */
    private LoadingCache<String,String> cache;

    /** SQL for retrieving one parameter values */
    private String sqlSelect;



    /**
     * Constructor
     * @param config Parameter configuration object
     * @param dataSource Database connection
     */
    public ParamProviderGuava(ParamProviderConfig config,DataSource dataSource) {
        super(config,dataSource);
        this.sqlSelect= getFetchSql(config);
        buildCache(config);
    }



    /**
     * Builds the SQL for retrieving values
     * @param conf
     * @return
     */
    private String getFetchSql(ParamProviderConfig conf) {
        StringBuilder sql= new StringBuilder()
            .append("SELECT ").append(conf.getValueColumnName())
            .append(" FROM ").append(conf.getTableName())
            .append(" WHERE ");

        // Path value present?
        if (StringUtils.isNotBlank(conf.getPathValue()))
            sql.append("path='").append(conf.getPathValue()).append("' AND ");

        sql.append(conf.getIdColumnName()).append("=?");

        return sql.toString();
    }




    /**
     * Creates the parameters cache
     */
    protected final void buildCache(ParamProviderConfig config) {
        cache= CacheBuilder.newBuilder().
                maximumSize(config.getMaxSize()).
                expireAfterWrite(config.getDuration(),config.getDurationUnit()).
                build(new CacheLoader<String,String>(){
                    //<editor-fold defaultstate="collapsed" desc=" Fetch param ">
    @Override
            public String load(String id) throws Exception {
                return fetchValue(id);
            }
                    //</editor-fold>
                });
    }



    /**
     * Retrieves the parameter value from the DataSource
     * @param id GatewayTelcoId
     * @return Parameter value or NULL if it doesn't exist.
     * @throws SQLException
     */
    private String fetchValue(String id) throws SQLException {
        QueryRunner qr=new QueryRunner(dataSource);
        String value= qr.query(sqlSelect, new ScalarHandler<String>(), id);
        return value;
    }



    @Override
    protected String getCacheValue(String id) throws Exception {
        String v=null;
        try {
            v= cache.get(id);
        } catch (InvalidCacheLoadException e) {
        }
        return v;
    }


    @Override
    protected void setCacheValue(String id, String value) {
        cache.put(id, value);
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
