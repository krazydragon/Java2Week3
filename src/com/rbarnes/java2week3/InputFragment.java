package com.rbarnes.java2week3;







import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;


public class InputFragment extends Fragment implements OnClickListener {
	private InputListener listener;
	
	public interface InputListener{
		public void onDessertSearch(String zipCode, String category);
		public void addZip();
		
	}
	// onCreate
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	   }

	   // onActivityCreated
	   @Override
	   public void onActivityCreated(Bundle savedInstanceState) {
	      super.onActivityCreated(savedInstanceState);
	   }
	   
	   // onCreateView
	   @Override
	   public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		   RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.fragment_input,container,false);
		 //Detect form elements
			Button cookieButton = (Button)view.findViewById(R.id.cookieButton);
			Button pieButton = (Button)view.findViewById(R.id.pieButton);
			Button cakeButton = (Button)view.findViewById(R.id.cakeButton);
			Button candyButton = (Button)view.findViewById(R.id.candyButton);
			
			//Detect button click
			cookieButton.setOnClickListener(this);
			pieButton.setOnClickListener(this);
			cakeButton.setOnClickListener(this);
			candyButton.setOnClickListener(this);

		 
			
			
			
	      
	      return view;
	   }
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		ImageView dessertView = (ImageView)getActivity().findViewById(R.id.dessert_view);
		switch(v.getId()){
		
		//Change image when button is pressed	
		case R.id.cookieButton:
			dessertView.setImageResource(R.drawable.cookies);
			break;
		case R.id.pieButton:
			dessertView.setImageResource(R.drawable.pies);
			break;
		case R.id.cakeButton:
			dessertView.setImageResource(R.drawable.cakes);
			break;
		case R.id.candyButton:
			dessertView.setImageResource(R.drawable.candy);
			break;
		
		
		}
	}
		@Override
		public void onAttach(Activity activity){
			super.onAttach(activity);
			try{
				listener = (InputListener)activity;
			}catch(ClassCastException e){
				throw new ClassCastException(activity.toString() + "did not Implemnt Input Listener!");
			}
		}	 

	   
	   
	   

	   

  

}
