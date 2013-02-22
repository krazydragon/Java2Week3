package com.rbarnes.java2week3;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;




import com.rbarnes.java2week3.InputFragment.InputListener;
import com.rbarnes.other.Dessert;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements InputListener{

	Context _context;
	ArrayList<Dessert> _desserts;
	HashMap<String, String> _oldLocation;
	EditText _inputField;
	Toast _toast;
	JSONObject _tempLocation;
	Button _inputButton;
	int _buttonId;
	Intent _resulutIntent;
	String _picture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_input_form);
		startService(new Intent(this, DessertService.class));
		_context = this;
		_resulutIntent = new Intent(this, ResultActivity.class);
		_oldLocation = new HashMap<String, String>();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDessertSearch(String zipCode, String category) {
		
		
	}

	@Override
	public void addZip() {
		// TODO Auto-generated method stub
		
	}

}
