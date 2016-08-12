package com.rukspot.httpclient;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClient {

	public static CloseableHttpClient getHttpsClient() {
		CloseableHttpClient httpClient = HttpClients
				.custom()
				.setHostnameVerifier(
						SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
				.build();
		return httpClient;
	}

	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient;
	}
}
