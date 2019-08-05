package com.saturn.common.test;

import com.saturn.commons.utils.json.JsonUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;


/**
 * JSON Parser tester
 */
public class JsonTest {

    /** Sample JSON array */
    private static final String JSON_ARRAY= "[ { \"id\": \"uno\", \"val\":1 }, { \"id\": \"dos\", \"val\":2 }, { \"id\": \"tres\", \"val\":3 }, { \"id\": \"cuatro\", \"val\":4 } ]";

    /** Sample JSON object */
    private static final String JSON_OBJECT1= "{ \"response\": { \"id\": \"00454541\", \"name\":\"Example object\" } }";
    private static final String JSON_OBJECT2= "{ \"id\": \"00454541\", \"name\":\"Example object\" }";



    @Test
    public void getListById() throws IOException {
        Object[] expected= new Object[]{ "uno", "dos", "tres", "cuatro" };
        Object[] actual= JsonUtils.getListById(JSON_ARRAY,"id").toArray();
        Assert.assertArrayEquals("Invalid list parsing",expected, actual);
    }



    @Test
    public void getAllObjectAttributes() throws IOException {
        Map<String,String> ob= JsonUtils.getAllObjectAttributes(JSON_OBJECT1,"response");
        Assert.assertEquals("Invalid id Attribute", "00454541", ob.get("id"));
        Assert.assertEquals("Invalid name Attribute", "Example object", ob.get("name"));
    }



    @Test
    public void getObjectAttributes() throws IOException {
        Map<String,String> ob= JsonUtils.getObjectAttributes(JSON_OBJECT2,"id,name");
        Assert.assertEquals("Invalid id Attribute", "00454541", ob.get("id"));
        Assert.assertEquals("Invalid name Attribute", "Example object", ob.get("name"));
    }



    @Test
    public void getListAsMap() throws IOException {
        List<Map<String,String>> lst= JsonUtils.getListAsMap(JSON_ARRAY,"id,val");

        Map m= lst.get(0);
        Assert.assertEquals("Invalid object Attribute", "uno", m.get("id"));
        Assert.assertEquals("Invalid object Attribute", "1", m.get("val"));

        m= lst.get(3);
        Assert.assertEquals("Invalid object Attribute", "cuatro", m.get("id"));
        Assert.assertEquals("Invalid object Attribute", "4", m.get("val"));
    }



    @Test
    public void getMapByIdAndValue() throws IOException {
        Map<String,String> ob=JsonUtils.getMapByIdAndValue(JSON_ARRAY,"id","val",null);
        Assert.assertEquals("Invalid object Attribute", "1", ob.get("uno"));
        Assert.assertEquals("Invalid object Attribute", "2", ob.get("dos"));
        Assert.assertEquals("Invalid object Attribute", "3", ob.get("tres"));
        Assert.assertEquals("Invalid object Attribute", "4", ob.get("cuatro"));
    }



//    @Test
//    public void parseJson() throws IOException {
//        JsonUtils.parseJson();
//    }



    @Test
    public void getIdValue() throws IOException {
        String val= JsonUtils.getIdValue(JSON_OBJECT1,"id");
        Assert.assertEquals("Invalid attribute value", "00454541", val);
        
    }


}
