package pfq.store.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import pfq.store.AppSettings;

public class ConnettionService {
	private HttpClient  httpClient;
	private CookieStore cookieStore;
	private HttpContext httpContext;
	private boolean     isConnect;
	ObjectMapper mapper = new ObjectMapper();
	
	
	private ConnettionService() {
		super();
		httpClient  = HttpClientBuilder.create().build();
		cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
	}
	
	public static Builder newBuilder() {
        return new ConnettionService().new Builder();
    }
	
    public Builder getBuilder() {
        return this.new Builder();
    }
    
    public HttpResponse doPost(String path, HashMap<String,String> variables,boolean isJson) throws URISyntaxException, ClientProtocolException, IOException {
    	URIBuilder builder = new URIBuilder();
    	builder.setScheme("http").setHost(AppSettings.get("host")).setPort(AppSettings.getInt("port", 8080)).setPath(path);
    	HttpPost httppost = new HttpPost(builder.build());
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    
    	if(isJson) {
    		StringEntity postingString = new StringEntity( mapper.writeValueAsString(variables), "utf-8");
    		httppost.setEntity(postingString);
    		httppost.setHeader("Accept", "application/json");
    		httppost.setHeader("Content-type", "application/json; charset=utf-8");
    		System.out.println("isJson");
    	}else {
    		
    		for (String key: variables.keySet()) {
    	    	
        		params.add(new BasicNameValuePair(key, URLEncoder.encode( variables.get(key), "UTF-8" )));
        	}
    		
    		httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    		httppost.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
    		
    		httppost.setEntity(new UrlEncodedFormEntity(params));
    	}
    	
    	System.out.println(httppost.getEntity());
    	HttpResponse response = httpClient.execute(httppost);
    	return response;
    }
    
    public HttpResponse doGet(String path, HashMap<String,String> variables,boolean isJson) throws ClientProtocolException, IOException, URISyntaxException {
    	URIBuilder builder = new URIBuilder();
    	builder.setScheme("http").setHost(AppSettings.get("host")).setPort(AppSettings.getInt("port", 8080)).setPath(path);
    	for (String key: variables.keySet()) {
    	    builder.setParameter(key, variables.get(key));
    	}
    	HttpGet request = new HttpGet(builder.build());
    	if(isJson) {
    		request.setHeader("Accept", "application/json");
    		request.setHeader("Content-type", "application/json");
    	}else {
    		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    		request.setHeader("Content-type", "application/x-www-form-urlencoded");
    	}
    	HttpResponse response = httpClient.execute(request);
    	return response;
    }
	
	public class Builder {
		private Builder() { }
		
		public Builder connect(String login,String password) {
			HashMap<String,String> variables = new HashMap<>();
			variables.put("login", login);
			variables.put("password", password);
			
			try {
				HttpResponse res = ConnettionService.this.doPost("/login",variables,false);
				//System.out.println(res.getStatusLine().getStatusCode());
				if (  res.getStatusLine().getStatusCode() > 302 ) {
					ConnettionService.this.isConnect = false;
				}else {ConnettionService.this.isConnect = true;}
				
			} catch (URISyntaxException | IOException e) {
				ConnettionService.this.isConnect = false;
			}
			return this;
		}
		
		public ConnettionService build() throws ConnettionServiceException {
			if(!ConnettionService.this.isConnect) {throw new ConnettionServiceException("Client not connect");}
			return ConnettionService.this;
		}
	}
	
	
	
}
