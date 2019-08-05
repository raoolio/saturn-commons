package com.saturn.commons.params;

import java.util.concurrent.TimeUnit;


/**
 * Parameter provider wrapper
 * @author raoolio
 */
public class ParamUtil {

    /** Default String */
    private static final String DEFAULT="DFLTVAL";

    /** Parameter provider */
    private ParamProvider paramProvider;


    /**
     * Constructor
     */
    public ParamUtil() {
    }



    /**
     * Constructor
     * @param parProv Parameter provider
     */
    public ParamUtil(ParamProvider parProv) {
        this.paramProvider = parProv;
    }



    /**
     * Set parameter provider
     * @param paramProvider
     */
    public final void setParamProvider(ParamProvider paramProvider) {
        this.paramProvider = paramProvider;
    }



    /**
     * Returns the parameter value as String
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public String getString(String id) {
        return getString(id,null);
    }



    /**
     * Returns the parameter value as String
     * @param id Parameter ID
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public String getString(String id,String defValue) {
        String v= defValue;
        int c=0;
        do {
            // Fetch value...
            v= paramProvider.getValue(id, DEFAULT);

            // Value found? -> quit!
            if (!DEFAULT.equals(v)) {
                break;
            } else {
                // id contains dot "." ?
                int p= id.lastIndexOf(".");
                if (p>0) {
                    // get parent ID
                    id= id.substring(0, p);
                } else {
                    break;
                }
            }

        } while (++c<2);

        return v.equals(DEFAULT)? defValue: v;
    }



    /**
     * Returns the parameter value as Integer
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Integer getInt(String id) {
        return getInt(id,null);
    }



    /**
     * Returns the parameter value as Integer
     * @param id Parameter ID
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Integer getInt(String id,String defValue) {
        String val= getString(id,defValue);
        return Integer.valueOf(val);
    }



    /**
     * Returns the parameter value as Long
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
     public Long getLong(String id) {
        return getLong(id,null);
    }



    /**
     * Returns the parameter value as Long
     * @param id Parameter ID
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Long getLong(String id,String defValue) {
        String val= getString(id,defValue);
        return Long.valueOf(val);
    }



    /**
     * Returns the parameter value as Float
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Float getFloat(String id) {
        return getFloat(id,null);
    }



    /**
     * Returns the parameter value as Float
     * @param id Parameter ID
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Float getFloat(String id,String defValue) {
        String val= getString(id,defValue);
        return Float.valueOf(val);
    }



    /**
     * Returns the parameter value as Double
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Double getDouble(String id) {
        return getDouble(id,null);
    }



    /**
     * Returns the parameter value as Double
     * @param id Parameter ID
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Double getDouble(String id,String defValue) {
        String val= getString(id,defValue);
        return Double.valueOf(val);
    }



    /**
     * Returns the parameter value as Boolean
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public Boolean getBoolean(String id) {
        return getBoolean(id,null);
    }



    /**
     * Returns the parameter value as Boolean
     * @param id Parameter ID
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public Boolean getBoolean(String id,String defValue) {
        String val= getString(id,defValue);
        return Boolean.valueOf(val);
    }



    /**
     * Returns the parameter value as TimeUnit
     * @param id Parameter ID
     * @return Parameter value or <b>NULL</b> if it doesn't exist
     */
    public TimeUnit getTimeUnit(String id) {
        return getTimeUnit(id,null);
    }



    /**
     * Returns the parameter value as TimeUnit
     * @param id Parameter ID
     * @param defValue Default value
     * @return Parameter value or <b>defValue</b> if it doesn't exist
     */
    public TimeUnit getTimeUnit(String id,String defValue) {
        String val= getString(id,defValue);
        return TimeUnit.valueOf(val);
    }



    /**
     * Persists the given key-value pair
     * @param id Parameter ID
     * @param value Parameter value
     * @return Returns <b>true</b> if the operation succeeded.
     */
    public boolean setValue(String id,String value) {
        return paramProvider.setValue(id, value);
    }



}
