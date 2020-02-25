package com.saturn.commons.property;

import java.util.concurrent.TimeUnit;


/**
 * ParamProvider configuration parameters for a Guava Loading Cache object.
 * Each entry is composed of a key/value pair
 *
 * @author raoolio
 */
public class PropertyConfig {

    /** Table name for retrieving parameters */
    private String tableName= "property";

    /** Path column name */
    private String pathColumnName= "path";

    /** Parameter ID column name */
    private String idColumnName="name";

    /** Parameter value column name */
    private String valueColumnName="value";

    /** Path column name (used for table property) */
    private String basePath;

    /** Cache duration time before reloading */
    private long duration;

    /** Cache duration time unit */
    private TimeUnit durationUnit;

    /** Max cache size */
    private int maxSize;

    /** Recursive path search retries  */
    private int searchRetries=3;



    /**
     * ParamProvider constructor
     */
    public PropertyConfig() {
    }


    /**
     * Set the database table name source for retrieving values.
     * @param tableName Table name
     * @return PropertyConfig instance
     */
    public PropertyConfig setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }


    /**
     * Set the table's path column name
     * @param pathColumnName Path column name
     */
    public void setPathColumnName(String pathColumnName) {
        this.pathColumnName = pathColumnName;
    }


    /**
     * Set the table column name used for key matching
     * @param idColumnName Key column name
     * @return PropertyConfig instance
     */
    public PropertyConfig setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
        return this;
    }


    /**
     * Set the table column name used for value retrieval
     * @param valueColumnName Value column name
     * @return PropertyConfig instance
     */
    public PropertyConfig setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
        return this;
    }


    /**
     * Sets the base path for retrieving property values
     * @param path Base property path
     * @return PropertyConfig instance
     */
    public PropertyConfig setBasePath(String path) {
        this.basePath = path;
        return this;
    }


    /**
     * Set the key/value pair retention time in the cache
     * @param duration Duration time value
     * @param unit Duration time unit
     * @see setDuration
     * @return PropertyConfig instance
     */
    public PropertyConfig setDuration(long duration,TimeUnit unit) {
        this.duration = duration;
        this.durationUnit= unit;
        return this;
    }



    /**
     * Sets the cache max size
     * @param maxSize Maximum cache size
     * @return PropertyConfig instance
     */
    public PropertyConfig setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }



    /**
     * Sets the number of recursive search retries for a given entry
     * @param searchRetries Number of retries
     * @return PropertyConfig instance
     */
    public PropertyConfig setSearchRetries(int searchRetries) {
        this.searchRetries = searchRetries;
        return this;
    }



    public String getTableName() {
        return tableName;
    }

    public String getPathColumnName() {
        return pathColumnName;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public String getBasePath() {
        return basePath;
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

    public int getSearchRetries() {
        return searchRetries;
    }


    @Override
    public String toString() {
        return "PropertyConfig{" + "tableName=" + tableName + ", idColumnName=" + idColumnName +", valueColumnName=" + valueColumnName + '}';
    }

}
