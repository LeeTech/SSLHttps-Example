package com.hiteamtech.ddbes.request;

import android.content.Context;

import com.android.volley.toolbox.HurlStack;
import com.hiteamtech.ddbes.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Hashtable;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * ClassName: HiTeamSSLHttpsStack
 * Description 单向加密Stack
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/1/25 12:30
 */
public class HiTeamSSLHttpsStack extends HurlStack {


    private Map<String, SSLSocketFactory> socketFactoryMap;

    public HiTeamSSLHttpsStack(Context context) {
        this.socketFactoryMap = getSSLSocketFactory(context);
    }

    /**
     * 获取单向SSLSocketFactory
     *
     * @param context
     * @return
     */
    private static Map<String, SSLSocketFactory> getSSLSocketFactory(Context context) {
        String[] hosts = {context.getResources().getString(R.string.host)};
        int[] certRes = {R.raw.ddbes};
        String[] certPass = {context.getResources().getString(R.string.certPass)};
        Map<String, SSLSocketFactory> socketFactoryMap = new Hashtable<>(hosts.length);
        for (int i = 0; i < certRes.length; i++) {
            int res = certRes[i];
            String password = certPass[i];
            SSLSocketFactory sslSocketFactory = null;
            try {
                sslSocketFactory = createSSLSocketFactory(context, res, password);
                socketFactoryMap.put(hosts[i], sslSocketFactory);
            } catch (CertificateException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (KeyStoreException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return socketFactoryMap;
    }


    /**
     * 单向配置
     *
     * @param context
     * @param res
     * @param password
     * @return
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    private static SSLSocketFactory createSSLSocketFactory(Context context, int res, String password)
            throws CertificateException,
            NoSuchAlgorithmException,
            IOException,
            KeyStoreException,
            KeyManagementException {
        InputStream inputStream = context.getResources().openRawResource(res);
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(inputStream, password.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
        return sslContext.getSocketFactory();
    }


    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setSSLSocketFactory(socketFactoryMap.get(url.getHost()));
        return connection;
    }
}
