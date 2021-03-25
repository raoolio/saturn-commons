package com.saturn.commons.utils.file;

import com.google.common.cache.CacheLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Resource Loader
 */
public class ResourceLoader extends CacheLoader<String,String> {


    /**
     * Fetch the content of given resource
     * @param r
     * @return
     * @throws IOException
     */
    private String fetchResource(String fileName) throws IOException {

        StringBuilder b= new StringBuilder();
        String ln=null;
        try (BufferedReader in= new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName))) ) {
           while ( (ln=in.readLine()) != null ) {
               b.append(ln).append('\n');
           }
        }

        return b.toString();
    }



    @Override
    public String load(String fileName) throws Exception {
        return fetchResource(fileName);
    }



}
