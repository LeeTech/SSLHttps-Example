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
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * ClassName: HiTeamBiDSSLHttpsStack
 * Description 双向加密Stack
 * Auther lijun lee mingyangnc@163.com
 * Date 2016/3/25 14:47
 */
public class HiTeamBiDSSLHttpsStack extends HurlStack {

    private static final String TLS = "TLS";

    private static final String KEY_STORE_TYPE_BKS = "BKS";

    private static final String KEY_STORE_TYPE_P12 = "PKCS12";

    private Context mContext;

    private SSLSocketFactory sslSocketFactory;

    public HiTeamBiDSSLHttpsStack(Context context) {
        this.mContext = context;
        initSSLSocketFactory();
    }

    private void initSSLSocketFactory() {
        try {

            SSLContext context = SSLContext.getInstance(TLS);

            KeyManager[] keyManagers = createKeyManagers(R.raw.client_key, mContext.getResources().getString(R.string.key_store_pwd));

            TrustManager[] trustManagers = createTrustManagers(R.raw.client_trust, mContext.getResources().getString(R.string.trust_store_pwd));

            context.init(keyManagers, trustManagers, null);
            sslSocketFactory = context.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private KeyManager[] createKeyManagers(int keyStoreFileRaw, String keyStorePassword) throws IOException,
            KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

        InputStream inputStream = mContext.getResources().openRawResource(keyStoreFileRaw);
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
        keyStore.load(inputStream, keyStorePassword.toCharArray());

        KeyManager[] keyManagers;
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword == null ? null : keyStorePassword.toCharArray());

        keyManagers = keyManagerFactory.getKeyManagers();
        return keyManagers;

    }


    private TrustManager[] createTrustManagers(int trustStoreFileRaw, String trustStorePassword) throws IOException,
            KeyStoreException, CertificateException, NoSuchAlgorithmException {

        InputStream inputStream = mContext.getResources().openRawResource(trustStoreFileRaw);
        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
        trustStore.load(inputStream, trustStorePassword.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        return trustManagerFactory.getTrustManagers();

    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {

        // 信任主机
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setSSLSocketFactory(sslSocketFactory);
        return connection;
    }
}
