package com.FAU.help;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.RequestPasswordResetCallback;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Passreset extends ActionBarActivity {
	
	String message;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passreset);
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
        message="After entering your email, an email with instructions to reset your password will be sent to you";
        showad();
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.SignOut) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void onClick(View v) 
	   {
		    	
		     EditText edit = (EditText) findViewById(R.id.emailreset);
		     String email = edit.getText().toString();
		     if (emailValidator(email)==true)
		     {
		    	 ParseUser.requestPasswordResetInBackground(email,
                         new RequestPasswordResetCallback() {
		    		 public void done(ParseException e) {
		    			 if (e == null) {
		    				 // An email was successfully sent with reset instructions.
		    				 next();
		    			 } else {
		    				 // Something went wrong. Look at the ParseException to see what's up.
		    				 message ="Sorry, we could not find that email in our records";
		    		    	 
		    		    	 showad();
		    				 
		    			 }
		    		 }
		    	 });
		    	 
		    	
		     }
		      
		     else
		     { 
		    	 message ="Please enter a valid email address";
		    	 showad();
		     }
			 
			 
		
			
	    	}
    
    public void next()
    {
    	Intent j = new Intent(this,Signin.class);
	     startActivity(j); 
    }
    public boolean emailValidator(String email) 
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
	   

public void showad()
{
	new AlertDialog.Builder(this)
    .setTitle("Check your Input")
    
    .setMessage(message)
   
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
}