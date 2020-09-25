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
        dataSource= initDataSource();
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

        PropertyProvider propProv= PropertyProviderFactory.getPropertyProvider(conf, dataSource);
        PropertyUtil props= new PropertyUtil(propProv);

        // Generate random Ids and paths
        String propId= "TEST."+RandomStringUtils.randomAlphabetic(5)+"."+RandomStringUtils.randomAlphabetic(5);
        String path1= RandomStringUtils.randomAlphabetic(5);
        String path2= path1+ "/" + RandomStringUtils.randomAlphabetic(5);

        /******************
         * Set root value
         ******************/
        String rootVal= RandomStringUtils.randomAscii(10);
        props.setValue(null,propId, rootVal);

        // Ask for child value, expect root value
        String val= props.getString(path1,propId);
        Assert.assertEquals("[ROOT] Child value retrieval failed", rootVal, val);

        // Ask for grandChild value, expect root value
        val= props.getString(path2,propId);
        Assert.assertEquals("[ROOT] Grandchild value retrieval failed", rootVal, val);

        /******************
         * Set Path1 value
         ******************/
        String val1= RandomStringUtils.randomAscii(10);
        props.setValue(path1, propId, val1);

        // Ask for child value, expect val1
        val= props.getString(path1,propId);
        Assert.assertEquals("[PATH1] Child value retrieval failed", val1, val);

        // Ask for grandChild value, expect val1
        val= props.getString(path2,propId);
        Assert.assertEquals("[PATH1] Grandchild value retrieval failed", val1, val);

        /******************
         * Set Path2 value
         ******************/
        String val2= RandomStringUtils.randomAscii(10);
        props.setValue(path2, propId, val2);

        // Ask for grandChild value, expect val2
        val= props.getString(path2,propId);
        Assert.assertEquals("[PATH2] Grandchild value retrieval failed", val2, val);

    }


}
