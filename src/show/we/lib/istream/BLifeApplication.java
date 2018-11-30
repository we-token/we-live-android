/*
 * Copyright (C) 2011-2013 GUIGUI Simon, fyhertz@gmail.com
 * 
 * This file is part of Spydroid (http://code.google.com/p/spydroid-ipcamera/)
 * 
 * Spydroid is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.iavstream.blife;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;

import com.iavstream.utility.*;

public class BLifeApplication extends android.app.Application {

	public final static String TAG = "BLifeApplication";
	/** Key used in the SharedPreferences to store whether the mpegts server is enabled or not. */
	public final static String KEY_MPEGTS_ENABLED = "mpeg_ts_enabled";
	/** Key used in the SharedPreferences to store whether the rtsp server is enabled or not. */
	public final static String KEY_RTSP_ENABLED = "rtsp_enabled";
	/** Key used in the SharedPreferences for the port used by the mpegts server. */
	public final static String KEY_MPEGTS_ADDRESS = "mpegts_address";
	/** Key used in the SharedPreferences for the port used by the rtsp server. */
	public final static String KEY_RTSP_ADDRESS = "rtsp_address";
	/** Set this flag to true to disable the ads. */
	//public final boolean DONATE_VERSION = false;
	/** If the notification is enabled in the status bar of the phone. */
	public boolean notificationEnabled = true;	
	//audio parameter	
	public boolean streamaudioEnabled = false;
	public int audioBitrate = 64;//kbps
	//video parameter	
	public boolean streamvideoEnabled = false;
	public int videoCamSide = 1;
	public VideoQuality videoQuality = new VideoQuality(640,480,15,400000);
	//mpegts parameter
	public boolean mpegtsEnabled = false;
	public String mpegtsAddress;
	//rtsp parameter
	public boolean rtspEnabled = false;
    public String rtspAddress;
    public String sdpFilename;
    public String streamaddress;
    public  ViewPager mPager;
    public String FromName,FromAddress;
    public boolean jumpEnabled = false;

	private static BLifeApplication sApplication;

	@Override
	public void onCreate() {

		sApplication = this;
		super.onCreate();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		notificationEnabled = settings.getBoolean("notification_enabled", false);
        //1 get audio encoding parameter
		streamaudioEnabled  = settings.getBoolean("stream_audio", false);		
		if(streamaudioEnabled)	
		audioBitrate = Integer.parseInt(settings.getString("audio_bitrate",String.valueOf(audioBitrate)));
		//2 get video encoding parameter
		streamvideoEnabled  = settings.getBoolean("stream_video", false);		
		if(streamvideoEnabled)
		{
		  videoCamSide=Integer.parseInt(settings.getString("cam_side",String.valueOf(videoCamSide)));
		  // Read video quality settings from the preferences
		   videoQuality = new VideoQuality(
						settings.getInt("video_resX", videoQuality.resX),
						settings.getInt("video_resY", videoQuality.resY), 
						Integer.parseInt(settings.getString("video_framerate", String.valueOf(videoQuality.framerate))), 
						Integer.parseInt(settings.getString("video_bitrate", String.valueOf(videoQuality.bitrate/1000)))*1000);
		}
		//
		//3 get mpeg-ts parameter
		mpegtsEnabled  = settings.getBoolean(KEY_MPEGTS_ENABLED, false);		
		if(mpegtsEnabled)	
		mpegtsAddress =  settings.getString(KEY_MPEGTS_ADDRESS,String.valueOf(mpegtsAddress));
		//4  get rtsp parameter
		rtspEnabled  = settings.getBoolean(KEY_RTSP_ENABLED, false);		
		if(rtspEnabled)	
		{
		  rtspAddress =  settings.getString(KEY_RTSP_ADDRESS,String.valueOf(rtspAddress));		  
		  sdpFilename =  settings.getString("rtsp_sdp",String.valueOf(rtspAddress));
		
		}
		settings.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
	}

	public static BLifeApplication getInstance() {
		return sApplication;
	}

	private OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener = new OnSharedPreferenceChangeListener() {
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			
			if (key.equals("video_resX") || key.equals("video_resY")) {
				videoQuality.resX = sharedPreferences.getInt("video_resX", 0);
				videoQuality.resY = sharedPreferences.getInt("video_resY", 0);
			}

		    if (key.equals("video_framerate")) {
				videoQuality.framerate = Integer.parseInt(sharedPreferences.getString("video_framerate", "0"));
			}

			if (key.equals("video_bitrate")) {
				videoQuality.bitrate = Integer.parseInt(sharedPreferences.getString("video_bitrate", "0"))*1000;
			}

			if (key.equals("audio_bitrate") || key.equals("stream_audio")) {
				//1 get audio encoding parameter
				streamaudioEnabled  = sharedPreferences.getBoolean("stream_audio", false);		
				if(streamaudioEnabled)	
				audioBitrate = Integer.parseInt(sharedPreferences.getString("audio_bitrate",String.valueOf(audioBitrate)));
				
			}

			if (key.equals("stream_video") || key.equals("cam_side")) {
				streamvideoEnabled  = sharedPreferences.getBoolean("stream_video", false);		
				if(streamvideoEnabled)
				{					
				  videoCamSide=Integer.parseInt(sharedPreferences.getString("cam_side",String.valueOf(videoCamSide)));
				}
				
			}

			if (key.equals("notification_enabled")) {
				notificationEnabled  = sharedPreferences.getBoolean("notification_enabled", true);
			}
			
			if (key.equals("mpeg_ts_enabled") || key.equals("mpegts_address")) {
				//3 get mpeg-ts parameter
				mpegtsEnabled  = sharedPreferences.getBoolean(KEY_MPEGTS_ENABLED, false);		
				if(mpegtsEnabled)	
				mpegtsAddress = sharedPreferences.getString(KEY_MPEGTS_ADDRESS,String.valueOf(mpegtsAddress));
				
			}
			
			if (key.equals("rtsp_enabled") || key.equals("rtsp_address")) {
				rtspEnabled  = sharedPreferences.getBoolean(KEY_RTSP_ENABLED, false);		
				if(rtspEnabled)	
				{
				  rtspAddress =  sharedPreferences.getString(KEY_RTSP_ADDRESS,String.valueOf(rtspAddress));
				  sdpFilename =  sharedPreferences.getString("rtsp_sdp",String.valueOf(rtspAddress));
				
				}
				
			}

		}  
	};

}
