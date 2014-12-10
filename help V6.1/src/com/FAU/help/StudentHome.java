package com.FAU.help;

import java.util.List;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StudentHome extends PApplet {
    ParseUser currentUser = ParseUser.getCurrentUser();
    boolean Verified = currentUser.getBoolean("emailVerified");
    int button1X, button1Y;      // Position of first button
    int button2X, button2Y;  // Position of second button
    int button3X, button3Y;  // Position of Third button
    int button4X, button4Y;  // Position of Third button
    int button1Size = width;     // Diameter of first button
    int button2Size = width;   // Diameter of second button
    int button3Size = width;   // Diameter of Third button
    int button4Size = width;   // Diameter of Third button
    int button1Color, button2Color,button3Color,button4Color, baseColor;
    int button1Highlight, button2Highlight, button3Highlight,button4Highlight;
    boolean button1Over = false;
    boolean button2Over = false;
    boolean button3Over = false;
    boolean button4Over = false;
    
    PFont word1;
    PImage image,button1,button2,button3,button4;
    int HOURS=0;
    String Username;
    String x=" ";
    String y;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String Token = pref.getString("key", null);
        if (Token==null){
         currentUser = ParseUser.getCurrentUser();
        }
		 if(currentUser==null)
		 {
			 Intent i = new Intent(this, Signin.class);
		     startActivity(i);
		 }
        
		 Username= currentUser.getEmail();
		 
        String first = currentUser.getString("FullName");
        first.trim();
        String array[] = first.split("\\s+");
        x=array[0];        
        adduphrs();
        
    }
    public void setup() {
  	 
  	  word1 = createFont("Serif",75); // Arial, 16 point, anti-aliasing on
  	  image = loadImage("caroline.jpg");
  	  button1 = loadImage("leadersbutton.png");
  	  button2 = loadImage("findevent.png");
  	  button3 = loadImage("myevents.png");
  	  button4 = loadImage("signout.png");
  	  button1Color = color(162);
  	  button1Highlight = color(51);
  	  button2Color = color(162);
  	  button2Highlight = color(51);
  	  button3Color = color(162);
  	  button3Highlight = color(51);
  	  button4Color = color(162);
  	  button4Highlight = color(51);
  	  baseColor = color(255);
  	  button1X = width/2;
  	  button1Y = height/2;
  	  button2X = width/2;
  	  button2Y = height-height/3;
  	  button3X = width/2;
  	  button3Y = height-height/6;
  	  button4X = width-200;
  	  button4Y = height-height/9;
  	}

  	public void draw() {
  	  update(mouseX, mouseY);
  	imageMode(CORNER);
  	tint(255,3);
  	image(image,0,0,width,height);
  	
  	imageMode(CENTER);
    tint(255,277);
  	    fill(button1Color);
  	    stroke(255);
  	    image(button1,button1X,button1Y,width/4, button2Y/7); 
  	  
  	    fill(button2Color);
  	    stroke(255);
  	    image(button2,button2X,button2Y,width/4, button2Y/7); 
  	  
  	    fill(button3Color);
  		stroke(255);
  		image(button3,button3X,button3Y,width/4, button2Y/7); 
  		  
  	    fill(button4Color);
  		stroke(255);
  		image(button4,button4X,button4Y,150, button2Y/12); 
  		
  	  textFont(word1,90);                 
  	  fill(255);      
  	  textAlign(CENTER);
  	  text("Welcome "+x,width/2,height/3+50);
  	  text(HOURS+" Hrs",width/2,height/3+150);



  	}

  	void update(int x, int y) {
  		  if ( overButton1(button1X, button1Y, width/4, button2Y/7) ) {
  		    button1Over = true;
  		    button2Over = false;
  		    button3Over = false;
  		    button4Over = false;
  		  } else if ( overButton2(button2X, button2Y, width/4, button2Y/7) ) {
  		    button2Over = true;
  		    button1Over = false;
  		    button3Over = false;
  		    button4Over = false;
  		  }
  		  else if ( overButton3(button3X, button3Y, width/4, button2Y/7) ) {
  			    button3Over = true;
  			    button4Over = false;
  			    button1Over = false;
  			    button2Over = false;
  			  } else if ( overButton4(button4X, button4Y, 150, button2Y/12) ) {
    			    button4Over = true;
    			    button3Over = false;
    			    button1Over = false;
    			    button2Over = false;
    			  } else {
  		    button1Over = button2Over = button3Over= button4Over=false;
  		  }
  		}

  	public void mousePressed() {
  	  if (button2Over) {
  		Intent h = new Intent(this, Map.class); /*should go to map*/
        startActivity(h);
  			 
  	  }
  	  else if (button1Over) {
  		 Intent i = new Intent(this, Leaderboard.class);
         startActivity(i);
  	  }
  	  else if (button3Over) {
  		Intent j = new Intent(this, MyEvents.class);
        startActivity(j);
  	  }
  	  else if (button4Over) {
        	ParseUser.logOut();
			 SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			 Editor editor = pref.edit();
			 editor.clear();
			 editor.commit();
        	Intent i = new Intent(this, MainActivity.class);
	        startActivity(i);
  	  }
  	}

  	boolean overButton1(int x, int y, int width, int height)  {
  		  if (mouseX >= x && mouseX <= x+width && 
  		      mouseY >= y && mouseY <= y+height) {
  		    return true;
  		  } else {
  		    return false;
  		  }
  		}

  		boolean overButton2(int x, int y, int width, int height) {
  		  if (mouseX >= x && mouseX <= x+width && 
  		      mouseY >= y && mouseY <= y+height) {
  		    return true;
  		  } else {
  		    return false;
  		  }
  		}
  		boolean overButton3(int x, int y, int width, int height) {
  			  if (mouseX >= x && mouseX <= x+width && 
  			      mouseY >= y && mouseY <= y+height) {
  			    return true;
  			  } else {
  			    return false;
  			  }
  			}
  		boolean overButton4(int x, int y, int width, int height) {
			  if (mouseX >= x && mouseX <= x+width && 
			      mouseY >= y && mouseY <= y+height) {
			    return true;
			  } else {
			    return false;
			  }
			}

  	  public int sketchWidth() { return displayWidth; }
  	  public int sketchHeight() { return displayHeight; }
  	  public String sketchRenderer() { return P3D; }

   
    public void showad(){
    	new AlertDialog.Builder(this)
	    .setTitle("Check your Input")
	    
	    .setMessage("Please verify your email using the email sent when you regisitered")
	   
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
    
    @Override
    public void onBackPressed() {
      
    }
    
   
    
    
    public void adduphrs()
    {
    	ParseQuery<ParseObject> Events = ParseQuery.getQuery("eventsAtt");
        Events.whereEqualTo("User", Username);
        Events.findInBackground(new FindCallback<ParseObject>() {
		    
            public void done(List<ParseObject> locList, ParseException e) {
            	
                if (e == null) {
                
                    Log.d("location", "Retrieved " + locList.size() + " loc");
                    int i=0;
                	while(i<locList.size())
                	{
                		HOURS=HOURS + locList.get(i).getInt("Hrs");
                		i++;
                	}
                	//TextView view = (TextView) findViewById(R.id.score);
                    //view.setText(HOURS+ " Hrs");
                	  //text(" Hrs",width/2,height/2);
                    currentUser.put("Ranking", HOURS);
                    currentUser.saveInBackground();
					} 
                    
                   
                 else {
                    Log.d("location", "Error: " + e.getMessage());
                  
                }
                
            }
            
        });
        
    }
    
}
