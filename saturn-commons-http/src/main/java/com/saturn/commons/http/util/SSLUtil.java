package com.saturn.commons.http.util;

import static com.saturn.commons.http.impl.client.BaseHttpClient.LOG;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * SSLUtil class
 * @author raoolio
 */
public class SSLUtil {

    /** Trusting SSL Socket Factory */
    private static SSLSocketFactory socketFactory;

    /** Trusting Hostname Verifier */
    private static HostnameVerifier hostVerifier;



    /**
     * Returns a Trusting SSL SocketFactory instance
     * @return
     */
    private static synchronized SSLSocketFactory getTrustingSocketFactory() {
        if (socketFactory==null) {
            //<editor-fold defaultstate="collapsed" desc=" Create trust manager ">
            TrustManager[] trustAllCerts = new TrustManager[] {
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
            //</editor-fold>

            try {
                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                socketFactory= sc.getSocketFactory();
            } catch (Exception e) {
                LOG.error("Error installing cert validator", e);
            }

        }
        return socketFactory;
    }



    /**
     * Returns all-trusting host name verifier
     * @return
     */
    private static synchronized HostnameVerifier getTrustingHostnameVerifier() {
        if (hostVerifier==null) {
            //
            hostVerifier = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
        }
        return hostVerifier;
    }



    /**
     * Registers a TrustManager that skips SSL validation
     */
    public static final void registerTrustingSSLManager() {

        // Check SSL SocketFactory
        SSLSocketFactory trustingFact= getTrustingSocketFactory();
        SSLSocketFactory curFactory= HttpsURLConnection.getDefaultSSLSocketFactory();

        // Trusting factory not set?
        if (curFactory != trustingFact) {
            HttpsURLConnection.setDefaultSSLSocketFactory(trustingFact);
        }

        // Check HostnameVerifier
        HostnameVerifier trustingVerifier= getTrustingHostnameVerifier();
        HostnameVerifier curVerifier= HttpsURLConnection.getDefaultHostnameVerifier();

        // Install the all-trusting host verifier?
        if (curVerifier != trustingVerifier) {
            HttpsURLConnection.setDefaultHostnameVerifier(trustingVerifier);
        }

    }


}
