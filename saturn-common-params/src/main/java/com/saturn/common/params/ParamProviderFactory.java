package com.saturn.common.params;

import javax.sql.DataSource;
import org.apache.commons.lang3.Validate;


/**
 * Param provider factory
 * @author raoolio
 */
public class ParamProviderFactory {

    // Private constructor
    private ParamProviderFactory() {
    }



    /**
     * Returns a ParamProvider instance
     * @param config Parameter configuration
     * @param dataSource Data source instance
     * @return
     */
    public static ParamProvider getParamProvider(ParamProviderConfig config,DataSource dataSource) {
        Validate.notNull(config, "Configuration can't be null");
        return config.getIdPrefix()==null ? new ParamProviderGuava(config,dataSource) :
            new ParamProviderPrefix(config,dataSource);
    }


}
