package com.example.songbeacon;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class MainActivity extends Activity{
	
	private String firebaseHost = "https://resplendent-fire-3957.firebaseio.com/";
	private Firebase ref;
	
	//views for register layout
	View registerLayout;
	EditText namefield;
	Button registerbutton;
	SharedPreferences prefs;
	User bigbootyuser;
	
	//views for application layout
	View applicationLayout;
	TextView name, deviceID, songID;

	List <User> userList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		ref = new Firebase(firebaseHost);
		setFirebaseEventListener();
		
		userList = new ArrayList<User>();
		
		registerLayout = findViewById(R.id.registerLayout);
		namefield = (EditText) findViewById(R.id.et_name);
		registerbutton = (Button) findViewById(R.id.b_register);
		
		applicationLayout = findViewById(R.id.applicationLayout);
		name = (TextView)findViewById(R.id.tv_name);
		deviceID = (TextView)findViewById(R.id.tv_deviceID);
		songID = (TextView)findViewById(R.id.tv_songID);
		
		
		//obtain sharedpreferences
		prefs = this.getSharedPreferences("com.example.songbeacon", Context.MODE_PRIVATE);
		
		if(prefs.getBoolean("authtoken", false) == false){
			
			Log.i("EEE", "not logged in - the authtoken is false");
						
			registerLayout.setVisibility(View.VISIBLE);
			applicationLayout.setVisibility(View.GONE);
			
			
			
			
		}else{
			Log.i("EEE", "logged in - the authtoken is true");
			registerLayout.setVisibility(View.GONE);
			applicationLayout.setVisibility(View.VISIBLE);
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
		
		String name;
		name = namefield.getText().toString();

		TelephonyManager tMan = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		bigbootyuser = new User(name, tMan.getDeviceId());
		
		Firebase newpushref = ref.push();
		newpushref.setValue(bigbootyuser);
		
		
		//create a new editor for the prefs object
		Editor editor = prefs.edit();
				
		//store the authtoken as "true"
		editor.putBoolean("authtoken", true);
		editor.putString("name", bigbootyuser.getName());
		editor.putString("deviceID", bigbootyuser.getDeviceID());
		editor.putString("songID", bigbootyuser.getSongID());
		editor.commit();
		
		
		//display logged-in layout
		registerLayout.setVisibility(View.GONE);
		applicationLayout.setVisibility(View.VISIBLE);
		}
	
	protected void setFirebaseEventListener()
	{
		ref.addChildEventListener(new ChildEventListener() {
		  @Override
		  public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		    User m = snapshot.getValue(User.class);
		    Log.d("child event listener", m.getName() + " - song " + m.getSongID());
		    name.setText("name - " + m.getName());
		    userList.add(m);
		  }

		  @Override public void onChildChanged(DataSnapshot snapshot, String previousChildName) { }

		  @Override public void onChildRemoved(DataSnapshot snapshot) { }

		  @Override public void onChildMoved(DataSnapshot snapshot, String previousChildName) { }

		  @Override public void onCancelled(FirebaseError error) { }
		});
	}
	
	protected void traverseFirebaseForValues()
	{
		
	}
	
}


































