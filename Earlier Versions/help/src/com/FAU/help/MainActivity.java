package com.FAU.help;


//import com.parse.Parse;
import com.parse.Parse;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        	ParseUser.logOut();
        	Intent i = new Intent(this, MainActivity.class);
	           startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    public void onClick(View v) 
	   {
    	
		 switch (v.getId())
	    	{
		 case R.id.LTV:
			 Intent i = new Intent(this, Signin.class);/*should go to map*/
	           startActivity(i);
			 break;
			 
		 case R.id.LFV:	
			Intent j = new Intent(this,Signin.class);
	        startActivity(j);
			 break;
	    	}
	   }
}
