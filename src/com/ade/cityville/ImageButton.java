package com.ade.cityville;

import processing.core.*; 
import android.content.Intent;
import android.widget.Toast;

public class ImageButton extends PApplet {

PImage icon;  // Declare variable "a" of type PImage
PImage about;

public void setup()
{
 // Set the background color here in rgb
  background(225,225,225);
  /***icon****/
  icon = loadImage("ic_launcher.png");  // Load the image into the program  
  /***end icon***/
  about = loadImage("about.png"); 
  noLoop();  // Makes draw() only run once
}

public void draw()
{
	int y = 10+height/4;
  /***icon***/
  image(icon, width/2-((height/4)/2), 5, height/4, height/4);
  /***end icon***/
  image(about, width/2-((height/4)/2), y, height/4, height/4);
}

@Override
public void onBackPressed() {
	// fixes force close that occurres when using standard back button
	// this causes the activity to be destroyed and fall back to previous activity without null pointer FC
	finish();
}


  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
}
