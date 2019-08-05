package com.saturn.commons.param.impl;


import com.saturn.commons.database.ExistsHandler;
import com.saturn.commons.param.ParamProvider;
import com.saturn.commons.param.ParamProviderConfig;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



/**
 * Parameter cache implementation for any table.
 * @author rdelcid
 */
abstract class ParamProviderWriter implements ParamProvider {

    /** Logger */
    protected Logger LOG= LogManager.getLogger(getClass());

    /** Configuration */
    protected ParamProviderConfig config;

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
    public ParamProviderWriter(ParamProviderConfig config,DataSource dataSource) {
        this.config= config;
        this.dataSource = dataSource;
        Validate.notBlank(config.getTableName(), "Table name can't be empty");
        Validate.notBlank(config.getIdColumnName(), "ID column name can't be empty");
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
    private final void buildSqls(ParamProviderConfig conf) {

        boolean hasPath= StringUtils.isNotBlank(conf.getPathValue());
        StringBuilder vals= new StringBuilder();

        // Prepare UPDATE
        StringBuilder sql= new StringBuilder()
            .append("UPDATE ").append(conf.getTableName())
            .append(" SET ").append(conf.getValueColumnName()).append("=?")
            .append(" WHERE ");

        if (hasPath)
            sql.append("path='").append(conf.getPathValue()).append("' AND ");

        sql.append(conf.getIdColumnName()).append("=?");
        sqlUpdate= sql.toString();

        // Prepare INSERT
        // NOTE: fields in reverse order for compatibility with UPDATE statement
        sql.delete(0, sql.length())
           .append("INSERT INTO ").append(conf.getTableName()).append("(");

        if (hasPath) {
            sql.append("path,");
            vals.append("'").append(conf.getPathValue()).append("',");
        }

        sql.append(conf.getValueColumnName())
           .append(",")
           .append(conf.getIdColumnName());

        vals.append("?,?");

        // Add description field?
        if (conf.getTableName().contains("subscription_config")) {
            sql.append(",description");
            vals.append(",'Code generated value'");
        }

        sql.append(") VALUES(").append(vals).append(")").toString();
        sqlInsert= sql.toString();

        // Prepare EXISTS
        sql.delete(0, sql.length())
            .append("SELECT ").append(conf.getIdColumnName())
            .append(" FROM ").append(conf.getTableName())
            .append(" WHERE ");

        if (hasPath)
            sql.append("path='").append(conf.getPathValue()).append("' AND ");

        sqlExists= sql.append(conf.getIdColumnName()).append("=?")
            .toString()
        ;

    }



    /**
     * Retrieve a value from the cache
     * @param id Parameter id
     * @return
     */
    protected abstract String getCacheValue(String id) throws Exception;


    /**
     * Updates a value in the cache
     * @param id Parameter ID
     * @param value New value
     * @return
     */
    protected abstract void setCacheValue(String id,String value);



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
    public String getValue(String id) {
        return getValue(id,null);
    }



    /**
     * Returns the value associated with the given id. Returns the default value
     * if not found
     * @param id Parameter ID
     * @param defaultValue Default parameter value
     * @return
     */
    @Override
    public String getValue(String id, String defaultValue) {
        String v=null;
        try {
            v= getCacheValue(id);
        } catch (Exception e) {
            LOG.warn("ID["+id+"] -> "+e.getCause().toString());
        }

        return v!=null? v : defaultValue;
    }



    /**
     * Stores the given key-value pair in the cache
     * @param id Parameter ID
     * @param value Parameter value
     * @return
     */
    @Override
    public boolean setValue(String id, String value) {

        boolean done=false;
        if (id!=null && value!=null) {
            setCacheValue(id, value);
            done= updateOrInsert(id,value);
        }
        return done;
    }



    /**
     * Retrieves the parameter value from the DataSource
     * @param id GatewayTelcoId
     * @return Parameter value or NULL if it doesn't exist.
     * @throws SQLException
     */
    private boolean updateOrInsert(String id,String value) {
        boolean done=false;
        try {
            QueryRunner qr=new QueryRunner(dataSource);
            boolean exists= qr.query(sqlExists, new ExistsHandler(), id);

            done= qr.update(exists? sqlUpdate : sqlInsert,
                    value,id) > 0;

        } catch(Exception ex) {
            LOG.error("Error storing parameter: ["+id+"="+value+"]", ex);
        }
        return done;
    }



}
