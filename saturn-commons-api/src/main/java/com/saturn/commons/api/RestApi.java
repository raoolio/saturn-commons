package com.saturn.commons.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saturn.commons.utils.TimeUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Base API class for REST controller implementations
 */
public class RestApi {

    /** Logger */
    protected Logger LOG= LogManager.getLogger(this.getClass());

    /** Start time (milliseconds) */
    private Long startTime;


    
    /**
     * Constructor
     * @param title Rest title
     */
    public RestApi(String title) {
        LOG.info("Starting "+title);
        startTime= System.currentTimeMillis();
    }




    @RequestMapping(method=GET, value="/status")
    @ResponseBody
    public Object status() throws Exception {

        long t= System.currentTimeMillis() - startTime;

        Map m=new LinkedHashMap();
        m.put("status", "OK");
        m.put("run-time", TimeUtils.milisToString(t));
        ObjectMapper jsonMapper= new ObjectMapper();

        // Convert response -> HTTP Response
        return ResponseEntity.status(200)
                .body(jsonMapper.writeValueAsString(m));
    }



}
