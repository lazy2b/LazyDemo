package com.lazy2b.libs.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.lazy2b.libs.BuildConfig;

import android.util.Log;

/**
 * 
 * HTTP请求工具类(带Header参数)
 * 
 * @author jack.lin@qq.com
 * 
 *         $Id: HttpUtil.java 65 2016-05-25 04:19:01Z lazy2b $
 * 
 */
public enum  HttpUtil {
	Instance;
	
	 
	
	private DefaultHttpClient mHttpClient;
	
	private static final boolean DEBUG = BuildConfig.DEBUG;
	
	private static final String TAG = "HttpUtil";
	
	private static final int TIMEOUT = 30;
	
	private static final int BUFF_SIZE = 8192;
	
	/**
	 * 构造函数，只执行一次
	 */
	HttpUtil(){
		HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSocketBufferSize(params, BUFF_SIZE);
        
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register( new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        // Create an HttpClient with the ThreadSafeClientConnManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        
        mHttpClient = new DefaultHttpClient(cm, params);
	}
	
	/**
	 * GET方法
	 * @param url
	 * @param headers
	 * @return 返回http请求的正文
	 */
	public byte[] get(String url, HashMap<String, String> headers){
		
		if ( url.trim().equals("") ) {
			return null;
		}
		
		byte[] bytes = null;
		
		try {
			HttpGet httpGet 		= new HttpGet(url);
			
			if ( DEBUG ){
				Log.d(TAG, "header parameter:" );
			}
	        for (Iterator<Entry<String, String>> it = headers.entrySet().iterator(); it.hasNext(); ) {
	        	Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
				String name = e.getKey();
				String value = e.getValue();
				httpGet.addHeader(name, value);
				
				if ( DEBUG ){
					Log.d(TAG, "key=>" + name + ",value=>" + value );
				}
	        }
			HttpResponse response 	= mHttpClient.execute(httpGet);
			HttpEntity entity 		= response.getEntity();

			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					
					ByteArrayOutputStream bos = new ByteArrayOutputStream();  
					
					int ch; 
					byte[] buf = new byte[BUFF_SIZE]; 
					while ( (ch = inputStream.read(buf)) != -1 ) {  
						bos.write(buf, 0, ch); 
					}  
					
					bytes = bos.toByteArray();  
					bos.close(); 
					inputStream.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				entity.consumeContent();
        		}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			//mHttpClient.getConnectionManager().releaseConnection(null, (Long) null, null);
			//httpClient.getConnectionManager().shutdown();
        }
		
		
		return bytes;
		
	}
	
	/**
	 * 此方法实现http的POST请求
	 * @param url
	 * @param postField
	 * @param headers
	 * @return
	 */
	public byte[] post(String url, HashMap<String, Object> postField, HashMap<String, String> headers) {
		
		byte[] bytes = null;
		
        HttpPost httpPost = new HttpPost(url);
        if ( DEBUG ){
			Log.d(TAG, "header parameter:" );
		}
        
        for (Iterator<Entry<String, String>> it = headers.entrySet().iterator(); it.hasNext(); ) {
        	Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
			String name = e.getKey();
			String value = e.getValue();
			httpPost.addHeader(name, value);
			
			if ( DEBUG ){
				Log.d(TAG, "key=>" + name + ",value=>" + value );
			}
        }
        
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, "UTF-8", null);
        
        if ( DEBUG ){
			Log.d(TAG, "POST parameter:" );
		}
        
        for (Iterator<Entry<String, Object>> it = postField.entrySet().iterator(); it.hasNext(); ) {
        	
			Map.Entry<String, Object> e = (Map.Entry<String, Object>)it.next();
			String key = (String) e.getKey();
			Object value = e.getValue();
			
			if ( DEBUG ){
				Log.d(TAG, "key=>" + key + ",value=>" + value );
			}
			
			if ( value == null ) {
				//todo
			} else {
				
				if (value instanceof ByteArrayBody){
					reqEntity.addPart(key, (ByteArrayBody) value);
				} else if(value instanceof FileBody) {
					reqEntity.addPart(key, (FileBody) value);
					
				} else if(value instanceof InputStreamBody) {
					reqEntity.addPart(key, (InputStreamBody) value ); 
				} else if(value instanceof String) {
					
					try {
						StringBody strField = new StringBody( value.toString(), Charset.forName(HTTP.UTF_8) );
						reqEntity.addPart(key, strField);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					
				} else {
					try {
						StringBody strField = new StringBody( value.toString(), Charset.forName(HTTP.UTF_8) );
						reqEntity.addPart(key, strField);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					
				}
			}
			
        }
        
    
        httpPost.setEntity(reqEntity);
        
		// 设置cookie的兼容性，这一行必须要加，否则服务器无法获取登陆状态
//		HttpClientParams.setCookiePolicy(mHttpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		
		try {
			HttpResponse response 	= mHttpClient.execute(httpPost);
			HttpEntity entity 		= response.getEntity();
			
			if ( entity != null ){
				InputStream is = null;
				try {
					is = entity.getContent();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();  
					
					int ch; 
					byte[] buf = new byte[BUFF_SIZE]; 
					while ( (ch = is.read(buf)) != -1 ) {  
						bos.write(buf, 0, ch); 
					} 
					
					bytes = bos.toByteArray();  
					bos.close();
					is.close();
				} catch ( Exception e){
					e.printStackTrace();
				}
				
				entity.consumeContent();
			}
        	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
        }
        
		
		return bytes;
	}
}