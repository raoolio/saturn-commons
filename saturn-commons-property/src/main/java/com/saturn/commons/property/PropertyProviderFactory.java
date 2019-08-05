package com.saturn.commons.property;

import com.saturn.commons.property.impl.PropertyProviderPrefix;
import com.saturn.commons.property.impl.PropertyProviderGuava;
import javax.sql.DataSource;
import org.apache.commons.lang3.Validate;


/**
 * Param provider factory
 * @author raoolio
 */
public class PropertyProviderFactory {

    // Private constructor
    private PropertyProviderFactory() {
    }



    /**
     * Returns a PropertyProvider instance
     * @param config Parameter configuration
     * @param dataSource Data source instance
     * @return
     */
    public static PropertyProvider getParamProvider(PropertyProviderConfig config,DataSource dataSource) {
        Validate.notNull(config, "Configuration can't be null");
        return config.getIdPrefix()==null ? new PropertyProviderGuava(config,dataSource) :
            new PropertyProviderPrefix(config,dataSource);
    }


}
