package com.example.songbeacon;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class UserList extends ListActivity {

	public static final String beaconOneID = "55555";
	public Beacon assignedBeacon;

	public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	public static final String EXTRAS_BEACON = "extrasBeacon";

	private static final int REQUEST_ENABLE_BT = 1234;
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",
			null, null, null);

	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<String> usernames = new ArrayList<String>();

	private BeaconManager beaconManager;
	
	MediaPlayer mp;
	boolean isplaying = false;
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	String songselection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
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
		
		//get the firebase songselection for this device
		String username = prefs.getString("name", "");
		Firebase thisuserref = new Firebase("https://resplendent-fire-3957.firebaseio.com/" + username + "/songID");
		
		thisuserref.addValueEventListener(new ValueEventListener() {
		  @Override
		  public void onDataChange(DataSnapshot snapshot) {
			  //get the songselection for this user
			  songselection = snapshot.getValue().toString();
		  }

		  @Override
		  public void onCancelled(FirebaseError error) {
			  System.err.println("Listener was cancelled");
		  }
		});
		
		
		
		

		setListAdapter(new ArrayAdapter<String>(UserList.this,android.R.layout.simple_list_item_1, usernames));

		
	    beaconManager = new BeaconManager(this);
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
	      @Override
	      public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
	        // Note that results are not delivered on UI thread.
	        runOnUiThread(new Runnable() {
	          @Override
	          public void run() {
	            // Note that beacons reported here are already sorted by estimated
	            // distance between device and beacon.
	            getActionBar().setSubtitle("Found beacons: " + beacons.size());
	            for(int i = 0; i < beacons.size(); i++) {
	            	Beacon dummy = beacons.get(i);
	            	Log.d("beacon", "beacon " + i + " found");
	            	Log.d("beacon", "beacon info - minor" + String.valueOf(dummy.getMinor()) + " major " + String.valueOf(dummy.getMajor()) + " name " + dummy.getName());
	            	if(String.valueOf(beacons.get(i).getMinor()).equals(beaconOneID))
	            	{
	            	
	            		Log.d("my beacon", "my beacon");
	            		mp = MediaPlayer.create(UserList.this, R.raw.td4w);
			        	mp.setLooping(true);
			        	mp.start();
	            	}
	            }
	          }
	        });
	      }
	    });
	}

	  @Override
	  protected void onDestroy() {
	    beaconManager.disconnect();

	    super.onDestroy();
	  }
	  
	  @Override
	  protected void onStart() {
	    super.onStart();

	    // Check if device supports Bluetooth Low Energy.
	    if (!beaconManager.hasBluetooth()) {
	      Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
	      return;
	    }

	    // If Bluetooth is not enabled, let user enable it.
	    if (!beaconManager.isBluetoothEnabled()) {
	      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	      startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	    } else {
	      connectToService();
	    }
	  }
	
	  private void connectToService() {
		    getActionBar().setSubtitle("Scanning...");
		    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
		      @Override
		      public void onServiceReady() {
		        try {
		          beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
		        } catch (RemoteException e) {
		          Toast.makeText(UserList.this, "Cannot start ranging, something terrible happened",
		              Toast.LENGTH_LONG).show();
		          Log.e("fail", "Cannot start ranging", e);
		        }
		      }
		    });
		  }
	  
	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_ENABLE_BT) {
	      if (resultCode == Activity.RESULT_OK) {
	        connectToService();
	      } else {
	        Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
	        getActionBar().setSubtitle("Bluetooth not enabled");
	      }
	    }
	    super.onActivityResult(requestCode, resultCode, data);
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
