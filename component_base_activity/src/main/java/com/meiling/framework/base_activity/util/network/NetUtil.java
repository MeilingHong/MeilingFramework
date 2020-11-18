package com.meiling.framework.base_activity.util.network;


import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetUtil {
    /**
     * 需要保证在子线程中进行调用
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("HEAD");
        String redirect = conn.getHeaderField("Location");
        conn.disconnect();
        return redirect;
    }

    /**
     * 可达性校验
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean checkUrlAccess(String path) throws Exception {
        HttpsURLConnection.setDefaultSSLSocketFactory(createSSLSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new TrustAllHostnameVerifier());
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        boolean access = true;
        conn.connect();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            access = true;
        } else {
            access = false;
        }
        conn.disconnect();
        return access;
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{createTrustAllManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {

        }
        return sslSocketFactory;
    }

    private static X509TrustManager createTrustAllManager() {
        X509TrustManager tm = null;
        try {
            tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }
}
