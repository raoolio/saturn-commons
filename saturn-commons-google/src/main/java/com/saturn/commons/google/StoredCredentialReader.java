package com.saturn.commons.google;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import com.google.api.client.auth.oauth2.StoredCredential;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Lee la clase StoredCredential del disco a Memoria, mostrando sus atributos
 * @author raoolio
 */
public class StoredCredentialReader {

    /** Logger */
    private static final Logger LOG= LogManager.getLogger(StoredCredentialReader.class);



    /**
     * Reads an objects from given file
     * @param path Source file
     * @return
     */
    private static Object readObjectFromFile(File path) {
        Object o=null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            o= ois.readObject();
        } catch (Exception e) {
            LOG.error("Error reading java object from["+path+"]",e);
        }
        return o;
    }



    /**
     * Reads an object from given byte array.
     * @param bytes Byte array
     * @return
     * @throws Exception
     */
    private static Object byteArray2Object(byte[] bytes) throws Exception {
        Object o=null;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            o= ois.readObject();
        }
        return o;
    }



    /**
     * Reads a StoredCredential instance from given path
     * @param path Source path
     * @return
     */
    public static StoredCredential readCredential(File path) throws Exception {
        StoredCredential credential=null;

        File credentialFile= path.getName().endsWith("StoredCredential") ? path : new File(path,"StoredCredential");
        Validate.isTrue(credentialFile.isFile(), "Invalid file["+path+"]");

        // Read Map from file
        Object ob= readObjectFromFile(path);
        Validate.notNull(ob,"Invalid object file["+credentialFile+"]");
        Validate.isTrue(ob instanceof Map,"Invalid object class["+ob+"]");

        // Read user attribute
        Map map= (Map) ob;

        // Get user value
        Object usr= map.get("user");
        Validate.notNull(usr,"'user' attribute not found!");
        Validate.isTrue(usr instanceof byte[],"Invalid user value");

        byte[] bytes= (byte[]) usr;
        credential= (StoredCredential) byteArray2Object(bytes);
        Validate.notNull(credential,"Error parsing StoredCredential from byte array");

        return credential;
    }



}
