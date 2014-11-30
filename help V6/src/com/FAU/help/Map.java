package com.FAU.help;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Map extends ActionBarActivity
implements
ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener,
OnItemSelectedListener {
	
	Date mydate, eventdate, enddate;
    
	String msg, Username, category, age, radius, timeO, timeC;
   	int i =0, radiusnum=0;
   	int agenum=0;
    private GoogleMap mMap;
    Marker mMarker;
    ParseGeoPoint point = new ParseGeoPoint(0,0);
    ParseGeoPoint currgeo = new ParseGeoPoint(0,0);
    double curLat=0;
    double curLong=0;
    String eventName, myMap, address;
    Location mCurrentLocation;
    ParseUser currentUser;
    
    
    private GoogleApiClient mGoogleApiClient;


    // These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        date();
        getWindow().setSoftInputMode(
        	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent i = getIntent();
        myMap = i.getStringExtra("Mymap");
        setupspinner();
        setUpGoogleApiClientIfNeeded();
        mGoogleApiClient.connect();
        currentUser = ParseUser.getCurrentUser();
      
        
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
       
      
        return super.onOptionsItemSelected(item);
    }
    
    public void showad(){
    	new AlertDialog.Builder(this)
	    .setTitle("Check your Input")
	    
	    .setMessage(msg+curLat+curLong)
	   
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
        setUpGoogleApiClientIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
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
					mMap.setMyLocationEnabled(true);
	            
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }

    private void setUpGoogleApiClientIfNeeded() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }
    
    /**
     * Button to get current Location. This demonstrates how to get the current Location as required
     * without needing to register a LocationListener.
     */
    public void showMyLocation(View view) {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            String msg = "Location = "
                    + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
  

    /**
     * Callback called when connected to GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                REQUEST,
                this);  // LocationListener
        
        
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Location current = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            curLat = current.getLatitude();
            curLong = current.getLongitude();
            currgeo.setLatitude(curLat);
            currgeo.setLongitude(curLong);
            }
            findlocations();
            camera(11);
            //showad();
    }

    /**
     * Callback called when disconnected from GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        // Do nothing
    }

    /**
     * Implementation of {@link OnConnectionFailedListener}.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do nothing
    }
    @SuppressWarnings("deprecation")
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

    		String start =eventdate.toLocaleString();
    		start = start.substring(0, 12);
    		String end =enddate.toLocaleString();
    		end = end.substring(0,12);
    		
    		if (eventdate.before(mydate)){
    		mMap.addMarker(new MarkerOptions()
    		.position(new LatLng(latitude, longitude))
    		.title(eventName +" " +"("+ start + " to " + end+")")
    		.snippet(timeO +" to "+ timeC + "  @  "+address)
    		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    		}
    		else
    			
    		{
    			mMap.addMarker(new MarkerOptions()
        		.position(new LatLng(latitude, longitude))
        		.title(eventName + " (On going Event)")
        		.snippet(timeO +" to "+ timeC + "  @  "+address)
        		
        		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    		}
    	}
    	
    	

    }


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
		
	}
	
	public void setupspinner()
	{
		Spinner states = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterstate = ArrayAdapter.createFromResource(this,
        R.array.categoryA, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        states.setAdapter(adapterstate);
        states.setOnItemSelectedListener(this);
        
        states = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterstate = ArrayAdapter.createFromResource(this,
        R.array.AgeA, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        states.setAdapter(adapterstate);
        states.setOnItemSelectedListener(this);
        
        states = (Spinner) findViewById(R.id.spinner3);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterstate = ArrayAdapter.createFromResource(this,
        R.array.RadiusA, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        states.setAdapter(adapterstate);
        states.setOnItemSelectedListener(this);
        
        
		
	}
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        //An item was selected. You can retrieve the selected item using
		
         getspinner();
         if((category!=null) || (age!=null) || (radius!=null))
         {
        	
        	 findlocations();
         }
         else
         {
        	 
         }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
	public void getspinner()
	{
		Spinner QState = (Spinner) findViewById(R.id.spinner1);
		category = QState.getSelectedItem().toString();
		if (category.equals("Choose a category")==true){category=null;} 
		
		QState = (Spinner) findViewById(R.id.spinner2);
		age = QState.getSelectedItem().toString();
		agenum=QState.getSelectedItemPosition();
		if (age.equals("Age")==true){age=null;} 
		
		QState = (Spinner) findViewById(R.id.spinner3);
		radius = QState.getSelectedItem().toString();
		
		if (radius.equals("Radius")==true){radius=null; radiusnum=5;}
		else if(radius.equals("1 Mi")){radiusnum=1;}
		else if(radius.equals("2 Mi")){radiusnum=2;}
		else if(radius.equals("5 Mi")){radiusnum=5;}
		else if(radius.equals("10 Mi")){radiusnum=10;}
		else if(radius.equals("25 Mi")){radiusnum=25;}
		else if(radius.equals("50 Mi")){radiusnum=50;}
		else if(radius.equals("100 Mi")){radiusnum=100;}
		
	}
	
	public void findlocations()
	{
		getspinner();
		if (i>0){
		mMap.clear();}
		ParseQuery<ParseObject> Events = ParseQuery.getQuery("Events");
        if (myMap!=null){
        	ParseUser currentUser = ParseUser.getCurrentUser();
            Username = currentUser.getEmail();
        Events.whereEqualTo("User", Username);
        if(radius==null){radiusnum=100;}}
      
        Events.whereWithinMiles("location", currgeo, radiusnum);
        if(age!=null){Events.whereLessThanOrEqualTo("Age", agenum);}
        if(category!=null){Events.whereEqualTo("Category", category);}
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
                    		eventdate = locList.get(i).getDate("StartDate");
                    		enddate = locList.get(i).getDate("EndDate");
                    		String temp1 = locList.get(i).getString("StreetAddress");
                    		String temp2 = locList.get(i).getString("City");
                    		String temp3 = locList.get(i).getString("State");
                    		String temp4 = locList.get(i).getString("Zip");
                    		address = temp1 +" "+ temp2 +" "+ temp3+ ", "+temp4;
                    		timeO = locList.get(i).getString("open") + " " +locList.get(i).getString("openampm");
                    		timeC = locList.get(i).getString("close") + " " +locList.get(i).getString("closeampm");
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
       
      i++;
	}
	
	public void camera (int zoom)
	{
    	final CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(curLat, curLong))      // Sets the center of the map to Mountain View
        .zoom(zoom)                   // Sets the zoom
        .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
	public  void date() {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.YEAR, 3000);
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        mydate = cal.getTime();
    }
}
