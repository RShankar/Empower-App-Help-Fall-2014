int button1X, button1Y;      // Position of square button
int button2X, button2Y;  // Position of circle button
int button1Size = width;     // Diameter of rect
int button2Size = width;   // Diameter of circle
color button1Color, button2Color, baseColor;
color button1Highlight, button2Highlight;
color currentColor;
boolean button1Over = false;
boolean button2Over = false;
PFont word1;
PImage image;

void setup() {
  println("Width: "+ width);
  println("Height: " + height);
  println("Display Width: "+ displayWidth);
  println("Display Height: "+ displayHeight);
  size(displayWidth, displayHeight, P3D);
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

void draw() {
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

void update(int x, int y) {
  if ( overButton1(button1X, button1Y, width-600, button1Y/7) ) {
    button1Over = true;
    button2Over = false;
  } else if ( overButton2(button2X, button2Y, width-600, button1Y/7) ) {
    button2Over = true;
    button1Over = false;
  } else {
    button1Over = button2Over = false;
  }
}

void mousePressed() {
  if (button2Over) {
    currentColor = button2Color;
  }
  if (button1Over) {
    currentColor = button1Color;
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
