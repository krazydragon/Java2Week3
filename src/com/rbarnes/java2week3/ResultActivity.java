/*
 * project	Java2Week3
 * 
 * package	com.rbarnes.java2week3
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Feb 19, 2013
 */
package com.rbarnes.java2week3;

import java.util.HashMap;


import com.rbarnes.lib.FileInterface;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends FragmentActivity {

	
	
	Context _context;
	HashMap<String, String> _resultValues;
	String _titleStr;
	String _addressStr;
	String _cityStr;
	String _stateStr;
	String _phoneStr;
	String _mapCoord;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		_context = this;
		
		_resultValues = getOldLocation();
		//Display results
		displayResults();
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, String> getOldLocation(){
		Object stored = null;
		stored = FileInterface.readObjectFile(_context, "oldLocation", false);
		HashMap<String, String> oldLocation;
		if(stored == null){
			Log.i("OLD LOCATION", "NO OLD LOCATION FILE FOUND");
			oldLocation = null;
		}else{
			oldLocation = (HashMap<String, String>) stored;
		}
		return oldLocation;
	}
	private void displayResults(){
		//ImageView resultImageView = (ImageView)findViewById(R.id.resultImageView);
		//Bundle resultIntent = getIntent().getExtras();
		//String imageText = resultIntent.getString("Picture");
		
		
		
		((TextView)findViewById(R.id.titleValue)).setText(_resultValues.get("Title").toString());
		((TextView)findViewById(R.id.addressValue)).setText(_resultValues.get("Address").toString());
		((TextView)findViewById(R.id.cityValue)).setText(_resultValues.get("City").toString());
		((TextView)findViewById(R.id.stateValue)).setText(_resultValues.get("State").toString());
		_phoneStr = "tel:" + (_resultValues.get("Phone").toString());
		_mapCoord = "geo:" + (_resultValues.get("Coords").toString() + "?z=50");
		
		
		
		/*if(imageText == "cookies"){
			resultImageView.setImageResource(R.drawable.cookies);	
		}else if(imageText == "candy"){
			resultImageView.setImageResource(R.drawable.candy);
		}else if(imageText == "pies"){
			resultImageView.setImageResource(R.drawable.pies);	
		}else if(imageText == "cakes"){
			resultImageView.setImageResource(R.drawable.cakes);
		}*/
	}
}