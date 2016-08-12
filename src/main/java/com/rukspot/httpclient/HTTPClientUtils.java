package com.rukspot.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.CloseableHttpClient;

public class HTTPClientUtils {
	private final String USER_AGENT = "Apache-HttpClient/4.2.5 (java 1.7)";

	public static Response doGet(String url, List<NameValuePair> headers)
			throws Exception {

		CloseableHttpClient httpClient = HttpClient.getHttpsClient();
		HttpResponse response = sendGetRequest(httpClient, url, headers);
		return constructResponse(response);
	}

	public static Response doPost(String url, List<NameValuePair> headers,
			List<NameValuePair> urlParameters) throws Exception {
		CloseableHttpClient httpClient = HttpClient.getHttpsClient();
		HttpResponse response = sendPOSTMessage(httpClient, url, headers,
				urlParameters);
		return constructResponse(response);
	}

	private static HttpResponse sendGetRequest(CloseableHttpClient httpClient,
			String url, List<NameValuePair> headers) throws Exception {
		HttpGet request = new HttpGet(url);
		HttpClientParams.setRedirecting(request.getParams(), false);
		if (headers != null) {
			for (NameValuePair nameValuePair : headers) {
				request.addHeader(nameValuePair.getName(),
						nameValuePair.getValue());
			}
		}
		return httpClient.execute(request);
	}

	private static HttpResponse sendPOSTMessage(CloseableHttpClient httpClient,
			String url, List<NameValuePair> headers,
			List<NameValuePair> urlParameters) throws Exception {
		HttpPost post = new HttpPost(url);
		if (headers != null) {
			for (NameValuePair nameValuePair : headers) {
				post.addHeader(nameValuePair.getName(), nameValuePair.getValue());
			}
		}
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		return httpClient.execute(post);
	}

	private static String getResponseBody(HttpResponse response)
			throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent(), "UTF-8"));
		String line;
		StringBuffer sb = new StringBuffer();

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		return sb.toString();
	}

	private static Response constructResponse(HttpResponse response)
			throws IOException {
		int code = response.getStatusLine().getStatusCode();
		String body = getResponseBody(response);
		Header[] headers = response.getAllHeaders();
		Map<String, String> heads = new HashMap<String, String>();
		for (Header header : headers) {
			heads.put(header.getName(), header.getValue());
		}
		Response res = new Response();
		res.setBody(body);
		res.setStatusCode(code);
		res.setHeaders(heads);
		return res;
	}
}
