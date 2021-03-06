package pfq.store.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pfq.store.AppSettings;
import pfq.store.AppUtil;

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
    
    public HttpResponse doPost(String path, HashMap<String,String> variables,RequestType requestType) throws ClientProtocolException, URISyntaxException, IOException {
    	return doPost(path,variables,requestType,null);
    }
    
    public HttpResponse doPost(String path, HashMap<String,String> variables,RequestType requestType, File file) throws URISyntaxException, ClientProtocolException, IOException {
    	URIBuilder builder = new URIBuilder();
    	builder.setScheme("http").setHost(AppSettings.get("host")).setPort(AppSettings.getInt("port", 8080)).setPath(path);
    	HttpPost httppost = new HttpPost(builder.build());
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	httppost.setHeader("User-Agent", "Java client");
    	
    	if(requestType.equals(RequestType.JSON)) {
    		StringEntity postingString = new StringEntity( mapper.writeValueAsString(variables), "utf-8");
    		httppost.setEntity(postingString);
    		httppost.setHeader("Accept", "application/json");
    		httppost.setHeader("Content-type", "application/json; charset=utf-8");
    		System.out.println("isJson");
    	}else if(requestType.equals(RequestType.TEXT)){
    		
    		for (String key: variables.keySet()) {
        		params.add(new BasicNameValuePair(key, URLEncoder.encode( variables.get(key), "UTF-8" )));
        	}
    		
    		httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    		httppost.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
    		
    		httppost.setEntity(new UrlEncodedFormEntity(params));
    	}else if(requestType.equals(RequestType.FILE)){
    		String boundary = "---------------"+UUID.randomUUID().toString();
    		httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    		httppost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.getMimeType()+";boundary="+boundary);
    		httppost.setHeader("Accept-Encoding", "gzip, deflate");
    		
            HttpEntity data = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName())
                    .addTextBody("json", mapper.writeValueAsString(variables), ContentType.APPLICATION_JSON)
                    .build();
    		
    		httppost.setEntity(data);
    	}
    	
    	System.out.println(httppost.getEntity());
    	HttpResponse response = httpClient.execute(httppost);
    	
    	httppost.completed();
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
				HttpResponse res = ConnettionService.this.doPost("/login",variables,RequestType.TEXT);
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
