package com.saturn.commons.property.test;

import biz.televida.legacy.testing.DataSourceTest;
import com.saturn.commons.property.PropertyProvider;
import com.saturn.commons.property.PropertyConfig;
import com.saturn.commons.property.PropertyProviderFactory;
import com.saturn.commons.property.PropertyUtil;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Cache Tester
 */
public class PropertyProviderTest extends DataSourceTest {

    /** DataSource instance */
    private static DataSource dataSource;



    @BeforeClass
    public static void initTest() throws Exception {
        dataSource= initDataSource("Zunil.properties");
    }



    @Test
    public void PathTest() {

        PropertyConfig conf= new PropertyConfig()
                .setTableName("property")
                .setBasePath("/test-path/")
                .setIdColumnName("name")
                .setValueColumnName("value")
                .setMaxSize(100)
                .setDuration(1, TimeUnit.MINUTES);

        PropertyProvider parProv= PropertyProviderFactory.getPropertyProvider(conf, dataSource);
        PropertyUtil parUtil= new PropertyUtil(parProv);

        String testId= "TEST_"+RandomStringUtils.randomAlphanumeric(5);
        String testVal= RandomStringUtils.randomAscii(10);

        String val= parUtil.getString(null,testId);
        Assert.assertNull("Path Null value failed", val);

        parUtil.setValue(null,testId, testVal);
        String tval= parUtil.getString(null,testId);
        Assert.assertEquals("Path value retrieval failed", testVal, tval);
    }



    @Test
    public void ParentIdTest() {

        PropertyConfig conf= new PropertyConfig()
                .setTableName("property")
                .setBasePath("/test-path/")
                .setIdColumnName("name")
                .setValueColumnName("value")
                .setMaxSize(100)
                .setDuration(1, TimeUnit.MINUTES);

        PropertyProvider parProv= PropertyProviderFactory.getPropertyProvider(conf, dataSource);
        PropertyUtil parUtil= new PropertyUtil(parProv);

        String parentVal= RandomStringUtils.randomAscii(10);
        String childId= "TEST."+RandomStringUtils.randomAlphabetic(5)+"."+RandomStringUtils.randomAlphabetic(5);
        String parentId= childId.substring(0, childId.lastIndexOf("."));

        // Get childId and validate parent value!
        parUtil.setValue(null,parentId, parentVal);
        String pval= parUtil.getString(null,childId);
        Assert.assertEquals("Parent value retrieval failed", parentVal, pval);

        // Set value child and get value child!
        String childVal= RandomStringUtils.randomAscii(10);
        parUtil.setValue(null,childId, childVal);
        String cval= parUtil.getString(null,childId);
        Assert.assertEquals("Child value retrieval failed", childVal, cval);
    }


}
