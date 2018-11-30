

package com.iavstream.blife.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.iavstream.blife.BLifeApplication;
import com.iavstream.blife.R;
import com.iavstream.utility.CamParaUtil;
import com.iavstream.utility.OkCancelDialog;
import com.iavstream.utility.PowerStateReceiver;
import com.iavstream.utility.WireLessState;






	public class BLifeActivity extends FragmentActivity {

	static final public String TAG = "BLife_BLifeActivity";

	public final int HANDSET = 0x01;
	public final int TABLET = 0x02;

	// We assume that the device is a phone
	public int device = HANDSET;

	public  ViewPager mViewPager;
	private PowerManager.WakeLock mWakeLock;
	private SectionsPagerAdapter mAdapter;
	private SurfaceView mSurfaceView;
	private BLifeApplication mApplication;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//setHasOptionsMenu(true);
		mApplication = (BLifeApplication) getApplication();
		//操作系统版本检查
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			DisplayToast("你的手机版本小于4.0");
			this.finish();
			System.exit(0);
	    }
		//摄像头存在检查
		if(CamParaUtil.getInstance().getBackCamIndex()==-1){
			DisplayToast("后置摄像头不可用");
		}
        if(CamParaUtil.getInstance().getFrontCamIndex()==-1){
			DisplayToast("前置摄像头不可用");
		}		
		//电量检查
         PowerStateReceiver powerstate=new PowerStateReceiver();		
		 registerReceiver(powerstate, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));	
		
		//网络状态检查		
		WireLessState wireless=new WireLessState(this);		
		if(wireless.checkNetwork()==false)
		{
			final OkCancelDialog ok=new OkCancelDialog(this,"网路状态确认","请打开3G或者WIFI网络");
			ok.setOkClickListener(new OnClickListener() {
				@Override
    			public void onClick(View v) {					
    			if(android.os.Build.VERSION.SDK_INT > 10 )
    			{     //3.0以上打开设置界面
    			   startActivityForResult (new Intent(android.provider.Settings.ACTION_SETTINGS),0);
    			}
    			else
    			{
    			   startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS),0);
    			}
    			
    			ok.DialogHide();
    			
    		}});
			ok.setCancelClickListener(new OnClickListener() {
				@Override
    			public void onClick(View v) {   			
    			ok.DialogHide();
    			
    		}});			
			ok.DialogShow();		
		}
          

		setContentView(R.layout.blife);

		if (findViewById(R.id.handset_pager) != null) {
			// Handset detected !
			mAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
			mViewPager = (ViewPager) findViewById(R.id.handset_pager);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			mSurfaceView = (SurfaceView)findViewById(R.id.handset_camera_view);
		} else {
			 DisplayToast("BLife为手机设计，暂不支持平板");
			 System.out.println("blife exit");
             System.exit(0);
		}

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(2);
		mApplication.mPager=mViewPager;
		// Prevents the phone from going to sleep mode
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "com.iavstream.blife.wakelock");

	}

	public void onStart() {
		super.onStart();
		// Lock screen
		mWakeLock.acquire();
		if (mApplication.notificationEnabled) {
			Intent notificationIntent = new Intent(this, BLifeActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			Notification notification = builder.setContentIntent(pendingIntent)
					.setWhen(System.currentTimeMillis())
					.setTicker(getText(R.string.notification_title))
					.setSmallIcon(R.drawable.icon)
					.setContentTitle(getText(R.string.notification_title))
					.setContentText(getText(R.string.notification_content)).build();
			notification.flags |= Notification.FLAG_ONGOING_EVENT;
			((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).notify(0,notification);

		} else {
			removeNotification();
		}

	}

	@Override
	public void onStop() {
		super.onStop();
		if (mWakeLock.isHeld()) mWakeLock.release();
	}

	@Override
	public void onResume() {
		super.onResume();
		invalidateOptionsMenu();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override    
	public void onBackPressed() {
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(setIntent);
	}

	@Override    
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);	
		MenuItemCompat.setShowAsAction(menu.findItem(R.id.quit), 1);
		MenuItemCompat.setShowAsAction(menu.findItem(R.id.options), 1);
		SetMenubackgroudcolor();
		return true;
	}
	

    private void SetMenubackgroudcolor() {

			LayoutInflater inflater = LayoutInflater.from(this);
			if (inflater.getFactory() != null) {
				inflater = inflater.cloneInContext(this);
    	}
    	inflater.setFactory(new Factory() {  
                public View onCreateView(String name, Context context, AttributeSet attrs) {
                    if(name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")  
                            || name.equalsIgnoreCase("com.android.internal.view.menu.ListMenuItemView")){  
                        try {  
                            LayoutInflater f = getLayoutInflater();                             
                            final View view = f.createView( name, null, attrs );                             
                            new Handler().post( new Runnable() {                                 
                                public void run () {
                                    view.setBackgroundColor(Color.parseColor("#F6F9FE"));//设置背景色                                 
                                    }  
                                });  
                               
                        } catch (InflateException e) {  
                            e.printStackTrace();  
                        } catch (ClassNotFoundException e) {                           
                            e.printStackTrace();  
                        }  
                    }  
                    return null;  
                }

			
            });  
        }
	

	@Override    
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.options:
			intent = new Intent(this.getBaseContext(),OptionsActivity.class);
			startActivityForResult(intent, 0);
			return true;
		case R.id.quit:
			quitBLife();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private void quitBLife() {
		
		final OkCancelDialog ok=new OkCancelDialog(this,"退出","您确定要退出吗？");
		ok.setOkClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {					
				if (mApplication.notificationEnabled) removeNotification();  
				ok.DialogHide();
                System.out.println("the blife exit");
                System.exit(0);	
		}});

		ok.setCancelClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {   			
			ok.DialogHide();
			
		}});			
		ok.DialogShow();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){

        }
        return true;
    }

	public ViewPager GetViewPager(ViewPager vp)
	{ 
		return    mViewPager;
		
	}

	private void removeNotification() {
		((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
	}

	 public void DisplayToast(String str)
	 {
		    	Toast toast = Toast.makeText(this, str,Toast.LENGTH_SHORT);
		    	toast.setGravity(Gravity.BOTTOM, 0, 220);
		    	toast.show();
	}

	class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			if (device == HANDSET) {
				switch (i) {
				case 0: return new PlayerFragment();
				case 1: return new PlayListFragment();
				case 2: return new HandsetFragment();
				case 3: return new PreviewFragment();
				case 4: return new ShareFragment();
				
				}
			} else {
				/*switch (i) {
				case 0: return new TabletFragment();
				case 1: return new ShareFragment();
				} */
			}
			return null;
		}

		@Override
		public int getCount() {
			return device==HANDSET ? 5 : 2;
		}

		

		@Override
		public CharSequence getPageTitle(int position) {
			if (device == HANDSET) {
				switch (position) {
				case 0: return getString(R.string.page0);
				case 1: return getString(R.string.page1);
				case 2: return getString(R.string.page2);
				case 3: return getString(R.string.page3);
				case 4: return getString(R.string.page4);
				}        		
			} else {
				switch (position) {
			case 0: return getString(R.string.page0);
			case 1: return getString(R.string.page2);
		}
	}
			return null;
		}

	}

}