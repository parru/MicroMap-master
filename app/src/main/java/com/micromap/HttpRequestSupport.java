package com.micromap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRequestSupport {

	/**
	 * 
	 * @param data
	 * @param method
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getHttpRequestData(Map<String, String> data,
			String method) {
		if (method.equalsIgnoreCase("get")) {
			// get 方法，需要将请求对象封装成字符串
			Iterator iter = data.entrySet().iterator();
			String dataString = "";
			int i = 1;
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String key = entry.getKey().toString();
				String val = entry.getValue().toString();
				dataString += key + "=";
				dataString += val;
				if (i < data.size() ) {
					dataString += "&";
				}
			}
			return dataString;
		} else {
			// post 方法，需要将请求对象封装成POST对象
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			Iterator iter = data.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String key = entry.getKey().toString();
				String val = entry.getValue().toString();
				postParameters.add(new BasicNameValuePair(key, val));
			}
			return postParameters;
		}
		//return null;
	}

	/**
	 * 
	 * @param uri
	 * @param data
	 * @return
	 */
	//执行Get方法
	public static String executeHttpGet(String uri, Map<String, String>data) {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			String dataString = getHttpRequestData(data, "get").toString();
			request.setURI(new URI(uri + "?" + dataString));
			HttpResponse response = client.execute(request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	//执行Get方法
	@SuppressWarnings("unchecked")
	public String executePost(String uri, Map<String, String> data) {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			request.setURI(new URI(uri));
			List<NameValuePair> postParameters = 
				(List<NameValuePair>)getHttpRequestData(data, "post");
			
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);

			HttpResponse response = client.execute(request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
