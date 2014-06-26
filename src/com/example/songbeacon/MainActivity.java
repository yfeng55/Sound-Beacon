package com.example.songbeacon;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class MainActivity extends Activity{
	
	EditText namefield;
	Button registerbutton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		namefield = (EditText) findViewById(R.id.et_name);
		registerbutton = (Button) findViewById(R.id.b_register);
		
/*		Firebase ref = new Firebase("https://resplendent-fire-3957.firebaseio.com/");
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
	}
	
	
}
