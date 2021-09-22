package com.saturn.commons.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import java.io.File;
import java.time.Instant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Google Credential Handler
 */
public class GoogleCredentialHandler {

    /** Logger */
    private static final Logger LOG= LogManager.getLogger(GoogleCredentialHandler.class);

    /** Credential store folder */
    private File credentialFile;



    /**
     * Constructor
     * @param credentialFolder
     */
    public GoogleCredentialHandler(String credentialFolder) {
        this.credentialFile =  new File(credentialFolder,"StoredCredential");
    }



    /**
     * Deletes the store file if token has been removed
     * @return
     */
    public boolean deleteIfTokenRevoked() {
        boolean done=false;
        if (isTokenRevoked()) {
            done=deleteCredentialFile();
        }
        return done;
    }



    /**
     * Tells if credential token has been removed from the store
     * @return
     */
    public boolean isTokenRevoked() {
        boolean revoked=false;
        try {
            StoredCredential credential= StoredCredentialReader.readCredential(credentialFile);
            Long expiration= credential.getExpirationTimeMilliseconds();
            String token=credential.getAccessToken();
            revoked= (expiration==null);

            if (!revoked) {
                Instant humanTime= Instant.ofEpochMilli(expiration);
                LOG.info("Token not revoked: TOKEN["+token+"] EXPIRES["+humanTime+"]");
            }

            LOG.info("File["+credentialFile+"] -> ["+revoked+"]");
        } catch (Exception e) {
            LOG.error("Error validating credential file["+credentialFile+"]",e);
        }
        return revoked;
    }



    /**
     * Deletes the credential file
     * @return
     */
    public boolean deleteCredentialFile() {
        boolean done=false;
        try {
            done= credentialFile.delete();
            LOG.warn("["+credentialFile+"] -> ["+done+"]");
        } catch (Exception e) {
            LOG.error("Error deleting credential file["+credentialFile+"]",e);
        }
        return done;
    }


}
