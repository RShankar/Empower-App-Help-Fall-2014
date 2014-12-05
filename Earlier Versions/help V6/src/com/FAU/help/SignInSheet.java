package com.FAU.help;

import java.util.Calendar;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInSheet extends ActionBarActivity {
String event, name, username, msg, today, date;
int doit=0;
double hrs=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinsheet);
        
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
        getWindow().setSoftInputMode(
        	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent i = getIntent();
        event = i.getStringExtra("Id");
        ParseQuery <ParseObject> query = ParseQuery.getQuery("Events");
        try {
			ParseObject ev = query.get(event);
			 name = ev.get("EventName").toString();
			 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        TextView view = (TextView) findViewById(R.id.score);
        //Toast.makeText(getBaseContext(), event, Toast.LENGTH_LONG).show();
        view.setText(name);
        
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month =c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        today = month+"/"+date+"/"+year; 
        
        
        //Toast.makeText(getBaseContext(), today, Toast.LENGTH_LONG).show();
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
    	doit=1;
    	EditText edit = (EditText) findViewById(R.id.EventName);
	    username = edit.getText().toString();
	    if (checkuser()==0)
	    {
	    	doit=0;
	    	msg="Student Username not recoginized. Are you signed up?";
	    }
	    
	    edit = (EditText) findViewById(R.id.OrgAddress);
	    String shrs = edit.getText().toString();
	    hrs=-1;
	    if(shrs.matches("\\d+")){
	    hrs = Double.parseDouble(shrs);
	    }
	    if(hrs>12||hrs<0)
	    {
	    	doit=0;
	    	msg="Please pick a number between 0 and 12";
	    }
	    
	    ParseQuery<ParseUser> query = ParseQuery.getQuery("eventsAtt");
    	query.whereEqualTo("User", username);
    	query.whereEqualTo("date", today);
    	query.whereEqualTo("EventName", name);
    	List<ParseUser> objects = null;
		try {
			objects = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if (objects.size()>0)
    	{
    		doit = 0;
    		msg ="You've already signed in today at this event";
    	}
	    
	    if(doit==1)
	    {
	    save();
	    }
	    else
	    {
	    	Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
	    }
	   }
	@Override
	public void onBackPressed() {
	    // do something on back.
		//Intent j = new Intent(this, MyEvents.class);
        //startActivity(j);
		
	    return;
	}
	
	public void save()
	{
		ParseObject gameScore = new ParseObject("eventsAtt");
    	gameScore.put("Hrs", hrs);
    	gameScore.put("EventName", name);
    	gameScore.put("User", username);
    	gameScore.put("date", today);
    	gameScore.saveInBackground();
		
    	Toast.makeText(getBaseContext(), "You have been signed in", Toast.LENGTH_LONG).show();
    	ViewGroup group = (ViewGroup)findViewById(R.id.q);
    	for (int i = 0, count = group.getChildCount(); i < count; ++i) {
    	    View view = group.getChildAt(i);
    	    if (view instanceof EditText) {
    	        ((EditText)view).setText("");
    	    }
    	}
    	group = (ViewGroup)findViewById(R.id.w);
    	for (int i = 0, count = group.getChildCount(); i < count; ++i) {
    	    View view = group.getChildAt(i);
    	    if (view instanceof EditText) {
    	        ((EditText)view).setText("");
    	    }
    	}
	}
	
	 public int checkuser() 
	    {
	    	ParseQuery<ParseUser> query = ParseUser.getQuery();
	    	query.whereEqualTo("username", username);
	    	query.whereEqualTo("AccountType", "V");
	    	List<ParseUser> objects = null;
			try {
				objects = query.find();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if (objects.size()>0)
	    	{
	    		return 1;
	    	}
			return 0;
	    	
	    	
	}
}
