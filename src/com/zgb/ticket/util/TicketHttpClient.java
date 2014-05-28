package com.zgb.ticket.util;

import java.security.KeyStore;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class TicketHttpClient {
	public static AsyncHttpClient httpclient = new AsyncHttpClient();
	private static String host = "kyfw.12306.cn";
	private static String baseurl = "https://kyfw.12306.cn";
	private static TicketHttpClient client;
	
	private TicketHttpClient(){};
	
	public static TicketHttpClient getClient() {
		if(client == null){
			client = new TicketHttpClient();
			return client;
		}else{
			return client;
		}
	}
	
	
	public static void init(Context context){
		try {
		      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		      trustStore.load(null, null);
		      MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
		      sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		      httpclient.setSSLSocketFactory(sf);   
		    }
		    catch (Exception e) {   
		    }
		
		setCookies(context);
		httpclient.removeHeader("User-Agent");
		httpclient.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
		//httpclient.addHeader("Referer", "https://kyfw.12306.cn/otn/login/init");
		//httpclient.addHeader("Origin", baseurl);
	}
	
	public static void setCookies(Context context){
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		httpclient.setCookieStore(myCookieStore);
	}
	

  public static void get(Context context , String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	  if(params != null){
		  Log.i("get params", params.toString());
	  }
	  Log.i("get url", getAbsoluteUrl(url));
	 
	  httpclient.get(context, getAbsoluteUrl(url), params, responseHandler);
  }

  public static void post(Context context , String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	  if(params != null){
		  Log.i("post params", params.toString());
	  }
	  Log.i("post url", getAbsoluteUrl(url));
	  
	  httpclient.post(context , getAbsoluteUrl(url), params, responseHandler);
  }

  private static String getAbsoluteUrl(String relativeUrl) {
      return baseurl + relativeUrl;
  }
  
}
