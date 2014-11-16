package com.FAU.help;

import com.parse.Parse;
import com.parse.ParseException;
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
import android.widget.EditText;

public class Signup extends ActionBarActivity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        
    
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
    	String password,orgname,orgaddress,city, email,name,state,Zip,phone,web,Message ="a";
    	
    	EditText et = (EditText) findViewById(R.id.SignUpPass);
    	password = et.getText().toString();
    	int size0=password.length();
    	if (size0<3)
    	{Message="Password must be at least 3 characters";}
    	et = (EditText) findViewById(R.id.emailsignup);
    	email = et.getText().toString();
    	
    	boolean k = email.contains("@");
    	boolean l = email.contains(".com");
    	if((k == false)||(l == false))
    	{
    	Message ="Please enter a valid email address";
    	}
    	et = (EditText) findViewById(R.id.FullName);
    	name = et.getText().toString();
    	boolean m = name.contains(" ");
    	if(m == false)
    	{
    	Message ="Please enter a first and last name";
    	}
    	
    	et = (EditText) findViewById(R.id.OrgName);
    	orgname = et.getText().toString();
    	int size1= orgname.length();
    	
    	et = (EditText) findViewById(R.id.OrgAddress);
    	orgaddress = et.getText().toString();
    	int size2= orgaddress.length();
    	
    	et = (EditText) findViewById(R.id.City);
    	city = et.getText().toString();
    	int size3= city.length();
    	
    	et = (EditText) findViewById(R.id.State);
    	 state = et.getText().toString();
    	 int size4= state.length();
    	 
    	et = (EditText) findViewById(R.id.Zip);
    	 Zip = et.getText().toString();
    	int ziplength = Zip.length();
    	if(ziplength!=5)
    	{
    		Message="Zipcode must be 5 digits long";
    	}
    	et = (EditText) findViewById(R.id.Phone);
    	 phone = et.getText().toString();
    	 int size5= phone.length();
    	 if (size5!=10)
    	 {
    		 Message="Please enter a vaild 10-digit number";
    	 }
    	 int size=1;
    	 if(size1<2||size2<2||size3<2||size4<2||size5<2)
    	 {
    		 Message="Please Fill in all required fields";
    		 size=0;
    	 }
    	 
    	et = (EditText) findViewById(R.id.Web);
    	 web = et.getText().toString();
    	
    	 
        
        if((size0<3)||(ziplength!=5)||(k == false)||(l == false)||(m==false)||(size==0)||(size5!=10))
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
			 	   
    else
    {
    	ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
          
        // other fields can be set just like with ParseObject
        user.put("AccountType", "VC");
        user.put("FullName", name);
        user.put("OrgName", orgname);
        user.put("OrgAddress", orgaddress);
        user.put("OrgCity", city);
        user.put("OrgState", state);
        user.put("OrgZip", Zip);
        user.put("phone", phone);
        user.put("OrgWeb", web);
        
        
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
	         startActivity(j);}
   }
}