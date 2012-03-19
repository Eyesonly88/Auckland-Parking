/* Author : Mohammed Basim
 * Site : mbasim.me
 * Date : 29/01/2012
 */

package com.molabs.aucklandparking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeScreenActivity extends Activity{


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Button cbd = (Button) findViewById(R.id.button1);
		Button north = (Button) findViewById(R.id.button2);
		
		cbd.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent=new Intent(HomeScreenActivity.this,AucklandParkingActivity.class);
				//startActivity(intent);
				intent.putExtra("Region", "CBD");
				final int result=1;
				startActivityForResult(intent, result);
			}
		});
		
		north.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent=new Intent(HomeScreenActivity.this,AucklandParkingActivity.class);
				//startActivity(intent);
				intent.putExtra("Region", "NORTH");
				final int result=2;
				startActivityForResult(intent, result);
			}
		});
	}



}
