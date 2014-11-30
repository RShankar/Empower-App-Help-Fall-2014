package com.FAU.help;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.support.v4.app.FragmentActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Signin extends FragmentActivity {
	String V;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String Token = pref.getString("key", null);
        Intent p = getIntent();
        V = p.getStringExtra("Volunteer");
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
        getWindow().setSoftInputMode(
        	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
				 String type = currentUser.getString("AccountType");
				 if (type.equals("VC"))
				 {
				 Intent i = new Intent(this, UserHome.class);
		         startActivity(i);
		         break;
				 }
				 
				 else if (type.equals("V"))
				 {
				 Intent i = new Intent(this, StudentHome.class);
		         startActivity(i);
					 
				 }
				
			 }
			 else {
				 Toast.makeText(getBaseContext(), "Username and password don't match", Toast.LENGTH_LONG).show();
				  // show the signup or login screen
					}
			 break;
		 case R.id.createacc:
			 if(V.equals("VC"))
			 {
			Intent j = new Intent(this,Signup.class);
	        startActivity(j);
			 break;
			 }
			 else if (V.equals("V"))
			 {
				 Intent sign = new Intent(this,StudentSignup.class);
				 startActivity(sign); 
			 }
			 break;
		 case R.id.passreset:
			 Intent reset = new Intent(this, Passreset.class);
			 startActivity(reset);
			 break;
			 
		 case R.id.button1:
			 Intent g = new Intent(this,Map.class);
		        startActivity(g);
				
	    	}
	   }
    
    public void showad()
    {
    	new AlertDialog.Builder(this)
	    .setTitle("Check your Input")
	    
	    .setMessage(V)
	   
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
    }
    
    @Override
	public void onBackPressed() {
		ParseUser current = ParseUser.getCurrentUser();
		if(current!=null)
		{
		String t = current.getString("AccountType");
		if (t.equals("VC")){
		Intent k = new Intent(this, UserHome.class);
	    startActivity(k);}
		else if (t.equals("V"))
		{
			Intent k = new Intent(this, StudentHome.class);
		    startActivity(k);
		}
		}
		else
		{
			Intent k = new Intent(this, MainActivity.class);
		    startActivity(k);
		}
	}
}
