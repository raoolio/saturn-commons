package com.saturn.commons.param;

import java.util.concurrent.TimeUnit;


/**
 * Param configuration parameters for a Guava Loading Cache object.
 * Each entry is composed of a key/value pair
 *
 * @author raoolio
 */
public class ParamConfig {

    /** Table name for retrieving parameters */
    private String tableName;

    /** Parameter ID column name */
    private String idColumnName;

    /** Parameter value column name */
    private String valueColumnName;

    /** Path column name (used for table property) */
    private String pathValue;

    /** ID Pattern for pre loading all keys */
    private String idPrefix;

    /** Cache duration time before reloading */
    private long duration;

    /** Cache duration time unit */
    private TimeUnit durationUnit;

    /** Max cache size */
    private int maxSize;



    /**
     * ParamProvider constructor
     * @return
     */
    public ParamConfig ParamConfig() {
        return this;
    }


    /**
     * Set the database table name source for retrieving values.
     * @param tableName Table name
     * @return
     */
    public ParamConfig setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }


    /**
     * Set the table column name used for key matching
     * @param idColumnName Key column name
     * @return
     */
    public ParamConfig setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
        return this;
    }


    /**
     * Set the table column name used for value retrieval
     * @param valueColumnName Value column name
     * @return
     */
    public ParamConfig setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
        return this;
    }


    /**
     * Sets the PATH value for retrieving values
     * @param pathColumnName
     * @return
     */
    public ParamConfig setPathValue(String pathColumnName) {
        this.pathValue = pathColumnName;
        return this;
    }


    /**
     * Set the key/value pair retention time in the cache
     * @param duration Duration time value
     * @param unit
     * @see setDurationUnit
     * @return
     */
    public ParamConfig setDuration(long duration,TimeUnit unit) {
        this.duration = duration;
        this.durationUnit= unit;
        return this;
    }



    /**
     * Sets the cache max size
     * @param maxSize
     * @return
     */
    public ParamConfig setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }



    /**
     * Sets the key parameters
     * @param idPattern
     * @return
     */
    public ParamConfig setIdPrefix(String idPattern) {
        this.idPrefix = idPattern;
        return this;
    }



    public String getTableName() {
        return tableName;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public String getPathValue() {
        return pathValue;
    }

    public long getDuration() {
        return duration;
    }

    public TimeUnit getDurationUnit() {
        return durationUnit;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getIdPrefix() {
        return idPrefix;
    }


    @Override
    public String toString() {
        return "ParamConfig{" + "tableName=" + tableName + ", idColumnName=" + idColumnName + "["+idPrefix+"], valueColumnName=" + valueColumnName + '}';
    }

}
