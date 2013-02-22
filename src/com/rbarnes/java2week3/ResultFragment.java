package com.rbarnes.java2week3;






import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

public class ResultFragment  extends Fragment{
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_result, container, false);
		
		return view;
	}
	
	

}