package com.saturn.commons.params.test;

import biz.televida.legacy.testing.DataSourceTest;
import com.saturn.commons.param.ParamProvider;
import com.saturn.commons.param.ParamProviderConfig;
import com.saturn.commons.param.ParamProviderFactory;
import com.saturn.commons.param.ParamUtil;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Cache Tester
 */
public class ParamProviderTest extends DataSourceTest {

    /** DataSource instance */
    private static DataSource dataSource;



    @BeforeClass
    public static void initTest() throws Exception {
        dataSource= initDataSource("Zunil.properties");
    }



    @Test
    public void NormalTest() {

        ParamProviderConfig conf= new ParamProviderConfig()
                .setTableName("cdc_configuracion")
                .setIdColumnName("llave")
                .setValueColumnName("valor")
                .setMaxSize(100)
                .setDuration(1, TimeUnit.MINUTES);

        ParamProvider parProv= ParamProviderFactory.getParamProvider(conf, dataSource);
        ParamUtil parUtil= new ParamUtil(parProv);

        String testId= "TEST_"+RandomStringUtils.randomAlphanumeric(5);

        // Test null value
        String val= parUtil.getString(testId);
        Assert.assertNull("Null value failed", val);

        // Test default value
        val= parUtil.getString(testId,"45");
        Assert.assertEquals("Default value failed", "45", val);

        // Set value test
        String testVal= RandomStringUtils.randomAscii(10);
        parUtil.setValue(testId, testVal);
        String tval= parUtil.getString(testId);
        Assert.assertEquals("Normal value retrieval failed", testVal, tval);
    }



    @Test
    public void PrefixTest() {

        ParamProviderConfig conf= new ParamProviderConfig()
                .setTableName("subscription_config")
                .setIdColumnName("id")
                .setValueColumnName("value")
                .setIdPrefix("CPA")
                .setMaxSize(100)
                .setDuration(1, TimeUnit.MINUTES);

        ParamProvider parProv= ParamProviderFactory.getParamProvider(conf, dataSource);
        ParamUtil parUtil= new ParamUtil(parProv);

        String testId= "CPA_CONVERSION_ENABLED";
        String val= parUtil.getString(testId);
        Assert.assertEquals("Prefix value retrieval failed", "true", val);
    }



    @Test
    public void PathTest() {

        ParamProviderConfig conf= new ParamProviderConfig()
                .setTableName("property")
                .setPathValue("/test-path/")
                .setIdColumnName("name")
                .setValueColumnName("value")
                .setMaxSize(100)
                .setDuration(1, TimeUnit.MINUTES);

        ParamProvider parProv= ParamProviderFactory.getParamProvider(conf, dataSource);
        ParamUtil parUtil= new ParamUtil(parProv);

        String testId= "TEST_"+RandomStringUtils.randomAlphanumeric(5);
        String testVal= RandomStringUtils.randomAscii(10);

        String val= parUtil.getString(testId);
        Assert.assertNull("Path Null value failed", val);

        parUtil.setValue(testId, testVal);
        String tval= parUtil.getString(testId);
        Assert.assertEquals("Path value retrieval failed", testVal, tval);
    }



    @Test
    public void ParentIdTest() {

        ParamProviderConfig conf= new ParamProviderConfig()
                .setTableName("property")
                .setPathValue("/test-path/")
                .setIdColumnName("name")
                .setValueColumnName("value")
                .setMaxSize(100)
                .setDuration(1, TimeUnit.MINUTES);

        ParamProvider parProv= ParamProviderFactory.getParamProvider(conf, dataSource);
        ParamUtil parUtil= new ParamUtil(parProv);

        String parentVal= RandomStringUtils.randomAscii(10);
        String childId= "TEST."+RandomStringUtils.randomAlphabetic(5)+"."+RandomStringUtils.randomAlphabetic(5);
        String parentId= childId.substring(0, childId.lastIndexOf("."));

        // Get childId and validate parent value!
        parUtil.setValue(parentId, parentVal);
        String pval= parUtil.getString(childId);
        Assert.assertEquals("Parent value retrieval failed", parentVal, pval);

        // Set value child and get value child!
        String childVal= RandomStringUtils.randomAscii(10);
        parUtil.setValue(childId, childVal);
        String cval= parUtil.getString(childId);
        Assert.assertEquals("Child value retrieval failed", childVal, cval);
    }


}
