package com.example.songbeacon;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;


public class EditUser extends Activity{

	TextView namefield;
	EditText songfield;
	Button savechanges;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
		User u = (User) i.getSerializableExtra("selecteduser");
		
		setContentView(R.layout.edituser);
		
		namefield = (TextView) findViewById(R.id.tv_name);
		songfield = (EditText) findViewById(R.id.et_songselection);
		savechanges = (Button) findViewById(R.id.b_savechanges);
		
		//set form fields to the user's attribute values
		namefield.setText(u.getName());
		songfield.setText(u.getSongID());
	}
	
	
	
	
	public void writeToFirebase(View v){
		
		Firebase ref = new Firebase("https://resplendent-fire-3957.firebaseio.com/");
		
		Firebase userref = ref.child("");
	}
	
	
}
