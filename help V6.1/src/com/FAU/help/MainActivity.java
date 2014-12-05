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
String Token, Volunteer ="Volunteer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
       SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Token = pref.getString("key", null);
        if (Token!=null){
	        try {
				ParseUser.become(Token);
				ParseUser currentUser = ParseUser.getCurrentUser();
				
			     if(currentUser!=null)
			     {
			    	 String type = currentUser.getString("AccountType");
					 if (type.equals("VC"))
					 {
					 Intent i = new Intent(this, UserHome.class);
			         startActivity(i);
			         
					 }
					 
					 else if (type.equals("V"))
					 {
					 Intent i = new Intent(this, StudentHome.class);
			         startActivity(i);
						 
					 }
			     }}
	        catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
	 }
        
    }

    

    public void onClick(View v) 
	   {
    	
		 switch (v.getId())
	    	{
		 case R.id.LTV1:
			 Intent i = new Intent(this, Signin.class);
			 i.putExtra(Volunteer, "V");
	         startActivity(i);
			 break;
			 
		 case R.id.LFV1:
			 
			Intent j = new Intent(this,Signin.class);
			j.putExtra(Volunteer, "VC");
	        startActivity(j);
			 break;
			 
		 case R.id.button:
			 Intent qw = new Intent(this,Leaderboard.class);
		        startActivity(qw);
				 break;
	    	
	   }
}}
