/*
 * project	Java2Week3
 * 
 * package	com.rbarnes.java2week3
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Feb 21, 2013
 */
package com.rbarnes.java2week3;

import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;


import com.rbarnes.lib.FileInterface;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DessertService extends Service{

	Context _context;
	static Boolean _connected = false;
	static String _connectionType = "Unavailable";
	Toast _toast;
	HashMap<String, String> _oldLocation;
	
	 @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
		 _context = this;
		 Boolean connected = getConnectionStatus(_context);
		 Toast.makeText(this,"This service works :)", Toast.LENGTH_LONG).show();
		 if (connected){
				Log.i("SERVICE", "CONNECTED");
			}
		 _oldLocation = new HashMap<String, String>();
		 getLocations("cookies", "98404");
		 this.stopSelf();
		 
	    return Service.START_NOT_STICKY;
	  }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("service", "Service started");
		this.stopSelf();
	}
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("service", "Service destroyed");
    }
  //Get the type of network connection
  	/**
  	 * Gets the connection type.
  	 *
  	 * @param context the context
  	 * @return the connection type
  	 */
  	public static String getConnectionType(Context context){
  		netInfo(context);
  		return _connectionType;
  	}
  	//Check to see if there is an Internet connection
  	/**
  	 * Gets the connection status.
  	 *
  	 * @param context the context
  	 * @return the connection status
  	 */
  	public static Boolean getConnectionStatus(Context context) {
  		netInfo(context);
  		return _connected;
  	}
  	//Retrieve network info.
  	/**
  	 * Net info.
  	 *
  	 * @param context the context
  	 */
  	private static void netInfo(Context context){
  		ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
  		NetworkInfo nInfo = cManager.getActiveNetworkInfo();
  		if(nInfo != null){
  			
  			if(nInfo.isConnected()){
  				_connectionType = nInfo.getTypeName();
  				_connected = true;
  				
  			}else{
  				_connected = false;
  			}
  			
  		}
  	}
  	
  	/**
  	 * Gets the url string response.
  	 *
  	 * @param url the url
  	 * @return the url string response
  	 */
  	public static String getUrlStringResponse(URL url){
  		String response = "";
  		
  		try{
  			URLConnection connection = url.openConnection();
  			BufferedInputStream bin = new BufferedInputStream(connection.getInputStream());
  			
  			byte[] contentBytes = new byte[1024];
  			int bytesRead = 0;
  			StringBuffer responseBuffer = new StringBuffer();
  			
  			while((bytesRead = bin.read(contentBytes)) != -1){
  				
  				response = new String(contentBytes,0,bytesRead);
  				
  				responseBuffer.append(response);
  				
  			}
  			return responseBuffer.toString();
  		}catch (Exception e){
  			Log.e("URL RESPONSE ERROR","getURLStringResponse");
  		}
  		
  		return response;
  	}
  	private void getLocations(String dessert, String zipCode){
		String baseUrl = "http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=qJIjRlbV34GJZfg2AwqSWVV03eeg8SpTQKy5PZqSfjlRrItt5hS2n3PIysdPU_CCIQlCGXIGjoTDESp3l42Ueic3O1EaYXU-&query="+dessert+"&zip="+zipCode+"&results=1&output=json";
		URL finalURL;
		try{
			finalURL = new URL(baseUrl);
			LocationRequest lr = new LocationRequest();
			lr.execute(finalURL);
			Log.i("URL ", baseUrl);
			
		}catch(MalformedURLException e){
			Log.e("BAD URL","MALFORMED URL");
			finalURL = null;
		}
	}
	
	//get results
	private class LocationRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			
			for(URL url: urls){
				
				response = getUrlStringResponse(url);
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
			try{
				JSONObject json = new JSONObject(result);
				JSONObject locations = json.getJSONObject("ResultSet");
				if(locations.getString("totalResultsAvailable").compareTo("0")==0){
					_toast = Toast.makeText(_context, "No Results" , Toast.LENGTH_SHORT);
					_toast.show();
				}else{
					JSONObject location = locations.getJSONObject("Result");
					if(location != null){
						_toast = Toast.makeText(_context, "Saving File.", Toast.LENGTH_SHORT);
						_toast.show();
						_oldLocation.put("Title",  location.getString("Title"));
						_oldLocation.put("Address", location.getString("Address"));
						_oldLocation.put("City", location.getString("City"));
						_oldLocation.put("State", location.getString("State"));
						_oldLocation.put("Phone", location.getString("Phone"));
						_oldLocation.put("Coords", location.getString("Latitude")+","+location.getString("Longitude"));
						
						
						
						//Save File
						FileInterface.storeObjectFile(_context, "oldLocation", _oldLocation, false);
						//Show data
						
					}else{
						_toast = Toast.makeText(_context, "Something went wrong" , Toast.LENGTH_SHORT);
						_toast.show();
					}
				}
				
			}catch(JSONException e){
				Log.e("JSON", "JSON OBJECT EXCEPTION");
			}
			
		}
	}
}
