package com.FAU.help;

import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UserHome extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);
        
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

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v) 
	   {
		 switch (v.getId())
	    	{
	    	case R.id.findevents:
				 Intent h = new Intent(this, Map.class); /*should go to map*/
		           startActivity(h);
				 break;
		 case R.id.LTV:
			 Intent i = new Intent(this, AddLoc.class);
	           startActivity(i);
			 break;
		 case R.id.LFV:
			Intent j = new Intent(this, MyEvents.class);
	        startActivity(j);
			 break;
	    	}
	   }
}
