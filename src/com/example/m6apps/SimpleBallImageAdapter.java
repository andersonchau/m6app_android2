package com.example.m6apps;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
 

public class SimpleBallImageAdapter extends BaseAdapter {
	private Context mContext;
	 
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
    
    // Constructor
    public SimpleBallImageAdapter(Context c){
    	mContext = c;
    	
    	
    }
 
    @Override
    public int getCount() {
        return resIDArray.length;
    }
 
    @Override
    public Object getItem(int position) {
        return resIDArray[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    // This defines your touch listener
    @SuppressLint("NewApi")
	private final class MyTouchListener implements OnTouchListener {
      @SuppressLint("NewApi")
	public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
          ClipData data = ClipData.newPlainText("", "");
          View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
          view.startDrag(data, shadowBuilder, view, 0);
          //view.setVisibility(View.INVISIBLE);
          return true;
        } else {
        return false;
        }
      }
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(resIDArray[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        imageView.setTag(resIDArray[position]);
        imageView.setOnTouchListener(new MyTouchListener());
        return imageView;
    }
}
