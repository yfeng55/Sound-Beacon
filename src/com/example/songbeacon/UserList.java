package com.example.songbeacon;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class UserList extends ListActivity{

	//string array of users
	private ArrayList<String> users = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i("EEE", "In the listactivity");
		
		//store all firebase users into the users[] array
		Firebase ref = new Firebase("https://resplendent-fire-3957.firebaseio.com/");
		ref.addChildEventListener(new ChildEventListener() {
		  @Override
		  public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		    
			  User u = snapshot.getValue(User.class);
		    
		    //Log.i("stored user", u.getName());
		    users.add(u.getName());
		    
		    //log all values in users[]
		    for (String user : users){
		        Log.i("user added: ", user);
		    }
		    
		    setListAdapter(new ArrayAdapter<String>(UserList.this, android.R.layout.simple_list_item_1, users));
		  }
		  
		  @Override public void onChildChanged(DataSnapshot snapshot, String previousChildName) { }

		  @Override public void onChildRemoved(DataSnapshot snapshot) { }

		  @Override public void onChildMoved(DataSnapshot snapshot, String previousChildName) { }

		  @Override public void onCancelled(FirebaseError error) { }
		});
		
		
		//make menu activity full screen
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	    setListAdapter(new ArrayAdapter<String>(UserList.this, android.R.layout.simple_list_item_1, users));
		
	}
	
}


