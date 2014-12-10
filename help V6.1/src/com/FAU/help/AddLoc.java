package com.FAU.help;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddLoc extends ActionBarActivity {
Calendar c = new GregorianCalendar();
Date date, Sdate;
Date today = c.getTime(); 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addloc);
        
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String Token = pref.getString("key", null);
        if (Token==null){
        ParseUser currentUser = ParseUser.getCurrentUser();
        
		 if(currentUser==null)
		 {
			 Intent i = new Intent(this, Signin.class);
		     startActivity(i);
		 }
		}

        Spinner spinner = (Spinner) findViewById(R.id.category);
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
     R.array.categoryA, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     spinner.setAdapter(adapter);
     
     Spinner states = (Spinner) findViewById(R.id.States);
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapterstate = ArrayAdapter.createFromResource(this,
     R.array.StatesA, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     states.setAdapter(adapterstate);
     
     Spinner am = (Spinner) findViewById(R.id.ampm);
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapteram = ArrayAdapter.createFromResource(this,
     R.array.time, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapteram.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     am.setAdapter(adapteram);
     
     Spinner pm = (Spinner) findViewById(R.id.pmam);
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapterpm = ArrayAdapter.createFromResource(this,
     R.array.time, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapterpm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     pm.setAdapter(adapterpm);
     
     getWindow().setSoftInputMode(
     	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
     
     
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vcloggedin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.SignOut) {
        	ParseUser.logOut();
			 SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			 Editor editor = pref.edit();
			 editor.clear();
			 editor.commit();
        	Intent i = new Intent(this, MainActivity.class);
	        startActivity(i);
            return true;
        }
        if (id == R.id.Home) 
        {
        	Intent a = new Intent(this, UserHome.class); 
	           startActivity(a);
        }
        if (id == R.id.addloc) 
        {
        	Intent h = new Intent(this, AddLoc.class); 
	         startActivity(h);
        }
        if (id == R.id.myevent) 
        {
        	Intent b = new Intent(this, MyEvents.class); 
	           startActivity(b);
        }
        if(id == R.id.map)
        {
        	Intent m = new Intent(this, Map.class); 
	           startActivity(m);
        }
        return super.onOptionsItemSelected(item);
    }
    
 
    public void onClick(View v) throws IOException, ParseException 
	   {
    	
    		String Eventname,StartDate,EndDate,open, close,address,city,Zip,message ="null";
			int age =0;
    		int fix =0;
    		
    		EditText et = (EditText) findViewById(R.id.eventname);
    		Eventname = et.getText().toString();
    		int sizename = Eventname.length();
    	
    		 et = (EditText) findViewById(R.id.startdate);
    		 StartDate = et.getText().toString();
    		 if(StartDate.equals("Permanent Event")==false){
    		 if((StartDate.contains("/")==false) ||(StartDate.length()!=10))
    		 {
    			 message = "Please enter valid date using format";
    			 fix=1;
    		 }
    		 else
    		 {
    		 Sdate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(StartDate);
    		 if (Sdate.before(today))
    		 {
    			 message = "Event can't start on or before today";
    			 fix=1;
    		 }
    		 }
    		 }
    		 
    		 et = (EditText) findViewById(R.id.enddate);
    		 EndDate = et.getText().toString();
    		 if(EndDate.equals("Permanent Event")==false){
        		 if((EndDate.contains("/")==false) ||(EndDate.length()!=10))
        		 {
        			 message = "Please enter valid date using format";
        			 fix=1;
        		 }
        		 else
        		 {
        		 date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(EndDate);
        		 }
        		 }
    		  
    		 if(Sdate!=null && date!=null){
        		 if (Sdate.after(date))
        		 {
        			 message = "Event can't end before it starts";
        			 fix=1;
        		 }}
    		 et = (EditText) findViewById(R.id.StartTime);
    		 open = et.getText().toString();
    		 if((open.contains(":")==false) ||(open.length()>5)||(open.length()<=3))
    		 {
    			 message = "Please enter valid time formated Hr:mm";
    			 fix=1;
    		 }
    		 
    		 et = (EditText) findViewById(R.id.endtime);
    		 close = et.getText().toString();
    		 if((close.contains(":")==false) ||(close.length()>5)||(close.length()<=3))
    		 {
    			 message = "Please enter valid time formated Hr:mm";
    			 fix=1;
    		 }
    		 
     		Spinner am = (Spinner) findViewById(R.id.ampm);
     		String am1 = am.getSelectedItem().toString();
     		
     		Spinner pm = (Spinner) findViewById(R.id.pmam);
     		String pm1 = pm.getSelectedItem().toString();
     		if((am1.equals("PM")&& pm1.equals("AM")))
     		{
     			message ="Opening time must be before closing time";
     			fix=1;
     		}
     		
    		 et = (EditText) findViewById(R.id.StreetAddress);
    		 address = et.getText().toString();
    		 int sizeaddress = address.length();
    		 
    		 et = (EditText) findViewById(R.id.eventCity);
    		 city = et.getText().toString();
    		 int sizecity = city.length();
    		 

    		 
    		 et = (EditText) findViewById(R.id.eventZip);
    		 Zip = et.getText().toString();
    		 int ziplength = Zip.length();
    	    	if(ziplength!=5)
    	    	{
    	    		message="Zipcode must be 5 digits long";
    	    		fix=1;
    	    	}
    		 et = (EditText) findViewById(R.id.Agereq);
    		 age=0;
    		 String c = et.getText().toString();
    		 if (c.matches("[0-9]+") && c.length() >=1)
    		 {
    		 age = Integer.parseInt(et.getText().toString());
    		 }
    		if( age<1 ||age>21)
    		{
    			message = "Please pick an appropriate age between 1-21";
    			fix=1;
    		}
    		 
		
    		Spinner spinner = (Spinner) findViewById(R.id.category);
    		String category = spinner.getSelectedItem().toString();
    		
    		int k = spinner.getSelectedItemPosition();
    		
    		if (k==0)
    		{
    			message = "Please choose a category";
    			fix=1;
    		}
    		
    		Spinner state = (Spinner) findViewById(R.id.States);
    		String State = state.getSelectedItem().toString();
    		
    		
    		
    		int l = state.getSelectedItemPosition();
    		if (l ==0)
    		{
    			message = "Please choose a State";
    			fix=1;
    		}
    		
    		if(sizename<2 || sizeaddress<3 || sizecity<2)
    		{
    			fix=1;
    			message="Please fill in all fields";
    			
    		}
    		
    		
    		double latitude = 0;
        	double longitude = 0;
        	String fullAddress ="a";
        	fullAddress= address+" "+city+" "+State+" "+Zip;
        	Geocoder geocoder = new Geocoder(this);  
        	List<Address> Eaddress;
        	Eaddress = geocoder.getFromLocationName(fullAddress, 1);
        	if(Eaddress.size() > 0) {
        	     latitude= Eaddress.get(0).getLatitude();
        	     longitude= Eaddress.get(0).getLongitude();
        	}
        	
        	if(latitude==0||longitude==0)
        	{
        		fix=1;
        		message="Address Not found"+"      "+fullAddress;
        	}
    		
    		if (fix==1)
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
    		
    else
    {
    	ParseUser currentUser = ParseUser.getCurrentUser();
    	String Email = currentUser.getEmail();
    	
    	
    	ParseGeoPoint point = new ParseGeoPoint(latitude,longitude);
    	ParseObject Events = new ParseObject("Events");
    	Events.put("EventName", Eventname);
    	if((Sdate==null)||(date==null))
    	{
    		Calendar s = new GregorianCalendar();
    		s.set(3000, 1, 1);
    		Sdate = s.getTime();
    		date = s.getTime();
    	}
    	Events.put("StartDate", Sdate);
    	Events.put("EndDate", date);
    	
    	Events.put("open", open);
    	Events.put("openampm", am1);
    	Events.put("close", close);
    	Events.put("closeampm", pm1);
    	Events.put("Category", category);
    	Events.put("Age", age);
    	Events.put("StreetAddress", address);
    	Events.put("City", city);
    	Events.put("State", State);
    	Events.put("Zip", Zip);
    	Events.put("User", Email);
    	Events.put("location", point);
    	
    	Events.saveInBackground();
    	Intent i = new Intent(this, Map.class); /*should go to map*/
	    startActivity(i);
    }
			
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

