package com.example.songbeacon;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent i = new Intent(Splash.this, MainActivity.class);
					startActivity(i);
				}
			}
		};
		timer.start();
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
}
