package com.example.m6apps;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ChooseBallActivity extends Activity {
	ImageView buttonPanelView = null;
	
	ArrayList<Integer> chosenBallList = null;
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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);
 
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        
        // Instance of ImageAdapter Class
        gridView.setAdapter(new SimpleBallImageAdapter(this));
        buttonPanelView = (ImageView)findViewById(R.id.bottom_panel);
	    
	    
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
                    	//Intent intent = new Intent(DisplayResultActivity.this, ChooseBallActivity.class); 
                   	    //startActivity(intent);
                   	    //finish();
                    	
                    } else if ( whichButton == 1 ){
                    	//Toast.makeText(getApplicationContext(), "Second Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(ChooseBallActivity.this, M6LuckyNumberActivity.class); 
                   	    startActivity(intent);
                   	    finish();
                    } else if ( whichButton == 2 ) {
                    	//Toast.makeText(getApplicationContext(), "Third Button", Toast.LENGTH_SHORT).show();
                    	Intent intent = new Intent(ChooseBallActivity.this, StatisticsActivity.class); 
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
	    
	    findViewById(R.id.number_text).setOnDragListener(new MyDragListener());

	    
	    
    }
	
	void enqueueBallNumber(int ballNum ){
		if ( chosenBallList == null ){
		    chosenBallList = new ArrayList<Integer>();
		}
		chosenBallList.add(ballNum+1);
		String displayText = " ";
		TextView myTxtView = (TextView)ChooseBallActivity.this.findViewById(R.id.number_text);
		int i = 0;
		for ( int ball : chosenBallList ){
			if ( i == 0 ){
				displayText = new Integer(ball).toString();
			} else {
				displayText = displayText + "," + new Integer(ball).toString();
			}
			i++;
		}
		displayText = displayText + "\n\n\n"; 
		myTxtView.setText(displayText);
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
	static int id = 1;

	// Returns a valid id that isn't in use
	public int findId(){  
	    View v = findViewById(id);  
	    while (v != null){  
	        v = findViewById(++id);  
	    }  
	    return id++;  
	}
	
	@SuppressLint("NewApi")
	class MyDragListener implements OnDragListener {
	      //Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
	      @SuppressLint("NewApi")
		//Drawable normalShape = getResources().getDrawable(R.drawable.shape);
	      @Override
	      public boolean onDrag(View v, DragEvent event) {
	        int action = event.getAction();
	        switch (action) {
	        case DragEvent.ACTION_DRAG_STARTED:
	        // Do nothing
	          break;
	        case DragEvent.ACTION_DRAG_ENTERED:
	          
	          break;
	        case DragEvent.ACTION_DRAG_EXITED:        
	          
	          break;
	        case DragEvent.ACTION_DROP:
	          // Dropped, reassign View to ViewGroup
	          Log.d("Anderson" , "view dropping ");
	          View view = (View) event.getLocalState();
	          int result = getBallNumber(view);
	          Log.d("Anderson" , "getBallNumber return " + result);
	          if ( result != -1 ){
	        	  enqueueBallNumber(result);
	        	  /*
	        	  TextView myTxtView = (TextView)ChooseBallActivity.this.findViewById(R.id.number_text);
	        	  String myText = (String) myTxtView.getText();
	        	  if ( myText.length() >= 4 && myText.substring(0,4).equals("drag") ){
	        		  String newText =   new Integer(result).toString();
	        		  myTxtView.setText(newText);
	        	  } else {
	        		  String newText =  myText + "," + new Integer(result).toString();
	        		  myTxtView.setText(newText);
	        	  }
	        	  */
	        	  
	          } else {
	              
	          }
	          //ViewGroup owner = (ViewGroup) view.getParent();
	          //owner.removeView(view);
	         // LinearLayout container = (LinearLayout) v;
	         // container.addView(view);
	         // view.setVisibility(View.VISIBLE);
	          
	          break;
	        case DragEvent.ACTION_DRAG_ENDED:
	         
	          default:
	          break;
	        }
	        return true;
	      }
	    } 
	int getBallNumber(View imgView){
		
		ImageView ballPic = (ImageView)imgView;
		Object tag = ballPic.getTag();                  
		int id = tag == null ? -1 : Integer.parseInt(tag.toString());
		for ( int i = 0 ; i < resIDArray.length ; i++ ){
			if (id == resIDArray[i] ){
			    return i;
			}
		}
	    return -1;
	}
}
