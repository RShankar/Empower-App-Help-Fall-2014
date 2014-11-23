package com.FAU.help;


//import com.parse.Parse;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends FragmentActivity {
String Token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
       SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Token = pref.getString("key", null);
        
    }

    

    public void onClick(View v) 
	   {
    	
		 switch (v.getId())
	    	{
		 case R.id.LTV:
			 Intent i = new Intent(this, Map.class);/*should go to map*/
	           startActivity(i);
			 break;
			 
		 case R.id.LFV:
			 if (Token!=null){
			        try {
						ParseUser.become(Token);
						ParseUser currentUser = ParseUser.getCurrentUser();
					     if(currentUser!=null)
					     {
					    	 Intent q = new Intent(this, UserHome.class);
					         startActivity(q);
					         break;
					     }
					     }
			        catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								}
			 }
			Intent j = new Intent(this,Signin.class);
	        startActivity(j);
			 break;
	    	
	   }
}}
