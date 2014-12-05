package com.FAU.help;

import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

public class Leaderboard extends ActionBarActivity
{
	String Username;
    ParseUser currentUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
        getWindow().setSoftInputMode(
        	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
 
        currentUser = ParseUser.getCurrentUser();  
        if (currentUser!=null)
        {
        Username = currentUser.getEmail();
        
        }
        loadleaders();
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
    
    public void loadleaders()
    {
    	int x=0;
    	final ListView listView = (ListView) findViewById(R.id.listView1);
    	final ListView listView2 = (ListView) findViewById(R.id.listView2);
    	if (x ==0)
    	{
    	ParseQueryAdapter<ParseUser> adapter =
            	new ParseQueryAdapter<ParseUser>(this, new ParseQueryAdapter.QueryFactory<ParseUser>() {
                public ParseQuery<ParseUser> create() {
                    // Here we can configure a ParseQuery to our heart's desire.
                	
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereGreaterThan("Ranking", 0);
                    query.addDescendingOrder("Ranking");
                    query.setLimit(10);
                    return query;
                  }
                });
            
            adapter.setTextKey("FullName");
            
            listView.setAdapter(adapter);
            adapter =
            	new ParseQueryAdapter<ParseUser>(this, new ParseQueryAdapter.QueryFactory<ParseUser>() {
                public ParseQuery<ParseUser> create() {
                    // Here we can configure a ParseQuery to our heart's desire.
                	
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereGreaterThan("Ranking", 0);
                    query.addDescendingOrder("Ranking");
                    query.setLimit(10);
                    return query;
                  }
                });
                
                adapter.setTextKey("Ranking");
                
                
                listView2.setAdapter(adapter);
                x =1;
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
