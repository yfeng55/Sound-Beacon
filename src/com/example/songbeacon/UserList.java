package com.example.songbeacon;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class UserList extends ListActivity {

	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<String> usernames = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("EEE", "In the listactivity");

		// store all firebase users into the users[] array
		Firebase ref = new Firebase("https://resplendent-fire-3957.firebaseio.com/");

		ref.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

				User u = snapshot.getValue(User.class);

				users.add(u);
				usernames.add(u.getName());

				// DEBUGGING: log all values in users[]
				for (User user : users) {
					Log.i("user added: ", user.getName());
				}

				setListAdapter(new ArrayAdapter<String>(UserList.this, android.R.layout.simple_list_item_1, usernames));
			}

			//we're not using this stuff currently
			@Override
			public void onChildChanged(DataSnapshot snapshot,String previousChildName) {}
			@Override
			public void onChildRemoved(DataSnapshot snapshot) {}
			@Override
			public void onChildMoved(DataSnapshot snapshot,String previousChildName) {}
			@Override
			public void onCancelled(FirebaseError error) {}
			
		});

		setListAdapter(new ArrayAdapter<String>(UserList.this,android.R.layout.simple_list_item_1, usernames));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		//Log.i("EEE", "clicked an item");

		Intent i = new Intent(this, EditUser.class);

		// get the user object for the user that was selected in the list
		User selecteduser = users.get(position);

		// put the selected user object in the newly created intent
		//i.putExtra("user", selecteduser);
		
		i.putExtra("selecteduser", selecteduser);
		startActivity(i);
	}

}
