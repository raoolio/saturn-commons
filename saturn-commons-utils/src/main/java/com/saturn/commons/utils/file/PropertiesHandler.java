package com.saturn.commons.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Local cache handler for MemberIds
 */
public class PropertiesHandler extends Properties {

    /** Logger */
    private static final Logger LOG= LogManager.getLogger(PropertiesHandler.class);

    /** Properties path */
    private String path;
    
    /** Properties comment */
    private String comment;



    /**
     * Constructor
     * @param path Properties file path
     */
    public PropertiesHandler(String path) {
        super();
        this.path=path;
        loadProperties(path);
    }



    /**
     * Retrieve collection of memberIds mapped by email
     * @param path
     * @return
     */
    private void loadProperties(String path) {
        File f= new File(path);

        // file exists?
        if (f.isFile()) {
            LOG.warn("File["+path+"] found! -> Reading properties...");
            try (Reader r= new BufferedReader(new FileReader(path))){
                load(r);
                LOG.debug("["+size()+"] entries loaded!");
            } catch (Exception e) {
                LOG.warn("Error reading properties from file["+path+"] -> "+e);
            }
        } else
            LOG.warn("File["+path+"] not found!");
    }



    /**
     * Store list of emails by memberIds
     * @param comment Comment of the properties
     * @return
     */
    public boolean saveProperties(String comment) {
        boolean saved=false;

        try (Writer w= new BufferedWriter(new FileWriter(path))){
            store(w, comment);
            saved=true;
            LOG.debug("["+size()+"] entries saved!");
        } catch (Exception e) {
            LOG.warn("Error storing properties to file["+path+"] -> "+e);
        }

        return saved;
    }







}
