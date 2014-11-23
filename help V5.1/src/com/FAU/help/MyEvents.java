package com.FAU.help;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.FAU.help.Map;

public class MyEvents extends ActionBarActivity {
String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myevents);
        ParseUser currentUser = ParseUser.getCurrentUser();
        Username = currentUser.getEmail();
        
        
		 ParseQueryAdapter<ParseObject> adapter =
        	new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                // Here we can configure a ParseQuery to our heart's desire.
            	
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Events");
                query.whereEqualTo("User", Username);
                return query;
              }
            });
        
        adapter.setTextKey("EventName");
        
       
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        
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
    public void onClick(View v) 
	   {
		 
			 Intent i = new Intent(this, Map.class);
			 i.putExtra("Mymap", "1");
	           startActivity(i);
	           
			 
	   
}}
