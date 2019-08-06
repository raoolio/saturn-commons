package com.saturn.commons.property;

import java.util.concurrent.TimeUnit;


/**
 * Parameter provider wrapper
 * @author raoolio
 */
public class PropertyUtil {


    /** Parameter provider */
    private PropertyProvider propertyProvider;


    /**
     * Constructor
     */
    public PropertyUtil() {
    }



    /**
     * Constructor
     * @param propProv Property provider
     */
    public PropertyUtil(PropertyProvider propProv) {
        this.propertyProvider = propProv;
    }



    /**
     * Set parameter provider
     * @param propertyProvider
     */
    public final void setPropertyProvider(PropertyProvider propertyProvider) {
        this.propertyProvider = propertyProvider;
    }



    /**
     * Returns the parameter value as String
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public String getString(String id) {
        return getString(null,id,null);
    }



    /**
     * Returns the parameter value as String
     * @param path
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public String getString(String path,String id) {
        return getString(path,id,null);
    }



    /**
     * Returns the parameter value as String
     * @param path Property path
     * @param id Property id
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public String getString(String path,String id,String defValue) {
        return propertyProvider.getValue(path,id,defValue);
    }



    /**
     * Returns the parameter value as Integer
     * @param path Property path
     * @param id Property id
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Integer getInt(String path,String id) {
        return getInt(path,id,null);
    }



    /**
     * Returns the parameter value as Integer
     * @param path Property path
     * @param id Property id
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Integer getInt(String path,String id,String defValue) {
        String val= getString(path,id,defValue);
        return Integer.valueOf(val);
    }



    /**
     * Returns the parameter value as Long
     * @param path Property path
     * @param id Property id
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
     public Long getLong(String path,String id) {
        return getLong(path,id,null);
    }



    /**
     * Returns the parameter value as Long
     * @param path Property path
     * @param id Property id
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Long getLong(String path,String id,String defValue) {
        String val= getString(id,defValue);
        return Long.valueOf(val);
    }



    /**
     * Returns the parameter value as Float
     * @param path Property path
     * @param id Property id
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Float getFloat(String path,String id) {
        return getFloat(path,id,null);
    }



    /**
     * Returns the parameter value as Float
     * @param path Property path
     * @param id Property id
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Float getFloat(String path,String id,String defValue) {
        String val= getString(path,id,defValue);
        return Float.valueOf(val);
    }



    /**
     * Returns the parameter value as Double
     * @param path Property path
     * @param id Property id
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Double getDouble(String path,String id) {
        return getDouble(path,id,null);
    }



    /**
     * Returns the parameter value as Double
     * @param path Property path
     * @param id Property id
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Double getDouble(String path,String id,String defValue) {
        String val= getString(path,id,defValue);
        return Double.valueOf(val);
    }



    /**
     * Returns the parameter value as Boolean
     * @param path Property path
     * @param id Property id
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Boolean getBoolean(String path,String id) {
        return getBoolean(path,id,null);
    }



    /**
     * Returns the parameter value as Boolean
     * @param path Property path
     * @param id Property id
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Boolean getBoolean(String path,String id,String defValue) {
        String val= getString(path,id,defValue);
        return Boolean.valueOf(val);
    }



    /**
     * Returns the parameter value as TimeUnit
     * @param path Property path
     * @param id Property id
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public TimeUnit getTimeUnit(String path,String id) {
        return getTimeUnit(path,id,null);
    }



    /**
     * Returns the parameter value as TimeUnit
     * @param path Property path
     * @param id Property id
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public TimeUnit getTimeUnit(String path,String id,String defValue) {
        String val= getString(path,id,defValue);
        return TimeUnit.valueOf(val);
    }



    /**
     * Persists the given key-value pair
     * @param path Property path
     * @param id Property id
     * @param value Property value
     * @return Returns <b>true</b> if the operation succeeded.
     */
    public boolean setValue(String path,String id,String value) {
        return propertyProvider.setValue(path,id,value);
    }



}
