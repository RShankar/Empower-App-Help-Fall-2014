package com.FAU.help;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Signup extends ActionBarActivity {
	String irsstate, irscity, irsname="";
	String tax,confirmpass,password,orgname,orgaddress,city, email,name,state,taxID,Zip,phone,web,Message ="a";
    int size=1;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        
        Spinner states = (Spinner) findViewById(R.id.Statesign);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterstate = ArrayAdapter.createFromResource(this,
        R.array.StatesA, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        states.setAdapter(adapterstate);
        
        
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
    	EditText et = (EditText) findViewById(R.id.SignUpPass);
    	password = et.getText().toString();
    	
    	et = (EditText) findViewById(R.id.confirmpass);
    	confirmpass = et.getText().toString();
    	if(password.equals(confirmpass)==false)
    	{
    		Message = "Passwords do not match";
    		size=0;
    	}
    	int size0=password.length();
    	if (size0<3)
    	{Message="Password must be at least 3 characters";}
    	et = (EditText) findViewById(R.id.emailsignup);
    	email = et.getText().toString();
    	
    	boolean k = emailValidator(email);
    	
    	if(k == false)
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
    	
		 et = (EditText) findViewById(R.id.taxID);
		 taxID = et.getText().toString();
		 int taxIDlength = taxID.length();
    	
    	et = (EditText) findViewById(R.id.City);
    	city = et.getText().toString();
    	int size3= city.length();
    	
    	et = (EditText) findViewById(R.id.Web);
   	 	web = et.getText().toString();
    	
   	 	Spinner QState = (Spinner) findViewById(R.id.Statesign);
		state = QState.getSelectedItem().toString();
    	//int size4= state.length();
    	 
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
    	 
    	double latitude = 0;
     	double longitude = 0;
     	String fullAddress ="a";
     	fullAddress= orgaddress+" "+city+" "+state+" "+Zip;
     	Geocoder geocoder = new Geocoder(this);  
     	List<Address> Eaddress;
     	Eaddress = geocoder.getFromLocationName(fullAddress, 1);
     	if(Eaddress.size() > 0) {
     	     latitude= Eaddress.get(0).getLatitude();
     	     longitude= Eaddress.get(0).getLongitude();
     	}
     	
     	if(latitude==0||longitude==0)
     	{
     		size=0;
     		Message="Address Not found"+"      "+fullAddress;
     	}
     	
    	 if(size1<2||size2<2||size3<2||size5<2)
    	 {
    		 Message="Please Fill in all required fields";
    		 size=0;
    	 }
    	 else if((taxIDlength!=9) /*&& (taxIDlength!=0)*/)  //only allowing organizations to register (for now?)
 		{
 			size=0;
 			Message="Please fill in a valid 9-digit organization taxID number"+taxIDlength;
 			//if(taxIDlength==0)
 			//{
 			//	setinfo();
 			//}
 		}
    	 checkID();
    	 if((size0<3)||(ziplength!=5)||(k == false)||(m==false)||(size==0)||(size5!=10))
         { 
         	showad();
         }
    	 

    	 
     
 		else
 		{
 			ParseQuery<ParseObject> go = ParseQuery.getQuery("go");
 			go.whereEqualTo("Parse_000262650", taxID);
 			
 			go.findInBackground(new FindCallback<ParseObject>() {
 			    
 	            public void done(List<ParseObject> taxList, ParseException e) {
 	            	
 	                if (e == null) {
 	                	
 	                
 	                    Log.d("location", "Retrieved " + taxList.size() + " loc");
 	                    if (taxList.size()>0)
 	                    {
 	                
 	                    irsname = taxList.get(0).getString("theSocietyOfStVincentDePaulOfMemphisInc");
 	                    irscity= taxList.get(0).getString("Memphis");
 	                    irsstate = taxList.get(0).getString("TN");
 	                    if ( irsstate.compareTo(state) ==0)
 	                    {
 	                    setinfo();
 	                    }
 	                    else
 	                    {
 	                    	Message ="Your TaxID doesn't match your address";
 	                    	
 	                    	showad();
 	                    }
 	                    }
 	                    else
 	                    {
 	                    	 Message ="Your taxID is invalid";
 	                    	 showad();
 	                    }
 	                    }
 	                   
 	                
 	                else 
 	                {
 	                	Message ="Whoops something went wrong, page will reload";
 	                	showad();
 	                	restartpage();
 	                }
 	                
 	            }
 	            
 	        });
 			
 		}
 		}
    	
    	
	 
        
       
			 	   
    public void setinfo()
    {
    	ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
          
        // other fields can be set just like with ParseObject
        user.put("AccountType", "VC");
        user.put("FullName", name);
        user.put("OrgName", irsname);
        user.put("OrgAddress", orgaddress);
        user.put("OrgCity", irscity);
        user.put("OrgState", irsstate);
        user.put("OrgZip", Zip);
        user.put("phone", phone);
        user.put("OrgWeb", web);
        user.put("TaxID", taxID);
        
        
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
	         startActivity(j);
   }
    public void  restartpage()
    {
    	Intent re = new Intent(this, Signup.class);
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
    	query.whereEqualTo("TaxID", taxID);
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
    		Message ="Your organization has already signed up";
    	}
    	
    	
}}