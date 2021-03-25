package com.saturn.common.test;

import com.saturn.commons.utils.file.ResourceCache;
import java.util.concurrent.ExecutionException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Resource Test class
 */
public class ResourceTest {


    @Test
    public void runTest() throws ExecutionException {
        ResourceCache files= new ResourceCache();
        String content= files.getResource("Test.properties");
        Assert.assertNotNull("Test file not found", content);

    }

}
