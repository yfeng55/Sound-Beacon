package com.example.songbeacon;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;


public class MainActivity extends Activity{
	
	EditText namefield;
	Button registerbutton;
	SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//obtain sharedpreferences
		prefs = this.getSharedPreferences("com.example.songbeacon", Context.MODE_PRIVATE);
		
		if(prefs.getBoolean("authtoken", false) == false){
			
			Log.i("EEE", "not logged in - the authtoken is false");
			
			setContentView(R.layout.activity_main);
			
			namefield = (EditText) findViewById(R.id.et_name);
			registerbutton = (Button) findViewById(R.id.b_register);
			
			
			
			
			
		}else{
			Log.i("EEE", "logged in - the authtoken is true");
			
		}
		
		
		/*Firebase ref = new Firebase("https://resplendent-fire-3957.firebaseio.com/");
		ref.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot arg0) { }
			
			@Override
			public void onCancelled(FirebaseError arg0) { }
		
		});*/
		
	}
	
	public void register(View v){
		//get a reference to the FireBase root
		Firebase ref = new Firebase("https://resplendent-fire-3957.firebaseio.com/");
		
		String name;
		name = namefield.getText().toString();
		
		Firebase newpushref = ref.push();
		newpushref.setValue(name);
		
		//create a new editor for the prefs object
		Editor editor = prefs.edit();
				
		//store the authtoken as "true"
		editor.putBoolean("authtoken", true);
		editor.commit();
		
		//display logged-in layout
		}
	
	
}


































