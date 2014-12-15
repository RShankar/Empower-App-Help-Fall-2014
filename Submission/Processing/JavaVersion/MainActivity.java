package com.FAU.help;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends PApplet {

int button1X, button1Y;      // Position of first button
int button2X, button2Y;  // Position of second button
int button3X, button3Y;  // Position of Third button
int button1Size = width;     // Diameter of first button
int button2Size = width;   // Diameter of second button
int button3Size = width;   // Diameter of Third button
int button1Color, button2Color,button3Color, baseColor;
int button1Highlight, button2Highlight, button3Highlight;
boolean button1Over = false;
boolean button2Over = false;
boolean button3Over = false;

PFont word1;
PImage image,button1,button2,button3;
String Token, Volunteer ="Volunteer";

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   Parse.initialize(this, "cYmMMmAde85cb3vziaRxx09ZYrKYFERHSvk23YJg", "ywOTFtcqvMfSKzzbGioolJqXoGvT4v2kUUAkjWXW");
   SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
    Token = pref.getString("key", null);
    if (Token!=null){
        try {
			ParseUser.become(Token);
			ParseUser currentUser = ParseUser.getCurrentUser();
			
		     if(currentUser!=null)
		     {
		    	 String type = currentUser.getString("AccountType");
				 if (type.equals("VC"))
				 {
				 Intent i = new Intent(this, UserHome.class);
		         startActivity(i);
		         
				 }
				 
				 else if (type.equals("V"))
				 {
				 Intent i = new Intent(this, StudentHome.class);
		         startActivity(i);
					 
				 }
		     }}
        catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
 }
    
}
public void setup() {

 
  word1 = createFont("Serif",75); // Arial, 16 point, anti-aliasing on
  image = loadImage("caroline.jpg");
  button1 = loadImage("volunteersbutton.png");
  button2 = loadImage("revolunteersbutton.png");
  button3 = loadImage("lookingbutton.png");
  button1Color = color(162);
  button1Highlight = color(51);
  button2Color = color(162);
  button2Highlight = color(51);
  button3Color = color(162);
  button3Highlight = color(51);
  baseColor = color(255);
  button2X = width/2;
  button2Y = height/2;
  button1X = width/2;
  button1Y = height-height/3;
  button3X = width/2;
  button3Y = height-height/6;
}

public void draw() {
	size(width,height);
	update(mouseX, mouseY);
	imageMode(CORNER);
	tint(255,3);
	image(image,0,0,width,height);
	
	imageMode(CENTER);

    fill(button1Color);
    stroke(255);
    tint(255,277);
    image(button1,button1X,button1Y,width/4,button1Y/7);
  
    fill(button2Color);
    stroke(255);
    image(button2,button2X,button2Y,width/4,button1Y/7);
  
    fill(button3Color);
	stroke(255);
	image(button3,button3X,button3Y,width/4,button1Y/7);  
  
  textFont(word1,80);                 // STEP 4 Specify font to be used
  fill(255);                        // STEP 5 Specify font color 
  textAlign(CENTER);
  text(" I'm",width/2,height/3+50);



}

void update(int x, int y) {
	  if ( overButton1(button1X, button1Y, width/4, button1Y/7) ) {
	    button1Over = true;
	    button2Over = false;
	    button3Over = false;
	  } else if ( overButton2(button2X, button2Y, width/4, button1Y/7) ) {
	    button2Over = true;
	    button1Over = false;
	    button3Over = false;
	  }
	  else if ( overButton3(button3X, button3Y, width/4, button1Y/7) ) {
		    button3Over = true;
		    button1Over = false;
		    button2Over = false;
		  } else {
	    button1Over = button2Over = button3Over= false;
	  }
	}

public void mousePressed() {
  if (button2Over) {
		 Intent i = new Intent(this, Signin.class);
		 i.putExtra(Volunteer, "V");
      startActivity(i);
		 
  }
  if (button1Over) {
	  Intent j = new Intent(this,Signin.class);
	  j.putExtra(Volunteer, "VC");
      startActivity(j);
  }
  if (button3Over) {
		 Intent qw = new Intent(this,Leaderboard.class);
	     startActivity(qw);
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

  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
  public String sketchRenderer() { return P3D; }
  @Override
  public void onBackPressed() {
    
  }
}
