package com.example.m6apps;
 
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
//import com.example.m6apps.R;
 
public class ImageAdapter extends BaseAdapter {
	private Context context;
	private int[] m6FrequencyValues;
 
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
   
	public ImageAdapter(Context context, int[] txtValues) {
		this.context = context;
		this.m6FrequencyValues = txtValues;
	}
    
	public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TAG","getView Called " + position + " " );
        
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView = null;
 
		if (convertView == null) {
 
			gridView = new View(context);
			gridView = inflater.inflate(R.layout.freq_item, null);
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(new Integer(m6FrequencyValues[position]).toString());
 
			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
 
			imageView.setImageResource(resIDArray[position]);
		} else {
			gridView = (View) convertView;
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(new Integer(m6FrequencyValues[position]).toString());
		}
		
		return gridView;
	}
	
	public void resetData(int[] txtValues){
		this.m6FrequencyValues = txtValues;
	}
 
	@Override
	public int getCount() {
		return m6FrequencyValues.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}