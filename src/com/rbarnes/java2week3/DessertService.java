package com.rbarnes.java2week3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DessertService extends Service{

	 @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
		 Toast.makeText(this,"This service works :)", Toast.LENGTH_LONG).show();
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
		Log.d("service", "FirstService started");
		this.stopSelf();
	}
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("service", "FirstService destroyed");
    }
	
	
}
