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
	User selecteduser;
	String song_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
		selecteduser = (User) i.getSerializableExtra("selecteduser");
		
		setContentView(R.layout.edituser);
		
		namefield = (TextView) findViewById(R.id.tv_name);
		songfield = (EditText) findViewById(R.id.et_songselection);
		savechanges = (Button) findViewById(R.id.b_savechanges);
		
		//set form fields to the user's attribute values
		namefield.setText(selecteduser.getName());
		songfield.setText(selecteduser.getSongID());
	}
	
	
	
	
	public void writeToFirebase(View v){
		song_id = songfield.getText().toString();
		
		Firebase ref = new Firebase("https://resplendent-fire-3957.firebaseio.com/" + selecteduser.getName());
		ref.child("songID").setValue(song_id);
	}
	
	
}
