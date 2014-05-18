package com.zgb.ticket;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class ConstructHeaders {
	//private static Header[] headersForLogin;
	//private static Header[] headersForSearch;
	
	
	public static Header[] getHeaders(int type){
		Header[] headers;
		Header[] headersForLogin = new Header[3];
		headersForLogin[0] = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
		headersForLogin[0] = new BasicHeader("","");
		headersForLogin[0] = new BasicHeader("","");
		
		Header[] headersForSearch = new Header[3];
		switch (type) {
		case 0:
			headers =  headersForLogin;
			break;
		case 1:
			headers =  headersForSearch;
			break;
		default:
			headers =  headersForLogin;
			break;
		}
		
		return headers;
	}
}
