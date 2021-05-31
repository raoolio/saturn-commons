package com.saturn.commons.http.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * SSLUtil class
 * @author raoolio
 */
public class SSLUtil {

    /** Logger */
    protected static Logger LOG=LogManager.getLogger(SSLUtil.class);

    /** Socket factory by protocol name */
    private static Map<String,SSLSocketFactory> socketFactMap= new HashMap();


    /** Trusting hostname verifier */
    private static final HostnameVerifier trustyHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


    /**
     * Friendly trust manager
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }
        }
    };


    /**
     * Returns a Trusting SSL SocketFactory instance
     * @param protocol SSL protocol
     * @return
     */
    public static synchronized SSLSocketFactory getTrustingSocketFactory(String protocol) {
        SSLSocketFactory socFact=null;
        Validate.notBlank(protocol,"Invalid SSL protocol");
        socFact= socketFactMap.get(protocol);

        if (socFact==null) {
            try {
                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance(protocol);
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                socFact= sc.getSocketFactory();
                socketFactMap.put(protocol, socFact);
            } catch (Exception e) {
                LOG.error("Error creating SSL socket factory", e);
            }
        }

        return socFact;
    }






    /**
     * Returns all-trusting host name verifier
     * @return
     */
    public static HostnameVerifier getTrustingHostnameVerifier() {
        return trustyHostnameVerifier;
    }



    /**
     * Registers a TrustManager that skips SSL validation
     */
    public static final void registerTrustingSSLManager() {
        registerTrustingSSLManager("SSL");
    }



    /**
     * Registers a TrustManager that skips SSL validation
     * @param protocol
     */
    public static final void registerTrustingSSLManager(String protocol) {
        Validate.notBlank(protocol, "Invalid SSL protocol");

        // Check SSL SocketFactory
        SSLSocketFactory trustingFact= getTrustingSocketFactory(protocol);
        SSLSocketFactory curFactory= HttpsURLConnection.getDefaultSSLSocketFactory();

        // Trusting factory not set?
        if (curFactory != trustingFact) {
            LOG.debug("Installing default trusting SSLSocketFactory!");
            HttpsURLConnection.setDefaultSSLSocketFactory(trustingFact);
        }

        // Check HostnameVerifier
        HostnameVerifier trustingVerifier= getTrustingHostnameVerifier();
        HostnameVerifier curVerifier= HttpsURLConnection.getDefaultHostnameVerifier();

        // Install the all-trusting host verifier?
        if (curVerifier != trustingVerifier) {
            LOG.debug("Installing default trusting HostnameVertifier!");
            HttpsURLConnection.setDefaultHostnameVerifier(trustingVerifier);
        }

    }


}
