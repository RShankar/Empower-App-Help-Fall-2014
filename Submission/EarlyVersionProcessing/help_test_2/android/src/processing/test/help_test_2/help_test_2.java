package processing.test.help_test_2;

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

public class help_test_2 extends PApplet {

int button1X, button1Y;      // Position of square button
int button2X, button2Y;  // Position of circle button
int button1Size = width;     // Diameter of rect
int button2Size = width;   // Diameter of circle
int button1Color, button2Color, baseColor;
int button1Highlight, button2Highlight;
int currentColor;
boolean button1Over = false;
boolean button2Over = false;
PFont word1;
PImage image;

public void setup() {
  println("Width: "+ width);
  println("Height: " + height);
  println("Display Width: "+ displayWidth);
  println("Display Height: "+ displayHeight);
 
  word1 = createFont("Arial",16,true); // Arial, 16 point, anti-aliasing on
  image = loadImage("volunteerhands.jpg");
  button1Color = color(162);
  button1Highlight = color(51);
  button2Color = color(162);
  button2Highlight = color(204);
  baseColor = color(255);
  currentColor = baseColor;
  button2X = width/4;
  button2Y = height/2;
  button1X = width/4;
  button1Y = height-height/3;
  ellipseMode(CENTER);
}

public void draw() {
  update(mouseX, mouseY);
  background(currentColor);
  
  if (button1Over) {
    fill(button1Highlight);
  } else {
    fill(button1Color);
  }
  stroke(255);
  rect(button1X, button1Y, width-600, button1Y/7);
  
  if (button2Over) {
    fill(button2Highlight);
  } else {
    fill(button2Color);
  }
  stroke(255);
  rect(button2X, button2Y, width-600, button1Y/7);
  textFont(word1,75);                 // STEP 4 Specify font to be used
  fill(0);                        // STEP 5 Specify font color 
  textAlign(CENTER);
  text("I'm",width/2,button2Y-50);
  text("Looking for",width/2,button1Y+button1Y/15);
   text("Voulenteers!",width/2,button1Y+button1Y/8);
  text("Looking to",width/2,button2Y+button1Y/15);
   text("Voulenteer!",width/2,button2Y+button1Y/8);   
  image(image,width/4,height/12,width/2,height/3);



}

public void update(int x, int y) {
 /* if ( overCircle(circleX, circleY, circleSize) ) {
    circleOver = true;
    rectOver = false;
  } else if ( overRect(rectX, rectY, rectSize, rectSize) ) {
    rectOver = true;
    circleOver = false;
  } else {
    circleOver = rectOver = false;
  }*/
}

public void mousePressed() {
  if (button2Over) {
    currentColor = button2Color;
  }
  if (button1Over) {
    currentColor = button1Color;
  }
}

public boolean overRect(int x, int y, int width, int height)  {
  if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}

public boolean overCircle(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if (sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
    return true;
  } else {
    return false;
  }
}

  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
  public String sketchRenderer() { return P3D; }
}
