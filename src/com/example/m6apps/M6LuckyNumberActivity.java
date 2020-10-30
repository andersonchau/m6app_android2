package com.example.m6apps;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class M6LuckyNumberActivity extends Activity {
	 final int[] rowLayoutID = new int[] {
	     R.id.lucky1_layout,
	     R.id.lucky2_layout,
	     R.id.lucky3_layout,
	     R.id.lucky4_layout,
	 }; 
	 final int[] resIDArray = new int[]{
			 R.drawable.no_01_s,
			 R.drawable.no_02_s,
			 R.drawable.no_03_s,
			 R.drawable.no_04_s,
			 R.drawable.no_05_s,
			 R.drawable.no_06_s,
			 R.drawable.no_07_s,
			 R.drawable.no_08_s,
			 R.drawable.no_09_s,
			 R.drawable.no_10_s,
			 R.drawable.no_11_s,
			 R.drawable.no_12_s,
			 R.drawable.no_13_s,
			 R.drawable.no_14_s,
			 R.drawable.no_15_s,
			 R.drawable.no_16_s,
			 R.drawable.no_17_s,
			 R.drawable.no_18_s,
			 R.drawable.no_19_s,
			 R.drawable.no_20_s,
			 R.drawable.no_21_s,
			 R.drawable.no_22_s,
			 R.drawable.no_23_s,
			 R.drawable.no_24_s,
			 R.drawable.no_25_s,
			 R.drawable.no_26_s,
			 R.drawable.no_27_s,
			 R.drawable.no_28_s,
			 R.drawable.no_29_s,
			 R.drawable.no_30_s,
			 R.drawable.no_31_s,
			 R.drawable.no_32_s,
			 R.drawable.no_33_s,
			 R.drawable.no_34_s,
			 R.drawable.no_35_s,
			 R.drawable.no_36_s,
			 R.drawable.no_37_s,
			 R.drawable.no_38_s,
			 R.drawable.no_39_s,
			 R.drawable.no_40_s,
			 R.drawable.no_41_s,
			 R.drawable.no_42_s,
			 R.drawable.no_43_s,
			 R.drawable.no_44_s,
			 R.drawable.no_45_s,
			 R.drawable.no_46_s,
			 R.drawable.no_47_s,
			 R.drawable.no_48_s,
			 R.drawable.no_49_s
		 };
	 private static final int NUM_OF_BALLS_IN_RESULT= 7;
	 private static final int NUM_OF_LUCKY_RANK= 4;
	 ImageView[][] ref2LuckBall;
	 ImageView buttonPanelView;
	 ImageView regenButtonView; 
	 
	 
	 int[][] randomPool = null;
	 
	 
	 void initRandomPool(){
		 randomPool = new int[NUM_OF_LUCKY_RANK][NUM_OF_BALLS_IN_RESULT];
	    for ( int i = 0 ; i < NUM_OF_LUCKY_RANK ; i++ ){
	    	for ( int j = 0 ; j < NUM_OF_BALLS_IN_RESULT ; j++ ){
	    		randomPool[i][j] = j;
	    	}
	    }
	 }
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_lucky);
	    initRandomPool();
	    buttonPanelView = (ImageView)findViewById(R.id.bottom_panel);

	    ref2LuckBall = new ImageView[NUM_OF_LUCKY_RANK][NUM_OF_BALLS_IN_RESULT];
	    
	    displayLuckyBallNumbers();
	    buttonPanelView.setOnTouchListener(new View.OnTouchListener() 
	    {

	        @Override
	        public boolean onTouch(View arg0, MotionEvent event) 
	        {
	        	switch(event.getAction())
	            {
	            case MotionEvent.ACTION_DOWN:
	            	return true;
	            case MotionEvent.ACTION_CANCEL:
	            case MotionEvent.ACTION_OUTSIDE:
	                return true;
	            case MotionEvent.ACTION_UP:
	            	int x = (int)event.getX();
	                int y = (int)event.getY();
	                int totalWidth = buttonPanelView.getWidth();
	                int whichButton = getWhichDivision((float)x , (float)totalWidth);
	                if ( whichButton == 0 ){
                    	//Toast.makeText(getApplicationContext(), "First Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(M6LuckyNumberActivity.this, ChooseBallActivity.class); 
                   	    startActivity(intent);
                   	    finish();
                    	
                    } else if ( whichButton == 1 ){
                    	
                    } else if ( whichButton == 2 ) {
                    	//Toast.makeText(getApplicationContext(), "Third Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(M6LuckyNumberActivity.this, StatisticsActivity.class); 
                   	    startActivity(intent);
                    	finish();
                    } else if ( whichButton == 3) {
                    	//Toast.makeText(getApplicationContext(), "Forth Button", Toast.LENGTH_SHORT).show();
                    	finish();
                    }
	                return true;
	            }
	            return false;
	        }
	    });
	    
	    regenButtonView = (ImageView)findViewById(R.id.gen_button);
	    regenButtonView.setOnTouchListener(new View.OnTouchListener() 
	    {

	        @Override
	        public boolean onTouch(View arg0, MotionEvent event) 
	        {
	        	switch(event.getAction()){
	        	 case MotionEvent.ACTION_DOWN:
		            	return true;
		            case MotionEvent.ACTION_CANCEL:
		            case MotionEvent.ACTION_OUTSIDE:
		                return true;
		            case MotionEvent.ACTION_UP:
		            	int x = (int)event.getX();
		                int y = (int)event.getY();
		                int totalWidth = regenButtonView.getWidth();
		            	if ( isWithinButtonRange((float)x , (float)totalWidth) == true ){
		            		regenerateRandomBalls();
		            	}
		            	return true;
	        	}
	        	return false;
	        }
	    });
	 } // onCreate

     void regenerateRandomBalls(){
    	 for ( int i = 0 ; i < NUM_OF_LUCKY_RANK ; i++ ){
 	    	for ( int j = 0 ; j < NUM_OF_BALLS_IN_RESULT ; j++ ){
 	    		
 	    		Random r = new Random();
 	    		int i1=r.nextInt(48);
 	    		randomPool[i][j] = i1;
 	    		redisplayLuckyBallNumbers();
 	    	}
 	    }
     }

	 void redisplayLuckyBallNumbers() {	
         int counter = 0;
		  boolean result = false;
		  for ( int row = 0 ; row < NUM_OF_LUCKY_RANK ; row++ ){
		     for( int ballNum = 0 ; ballNum < NUM_OF_BALLS_IN_RESULT ; ballNum++ ){
		    	 ref2LuckBall[row][ballNum].setImageResource(resIDArray[randomPool[row][ballNum]]);
		    	 
		     }
		  }
		  
	   }
	 
	 void displayLuckyBallNumbers() {	
         int counter = 0;
		  boolean result = false;
		  for ( int row = 0 ; row < NUM_OF_LUCKY_RANK ; row++ ){
		     for( int ballNum = 0 ; ballNum < NUM_OF_BALLS_IN_RESULT ; ballNum++ ){
		    	 LinearLayout iconLayout = (LinearLayout)findViewById(rowLayoutID[row]);
		    	 ImageView imageView = new ImageView(M6LuckyNumberActivity.this);
			     imageView.setImageResource(resIDArray[randomPool[row][ballNum]]);
			     imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,5f));
			     iconLayout.addView(imageView);
			     ref2LuckBall[row][ballNum] = imageView;
		    	 
		     }
		  }
		  
	   }
	 
	 public boolean isWithinButtonRange(float xPos, float fullWidth ){
		 Log.d("Anderson", "isWithinButtonRange " + xPos + " " + fullWidth );
		  if ( xPos/fullWidth > 0.33 && xPos/fullWidth <= 0.66 ){
			 return true;
		 } 
		  return false;
		}	 
public int getWhichDivision(float xPos, float fullWidth ){
 Log.d("Anderson", "getWhichDivision " + xPos + " " + fullWidth );
 if ( xPos/fullWidth <= 0.25 ){
     return 0;	 
 } else if ( xPos/fullWidth > 0.25 && xPos/fullWidth <= 0.5 ){
	 return 1;
 } else if ( xPos/fullWidth > 0.5 && xPos/fullWidth <= 0.75 ){
	 return 2;
 } else { 
	 return 3;
 } 
}
}