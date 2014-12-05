package com.FAU.help;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.parse.Parse;
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
import android.widget.AdapterView;
//import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.FAU.help.Map;

public class MyEvents extends ActionBarActivity {
String Username, item;
int x =0;
Calendar c = new GregorianCalendar();
Date today = c.getTime(); 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myevents);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        ParseUser currentUser = ParseUser.getCurrentUser();
        Username = currentUser.getEmail();
        String type = currentUser.getString("AccountType");
        x=0;
        if (type.equals("VC"))
        {
        listclickVC();
        }
        else if (type.equals("V"))
        {
        	TextView view = (TextView) findViewById(R.id.score);
            view.setText("Events Attendend");
            view = (TextView) findViewById(R.id.textView2);
            view.setVisibility(View.GONE);
            ListView lv = (ListView) findViewById(R.id.listView2);
            lv.setVisibility(View.GONE);
            View b = findViewById(R.id.button1);
            b.setVisibility(View.GONE);
        	listclickV();
        }
		 
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
        ParseUser current = ParseUser.getCurrentUser();
		String t = current.getString("AccountType");
		
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
        	if (t.equals("VC")){
        		Intent k = new Intent(this, UserHome.class);
        	    startActivity(k);}
        		else if (t.equals("V"))
        		{
        			Intent k = new Intent(this, StudentHome.class);
        		    startActivity(k);
        		}
        }
        if (id == R.id.addloc) 
        {
        	if (t.equals("VC")){
        		Intent k = new Intent(this, AddLoc.class);
        	    startActivity(k);}
        		
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
	   }
    
public void listclickVC()
{
	final ListView listView = (ListView) findViewById(R.id.listView1);
	ListView listView2 = (ListView) findViewById(R.id.listView2);
	if (x ==0)
	{
	ParseQueryAdapter<ParseObject> adapter =
        	new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                // Here we can configure a ParseQuery to our heart's desire.
            	
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Events");
                query.whereEqualTo("User", Username);
                query.whereGreaterThan("EndDate", today);
                
                return query;
              }
            });
        
        adapter.setTextKey("EventName");
        
        listView.setAdapter(adapter);
        adapter =
            	new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
                public ParseQuery<ParseObject> create() {
                    // Here we can configure a ParseQuery to our heart's desire.
                	
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Events");
                    query.whereEqualTo("User", Username);
                    query.whereLessThan("EndDate", today);
                    
                    return query;
                  }
                });
        adapter.setTextKey("EventName");
        listView2.setAdapter(adapter);
        x=1;
	}
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            	
            	
                ParseObject ob = (ParseObject) listView.getItemAtPosition(position);
                item = ob.getObjectId();
                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                signinsheet();
                
            }
        });
	}
    
public void listclickV()
{
	final ListView listView = (ListView) findViewById(R.id.listView1);
	final ListView listView3 = (ListView) findViewById(R.id.listView3);
	if (x ==0)
	{
	ParseQueryAdapter<ParseObject> adapter =
        	new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                // Here we can configure a ParseQuery to our heart's desire.
            	
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("eventsAtt");
                query.whereEqualTo("User", Username);
                return query;
              }
            });
        
        adapter.setTextKey("EventName");
        
        
        listView.setAdapter(adapter);
        adapter =
            	new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
                public ParseQuery<ParseObject> create() {
                    // Here we can configure a ParseQuery to our heart's desire.
                	
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("eventsAtt");
                    query.whereEqualTo("User", Username);
                    return query;
                  }
                });
            
            adapter.setTextKey("Hrs");
            
            
            listView3.setAdapter(adapter);
        x=1;
	}
        
	}
public void signinsheet()
{
	 Intent i = new Intent(this, SignInSheet.class);
	 i.putExtra("Id", item);
     startActivity(i);
}

@Override
public void onBackPressed() {
	ParseUser current = ParseUser.getCurrentUser();
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
}
