package com.saturn.commons.utils.web;

import com.saturn.commons.utils.string.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * User-Agents provider
 */
public class UserAgentProvider {

    /** Logger */
    private static final Logger LOG=LogManager.getLogger(UserAgentProvider.class);

    /** Singleton instance */
    private static Map<String,UserAgentProvider> agentsMap= new HashMap<>();

    /** User-Agent strings */
    private List<String> userAgents;



    /**
     * Returns unique instance of the class
     * @param fileName Agents file name
     * @return
     */
    public static synchronized UserAgentProvider getInstance(String fileName) {
        UserAgentProvider uap= agentsMap.get(fileName);

        if (uap==null) {
            uap= new UserAgentProvider(fileName);
            agentsMap.put(fileName, uap);
        }

        return uap;
    }



    /**
     * Constructor
     */
    private UserAgentProvider(String fileName) {
        userAgents= loadAgents(fileName);
    }



    /**
     * Fetch User-Agents list
     * @return
     */
    private List<String> loadAgents(String fileName) {
        List<String> l= new LinkedList<>();

        try (BufferedReader br= new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
            String line=null;
            while ( (line=br.readLine())!=null ) {
                if (StringUtils.isNotBlank(line) ) {
                    l.add(line.trim());
                }
            }

        } catch (Exception ex) {
            LOG.error("Error reading User-Agent file", ex);
        }
        LOG.info("["+l.size()+"] User-Agents loaded!");

        return l;
    }



    /**
     * Retrieve random User-Agent string
     * @return
     */
    public String getUserAgent() {
        double rnd= Math.random();
        int pos= (int)(userAgents.size()*rnd);
        return userAgents.get(pos);
    }



}
