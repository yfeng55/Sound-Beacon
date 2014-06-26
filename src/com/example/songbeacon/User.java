package com.example.songbeacon;

import android.telephony.TelephonyManager;

public class User 
{
	private String name;
	private String deviceID;
	private String songID;
	private TelephonyManager tMan;
	
	public User()
	{
		name = "";
		deviceID = tMan.getDeviceId();
		songID = "";
	}
	
	public User(String name)
	{
		this.name = name;
		deviceID = tMan.getDeviceId();
		songID = "";
	}

	public void setName(String newName){
		this.name = newName;
	}
	public void setDeviceID(String newDeviceID){
		this.deviceID = newDeviceID;
	}
	public void setSongID(String newSongID){
		this.songID = newSongID;
	}
	
	/**
	 *	Getters 
	 */
	public String getName()
	{
		return name;
	}
	public String getDeviceID()
	{
		return deviceID;
	}
	public String getSongID()
	{
		return songID;
	}
}
