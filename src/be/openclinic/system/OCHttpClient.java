package be.openclinic.system;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

public class OCHttpClient {
	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	Hashtable<String,String> params = new Hashtable();
	Hashtable<String,String> headers = new Hashtable();
	
	public OCHttpClient() {
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	}
	
	public void addStringParam(String sName, String sContent) {
		StringBody sb = new StringBody(sContent,ContentType.MULTIPART_FORM_DATA);
		builder.addPart(sName,sb);
	}
	
	public void addByteArrayParam(String sName, byte[] bContent) {
		ContentBody cb = new ByteArrayBody(bContent,SH.getRandomPassword(10)+".dat");
		builder.addPart(sName,cb);
	}
	
	public void addFileParam(String sName, File file) {
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		builder.addPart(sName, fileBody);
	}
	
	public void addXmlParam(String sName, String sXml) {
		ContentBody cb = new StringBody(sXml,ContentType.APPLICATION_XML);
		builder.addPart(sName, cb);
	}
	
	public void addBody(String sXml) {
		builder.addTextBody("dataValueSets", sXml, ContentType.APPLICATION_XML);
	}
	
	public void addParam(String sName,String sValue) {
		params.put(sName, sValue);
	}
	
	public void addHeader(String sName,String sValue) {
		headers.put(sName, sValue);
	}
	
	public CloseableHttpResponse post(String url) throws ClientProtocolException, IOException {
		Enumeration e = params.keys();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			if(url.contains("?")){
				url+="&";
			}
			else {
				url+="?";
			}
			url+=key+"="+params.get(key);
		}
		HttpEntity entity = builder.build();
		HttpPost post = new HttpPost(url);
		e = headers.keys();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			post.setHeader(key, headers.get(key));
		}
		post.setEntity(entity);
		CloseableHttpClient client = HttpClients.createDefault(); 
		return client.execute(post);
	}
	
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		Enumeration e = params.keys();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			if(url.contains("?")){
				url+="&";
			}
			else {
				url+="?";
			}
			url+=key+"="+params.get(key);
		}
		HttpGet get = new HttpGet(url);
		e = headers.keys();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			get.setHeader(key, headers.get(key));
		}
		CloseableHttpClient client = HttpClients.createDefault(); 
		return client.execute(get);
	}
	
	public CloseableHttpResponse postAuthenticated(String url, String userName, String password) throws ClientProtocolException, IOException {
		HttpEntity entity = builder.build();
		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(userName, password)
        );
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build(); 
		return client.execute(post);
	}
	
	public Element getRootElement(HttpResponse response) {
		HttpEntity entity = response.getEntity();
	    if(entity!=null){
			Document doc;
			try {
				doc = org.dom4j.DocumentHelper.parseText(EntityUtils.toString(entity));
				return(doc.getRootElement());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    return null;
	}
}
