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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
	private Cursor cursor;
	private static int counter = 0;
	
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
		 counter++;
		 getLocations("cakes", "98404");
		 counter++;
		 getLocations("candy", "98404");
		 counter++;
		 getLocations("pies", "98404");
		 counter = 0;
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
  	@SuppressWarnings("resource")
	public static Boolean storeStringFile(Context context, String filename, String content, Boolean external){
		try{
			File file;
			FileOutputStream fos;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fos =  new FileOutputStream(file);
			}else{
				fos =  context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			fos.write(content.getBytes());
			fos.close();
			
			
		}catch(IOException e){
			Log.e("WRITE ERROR", filename);
		}
		return true;
	}
	
	/**
	 * Store object file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param content the content
	 * @param external the external
	 * @return the boolean
	 */
	@SuppressWarnings("resource")
	public static Boolean storeObjectFile(Context context, String filename, Object content, Boolean external){
		try{
			File file;
			FileOutputStream fos;
			ObjectOutputStream oos;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fos =  new FileOutputStream(file);
			}else{
				
				fos =  context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			oos =  new ObjectOutputStream(fos);
			oos.writeObject(content);
			oos.close();
			fos.close();
		}catch(IOException e){
			Log.e("WRITE ERROR", filename);
		}
			return true;
		}
	
	/**
	 * Read string file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param external the external
	 * @return the string
	 */
	@SuppressWarnings("resource")
	public static String readStringFile(Context context, String filename, Boolean external){
		String content = "";
		try{
			File file;
			FileInputStream fis;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fis =  new FileInputStream(file);
			}else{
				file = new File(filename);
				fis =  context.openFileInput(filename);
			}
			BufferedInputStream bin = new BufferedInputStream(fis);
			
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			while((bytesRead = bin.read(contentBytes)) != -1){
				
				content = new String(contentBytes,0,bytesRead);
				contentBuffer.append(content);
				
			}
			content = contentBuffer.toString();
			fis.close();
			
			
		}catch(FileNotFoundException e){
			Log.e("READ ERROR","FILE NOT FOUND " + filename);
			return null;
		}catch(IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		
		return content;
	}
	
	/**
	 * Read object file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param external the external
	 * @return the object
	 */
	@SuppressWarnings("resource")
	public static Object readObjectFile(Context context, String filename, Boolean external){
		Object content = new Object();
		try{
			File file;
			FileInputStream fis;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fis =  new FileInputStream(file);
			}else{
				file = new File(filename);
				fis =  context.openFileInput(filename);
			}
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			try{
				content = ois.readObject();
			}catch(ClassNotFoundException e){
				Log.e("READ ERROR","INVALD JAVA OBJECT FILE");
			}
			ois.close();
			fis.close();
			
			
		}catch(FileNotFoundException e){
			Log.e("READ ERROR","FILE NOT FOUND " + filename);
			return null;
		}catch(IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		
		return content;
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
						storeObjectFile(_context, "oldLocation", _oldLocation, false);
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
