package com.FAU.help;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class StudentSignup extends ActionBarActivity {
	String school, studentnum,confirmpass,password,email,name, Message ="a";
    int size=1;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentsignup);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        getWindow().setSoftInputMode(
        	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
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
    
    public void onClick(View v) throws IOException 
	   {
    	size =1;
    	
    	EditText et = (EditText) findViewById(R.id.pass);
    	password = et.getText().toString();
    	
    	et = (EditText) findViewById(R.id.passcon);
    	confirmpass = et.getText().toString();
    	
    	if(password.equals(confirmpass)==false)
    	{
    		Message = "Passwords do not match";
    		size=0;
    	}
    	
    	int size0=password.length();
    	if (size0<3)
    	{Message="Password must be at least 3 characters";}
    	
    	et = (EditText) findViewById(R.id.email);
    	email = et.getText().toString();
    	if(emailValidator(email) == false)
    	{
    	Message ="Please enter a valid email address";
    	size=0;
    	}
    	
    	et = (EditText) findViewById(R.id.Full);
    	name = et.getText().toString();
    	boolean m = name.contains(" ");
    	if(m == false)
    	{
    	Message ="Please enter a first and last name";
    	size=0;
    	}
    	
    	et = (EditText) findViewById(R.id.schoolname);
    	school = et.getText().toString();
    	
    	et = (EditText) findViewById(R.id.studentnum);
    	studentnum = et.getText().toString();
    	checkID();
    	
    	 if((size0<3)||(size==0))
         { 
         	showad();
         }
    	 else
    	 {
    		 setinfo();
    	 }

	   }
    	
    	
	 
        
       
			 	   
    public void setinfo()
    {
    	ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
          
        // other fields can be set just like with ParseObject
        user.put("AccountType", "V");
        user.put("FullName", name);
        user.put("SchoolName", school );
        user.put("StudentNum", studentnum );
        
        
        user.signUpInBackground(new SignUpCallback() {
          public void done(ParseException e) {
            if (e == null) {
              // Hooray! Let them use the app now.
            } else {
              // Sign up didn't succeed. Look at the ParseException
              // to figure out what went wrong
            }
          }
        });
        
    	Intent j = new Intent(this, Signin.class);
    	j.putExtra("Volunteer", "V");
	         startActivity(j);
   }
    
    public void  restartpage()
    {
    	Intent re = new Intent(this, StudentSignup.class);
        startActivity(re);
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
	    
	    .setMessage(Message)
	   
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
    
    public void checkID() 
    {
    	ParseQuery<ParseUser> query = ParseUser.getQuery();
    	query.whereEqualTo("StudentNum", studentnum);
    	List<ParseUser> objects = null;
		try {
			objects = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if (objects.size()>0)
    	{
    		size=0;
    		Message ="This Student has already signed up";
    	}
    	
    	
}}