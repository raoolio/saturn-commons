package com.saturn.commons.catalog;

import java.util.concurrent.TimeUnit;


/**
 * ParamProvider configuration parameters for a Guava Loading Cache object.
 * Each entry is composed of a key/value pair
 *
 * @author raoolio
 */
public class CatalogConfig {

    /** Catalog ID */
    private String catalogId;

    /** Table name for retrieving parameters */
    private String tableName;

    /** Parameter ID column name */
    private String idColumnName;

    /** Parameter value column name */
    private String valueColumnName;

    /** HIT count column name */
    private String countColumnName;

    /** Change Date column name */
    private String changeDateColumnName;

    /** Cache duration time before reloading */
    private long duration;

    /** Cache duration time unit */
    private TimeUnit durationUnit;

    /** Cache initial size */
    private int iniSize=100;

    /** Cache maximum size */
    private int maxSize;

    /** Store the value if it doesn't exist */
    private boolean createIfNotFound;

    /** Stats enabled? */
    private boolean statsEnabled;

    /** Catalog keyFilter */
    private KeyFilter keyFilter;

    /** Item count update period (Milliseconds) */
    private long countUpdatePeriod;

    /** Item count history days used to initialize the cache */
    private int iniHistoryDays;



    /**
     * ParamProvider constructor
     * @return
     */
    public CatalogConfig ParamProviderConfig() {
        return this;
    }


    /**
     * Set the database table name source for retrieving values.
     * @param tableName Table name
     * @return
     */
    public CatalogConfig setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }


    /**
     * Set the table column name used for key matching
     * @param idColumnName Key column name
     * @return
     */
    public CatalogConfig setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
        return this;
    }


    /**
     * Set the table column name used for value retrieval
     * @param valueColumnName Value column name
     * @return
     */
    public CatalogConfig setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
        return this;
    }


    /**
     * Set the table column name used for hit count
     * @param countColumnName Count Column name
     * @return
     */
    public CatalogConfig setCountColumnName(String countColumnName) {
        this.countColumnName = countColumnName;
        this.statsEnabled= true;
        return this;
    }


    /**
     * Sets the Change date column name
     * @param changeDateColumnName
     * @return
     */
    public CatalogConfig setChangeDateColumnName(String changeDateColumnName) {
        this.changeDateColumnName = changeDateColumnName;
        return this;
    }


    /**
     * Set the key/value pair retention time in the cache
     * @param duration Duration time value
     * @param unit Duration time unit
     * @see setDurationUnit
     * @return
     */
    public CatalogConfig setDuration(long duration,TimeUnit unit) {
        this.duration = duration;
        this.durationUnit= unit;
        return this;
    }



    /**
     * Set cache initial size
     * @param iniSize Initial size value
     * @return
     */
    public CatalogConfig setIniSize(int iniSize) {
        this.iniSize = iniSize;
        return this;
    }



    /**
     * Sets the cache max size
     * @param maxSize Cache max size
     * @return
     */
    public CatalogConfig setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }


    /**
     * Enable the creation of value pairs if the key doesn't exist
     * @param create Creation enabled?
     * @return
     */
    public CatalogConfig setCreateIfNotFound(boolean create) {
        this.createIfNotFound = create;
        return this;
    }



    /**
     * Sets the KeyFilter
     * @param listener
     * @return
     */
    public CatalogConfig setKeyFilter(KeyFilter listener) {
        this.keyFilter = listener;
        return this;
    }



    /**
     * Sets the catalog ID
     * @param catalogId Logger id name
     * @return
     */
    public CatalogConfig setCatalogId(String catalogId) {
        this.catalogId = catalogId;
        return this;
    }



    /**
     * Set item count update period (minutes)
     * @param minutes
     * @return
     */
    public CatalogConfig setCounterUpdatePeriod(long minutes) {
        this.countUpdatePeriod= minutes*60*1000;
        return this;
    }


    public CatalogConfig setIniHistoryDays(int days) {
        this.iniHistoryDays= days;
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

    public String getCountColumnName() {
        return countColumnName;
    }

    public String getChangeDateColumnName() {
        return changeDateColumnName;
    }

    public long getDuration() {
        return duration;
    }

    public TimeUnit getDurationUnit() {
        return durationUnit;
    }

    public int getIniSize() {
        return iniSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean isCreateIfNotFound() {
        return createIfNotFound;
    }

    public boolean isStatsEnabled() {
        return statsEnabled && countColumnName!=null && changeDateColumnName!=null;
    }

    public KeyFilter getKeyFilter() {
        return keyFilter;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public long getCountUpdatePeriod() {
        return countUpdatePeriod;
    }

    public int getIniHistoryDays() {
        return iniHistoryDays;
    }


    @Override
    public String toString() {
        return "ParamProviderConfig{" + "tableName=" + tableName + ", nameField=" + idColumnName + ", valueField=" + valueColumnName + '}';
    }

}
