package com.bj.search.common.utils.esClient;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
public class ESSecuredClient {

    private static RestHighLevelClient ES_CLIENT;

    public static RestHighLevelClient initEsClientNew(String ES_HOST, int ES_PORT, String ES_USERNAME, String ES_PASSWORD) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ES_USERNAME, ES_PASSWORD));
        RestClientBuilder builder = RestClient.builder(new HttpHost(ES_HOST, ES_PORT, "https")
        ).setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            try {
                // trust all certifications
                SSLContext sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
                httpAsyncClientBuilder.setSSLContext(sslContext);
            } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
                e.printStackTrace();
            }
            // trust all certifications
            httpAsyncClientBuilder.setSSLHostnameVerifier(new AllowAllHostnameVerifier());
            // set authentication
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpAsyncClientBuilder;
        });
        ES_CLIENT = new RestHighLevelClient(builder);
        return ES_CLIENT;
    }

    public static RestHighLevelClient initESClient(String clusterAddress, int port, String userName, String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLIOSessionStrategy sessionStrategy = new SSLIOSessionStrategy(sc, new NullHostNameVerifier());
        SecuredHttpClientConfigCallback httpClientConfigCallback = new SecuredHttpClientConfigCallback(sessionStrategy, credentialsProvider);
        RestClientBuilder builder = RestClient.builder(new HttpHost(clusterAddress, port, "https")).setHttpClientConfigCallback(httpClientConfigCallback);
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    static TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};

    public static class NullHostNameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }
}