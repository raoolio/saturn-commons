package com.saturn.commons.param;

import com.saturn.commons.param.impl.ParamProviderPrefix;
import com.saturn.commons.param.impl.ParamProviderGuava;
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
    public static ParamProvider getParamProvider(ParamConfig config,DataSource dataSource) {
        Validate.notNull(config, "Configuration can't be null");
        return config.getIdPrefix()==null ? new ParamProviderGuava(config,dataSource) :
            new ParamProviderPrefix(config,dataSource);
    }


}
