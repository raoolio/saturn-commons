package com.saturn.commons.property.impl;


import com.saturn.commons.database.ExistsHandler;
import com.saturn.commons.property.PropertyConfig;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.saturn.commons.property.PropertyProvider;
import com.saturn.commons.utils.StringUtils;



/**
 * Parameter cache implementation for any table.
 * @author rdelcid
 */
abstract class PropertyProviderWriter implements PropertyProvider {

    /** Logger */
    protected static Logger LOG= LogManager.getLogger(PropertyProviderWriter.class);

    /** Configuration */
    protected PropertyConfig config;

    /** DataSource */
    protected DataSource dataSource;

    /** SQL for storing the parameter values */
    private String sqlExists;
    private String sqlInsert;
    private String sqlUpdate;



    /**
     * Constructor
     * @param config Parameter configuration object
     * @param dataSource Database connection
     */
    public PropertyProviderWriter(PropertyConfig config,DataSource dataSource) {
        this.config= config;
        this.dataSource = dataSource;
        Validate.notBlank(config.getTableName(), "Table name can't be empty");
        Validate.notBlank(config.getIdColumnName(), "Id column name can't be empty");
        Validate.notBlank(config.getValueColumnName(), "Value column name can't be empty");
        Validate.isTrue(config.getMaxSize()>0, "Cache size must be greater than 0");
        Validate.isTrue(config.getDuration()>0, "Cache duration value must be greater than 0");
        Validate.notNull(config.getDurationUnit(),"Cache duration unit can't be null");
        buildSqls(config);
    }



    /**
     * Builds the SQL for storing values
     * @param conf
     * @return
     */
    private final void buildSqls(PropertyConfig conf) {

        boolean hasPath= org.apache.commons.lang3.StringUtils.isNotBlank(conf.getBasePath());
        StringBuilder vals= new StringBuilder();

        //<editor-fold defaultstate="collapsed" desc=" Build Update ">

        StringBuilder sql= new StringBuilder()
                .append("UPDATE ").append(conf.getTableName())
                .append(" SET ").append(conf.getValueColumnName()).append("=?")
                .append(" WHERE ");

        sql.append(conf.getPathColumnName()).append("=? AND ");
        sql.append(conf.getIdColumnName()).append("=?");
        sqlUpdate= sql.toString();

        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc=" Build Insert ">

        // NOTE: fields in reverse order for compatibility with UPDATE statement
        sql.delete(0, sql.length())
                .append("INSERT INTO ").append(conf.getTableName()).append("(");

        sql.append(conf.getValueColumnName())
           .append(",").append(conf.getPathColumnName())
           .append(",").append(conf.getIdColumnName());
        vals.append("?,?,?");

        sql.append(") VALUES(").append(vals).append(")");
        sqlInsert= sql.toString();

        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc=" Build Exists ">

        sql.delete(0, sql.length())
                .append("SELECT ").append(conf.getIdColumnName())
                .append(" FROM ").append(conf.getTableName())
                .append(" WHERE ").append(conf.getPathColumnName()).append("=?")
                .append(" AND ").append(conf.getIdColumnName()).append("=?");
        sqlExists= sql.toString();

        //</editor-fold>

    }



    /**
     * Retrieve a value from the cache
     * @param key Parameter key
     * @return
     */
    protected abstract String getCacheValue(Key key) throws Exception;


    /**
     * Updates a value in the cache
     * @param path Property path
     * @param id Parameter ID
     * @param value New value
     * @return
     */
    protected abstract void setCacheValue(Key key,String value);



    /**
     * Free resources
     */
    @Override
    public void release() {
        dataSource=null;
        sqlUpdate=null;
    }



    /**
     * Returns the value associated with the given id.
     * @param id Parameter ID
     * @return Associated value or defaultValue if it doesn't exist.
     */
    @Override
    public String getValue(String path,String id) {
        return getValue(path,id,null);
    }



    /**
     * Returns property path. The path is formed by:
     * basePath + suffix
     * @param suffix Path suffix
     * @return
     */
    private String getPath(String suffix) {

        if (suffix==null)
            return config.getBasePath();
        else {
            StringBuilder p= new StringBuilder(config.getBasePath());
            StringUtils.appendIfMissing(p, '/');
            p.append(suffix);
            StringUtils.appendIfMissing(p, '/');
            return p.toString();
        }
    }



    /**
     * Returns the corresponding key value
     * @param k Key instance
     * @param d Default value
     * @return
     */
    private String getValue(Key k) {
        String v=null;
        try {
            v= getCacheValue(k);
        } catch (Exception e) {
            LOG.warn("ID["+k+"] -> "+e.getCause().toString());
        }

        return v;
    }



    /**
     * Returns the value associated with the given id. Returns the default value
     * if not found
     * @param pathSuffix Path suffix
     * @param id Parameter ID
     * @param defaultValue Default parameter value
     * @return
     */
    @Override
    public String getValue(String pathSuffix,String id, String defaultValue) {
        String v=null;
        String path= getPath(pathSuffix);

        // Get max retries
        int n=config.getSearchRetries();
        int c=n;

        do {
            if (c < n) {
                // Get path parent
                path= com.saturn.commons.utils.StringUtils.getParent(path, '/');

                if (path.length()==0) {
                    break;
                }
            }

            // Fetch value...
            Key k= new Key(path,id);
            v= getValue(k);

        } while (v==null && --c>=0);

        return v==null? defaultValue: v;
    }



    /**
     * Stores the given key-value pair in the cache
     * @param pathSuffix Path suffix
     * @param id Property id
     * @param value Property value
     * @return
     */
    @Override
    public boolean setValue(String pathSuffix,String id, String value) {
        boolean done=false;
        if (id!=null && value!=null) {
            Key key= new Key(getPath(pathSuffix),id);
            setCacheValue(key, value);
            done= updateOrInsert(key,value);
        }
        return done;
    }



    /**
     * Retrieves the parameter value from the DataSource
     * @param id GatewayTelcoId
     * @return Parameter value or NULL if it doesn't exist.
     * @throws SQLException
     */
    private boolean updateOrInsert(Key key,String value) {
        boolean done=false;
        try {
            QueryRunner qr=new QueryRunner(dataSource);

            boolean exists= qr.query(sqlExists, new ExistsHandler(),
                    key.getPath(),
                    key.getId());

            String sql=exists? sqlUpdate : sqlInsert;

            done= qr.update(sql, value, key.getPath(), key.getId()) > 0;

        } catch(Exception ex) {
            LOG.error("Error storing parameter: ["+key+"="+value+"]", ex);
        }
        return done;
    }



}
