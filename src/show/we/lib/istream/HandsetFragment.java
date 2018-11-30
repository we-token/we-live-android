

package com.iavstream.blife.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.iavstream.blife.BLifeApplication;
import com.iavstream.blife.R;


public class HandsetFragment extends Fragment {

	public final static String TAG = "HandsetFragment";
	private TextView mVersion;
    private BLifeApplication mApplication;
    Context mContext;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication  = (BLifeApplication) getActivity().getApplication();
		mContext = mApplication.getApplicationContext();
	}
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.main,container,false);
        mVersion = (TextView)rootView.findViewById(R.id.version);
        return rootView ;
    }
	
	@Override
    public void onStart() {
    	super.onStart();
        try {
			mVersion.setText("v"+mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0 ).versionName);
		} catch (Exception e) {
			mVersion.setText("v???");
		}
    	
    }
	@Override
    public void onPause() {
	super.onPause();
	getActivity().unregisterReceiver(mWifiStateReceiver);
}
	
	@Override
    public void onResume() {
		super.onResume();
    	getActivity().registerReceiver(mWifiStateReceiver,new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
    }
    // BroadcastReceiver that detects wifi state changements
    private final BroadcastReceiver mWifiStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        	
        } 
    };
}
