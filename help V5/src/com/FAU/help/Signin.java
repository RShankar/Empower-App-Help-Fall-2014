package com.FAU.help;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Signin extends FragmentActivity {
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String Token = pref.getString("key", null);
        if (Token!=null){
        try {
			ParseUser.become(Token);
			ParseUser currentUser = ParseUser.getCurrentUser();
		     if(currentUser!=null)
		     {
		    	 Intent i = new Intent(this, UserHome.class);
		         startActivity(i);
		     }
		     }
		     
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
		 case R.id.signin:
			 ParseUser.logOut();
			 
			 EditText edit = (EditText) findViewById(R.id.SignInPass);
		     String password = edit.getText().toString();
		    	
		     edit = (EditText) findViewById(R.id.SignInUser);
		     String email = edit.getText().toString();
		     
		    
			 try {
				ParseUser.logIn(email, password);
			} 
			 catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}	  
		     
			 ParseUser currentUser = ParseUser.getCurrentUser();
			 if (currentUser != null) {
				  // do stuff with the user
				 SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
				 Editor editor = pref.edit();
				 editor.clear();
				 editor.commit();
				 String Token = currentUser.getSessionToken();
				 editor.putString("key", Token); // Storing string
				 editor.commit();
				 pref.getString("key", null);
				 Intent i = new Intent(this, UserHome.class);
		         startActivity(i);
				} else {
				  // show the signup or login screen
				}
			 
			 break;
		 case R.id.createacc:
			Intent j = new Intent(this,Signup.class);
	        startActivity(j);
			 break;
			 
		 case R.id.passreset:
			 Intent reset = new Intent(this, Passreset.class);
			 startActivity(reset);
	    	}
	   }
}
