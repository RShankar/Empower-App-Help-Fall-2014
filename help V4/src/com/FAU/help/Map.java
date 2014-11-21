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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class Map extends FragmentActivity {
    
    private GoogleMap mMap;
    Marker mMarker;
    ParseGeoPoint point = new ParseGeoPoint(0,0);
    String eventName;
    Location mCurrentLocation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        
        ParseQuery<ParseObject> Events = ParseQuery.getQuery("Events");
   
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
