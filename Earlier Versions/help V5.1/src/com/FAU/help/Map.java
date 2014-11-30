package com.FAU.help;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Map extends ActionBarActivity {
   String Username; 
    private GoogleMap mMap;
    Marker mMarker;
    ParseGeoPoint point = new ParseGeoPoint(0,0);
    String eventName, myMap;
    Location mCurrentLocation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        
        Intent i = getIntent();
        myMap = i.getStringExtra("Mymap");
        
        
        
        ParseQuery<ParseObject> Events = ParseQuery.getQuery("Events");
        if (myMap!=null){
        	ParseUser currentUser = ParseUser.getCurrentUser();
            Username = currentUser.getEmail();
        Events.whereEqualTo("User", Username);}
        Events.findInBackground(new FindCallback<ParseObject>() {
    
            public void done(List<ParseObject> locList, ParseException e) {
            	
                if (e == null) {
                
                    Log.d("location", "Retrieved " + locList.size() + " loc");
                    try {
                    	int i=0;
                    	while(i<locList.size())
                    	{
                    		point=locList.get(i).getParseGeoPoint("location");
                    		eventName = locList.get(i).getString("EventName");
                    		setUpMap();
                    		i++;
                    	}
                    	
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    //showad();
                   
                } else {
                    Log.d("location", "Error: " + e.getMessage());
                  
                }
                
            }
            
        });
       
      
        
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
        	if(myMap==null){
        	Intent a = new Intent(this, MainActivity.class); 
	           startActivity(a);
        	}
        	else
        	{
        		Intent b = new Intent(this, UserHome.class); 
 	           startActivity(b);
        	}
        }
        if (id == R.id.filter) 
        {
        	
        }
      
        return super.onOptionsItemSelected(item);
    }
    
    public void showad(){
    	new AlertDialog.Builder(this)
	    .setTitle("Check your Input")
	    
	    .setMessage("")
	   
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
        setUpMapIfNeeded();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

   
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                try {
					setUpMap();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }

    
    private void setUpMap() throws IOException {
    	double latitude = 0;
    	double longitude = 0;
    	String fullAddress ="4855 NW 96th drive coral springs fl 33076";
    	Geocoder geocoder = new Geocoder(this);  
    	List<Address> addresses;
    	addresses = geocoder.getFromLocationName(fullAddress, 1);
    	if(addresses.size() > 0) {
    	     latitude= addresses.get(0).getLatitude();
    	     longitude= addresses.get(0).getLongitude();
    	}
    	
    	latitude =point.getLatitude();
    	longitude=point.getLongitude();
    	
    	if (latitude !=0 && longitude != 0)
    	{
    		mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(eventName));
    	}
    	
    	
    	final CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(latitude, longitude))      // Sets the center of the map to Mountain View
        .zoom(10)                   // Sets the zoom
        .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
