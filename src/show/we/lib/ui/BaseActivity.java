package show.we.lib.ui;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import show.we.lib.R;
import show.we.lib.ActivityManager;
import show.we.lib.cloudapi.ErrorCode;
import show.we.lib.config.Enums;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.ObserverGroup;
import show.we.lib.model.UserInfoResult;
import show.we.lib.service.MainService;
import show.we.lib.union.PostUnion;
import show.we.lib.utils.InputMethodUtils;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.PromptUtils;
import show.we.lib.utils.RequestUtils;
import show.we.lib.utils.StorageUtils;
import show.we.lib.utils.UserInfoUtils;
import show.we.lib.widget.abc_pull_to_refresh.base.PullToRefreshAttacher;
import show.we.lib.widget.dialog.StandardDialog;
import show.we.sdk.util.EnvironmentUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author ll
 * @version 1.0.0
 */
public class BaseActivity extends ActionBarActivity implements OnAuthCodeConfirmListener
        , OnDrawBonusListener, PullToRefreshAttacher.OnPullToRefreshAttacherEventListener {

    /** mObserverGroup */
    protected ObserverGroup mObserverGroup;
    /** mAutoRemoveGroup */
    protected boolean mAutoRemoveGroup = true;
    private int mUsableHeightPrevious;


    /**
     * actionbar
     */
    protected ActionBar mActionBar;
    /**
     * 当前领取任务的任务id
     */
//    protected String mCurMissionId;
    protected int mCurMissionId;
    private MainService mMainService;
    private Set<PullToRefreshAttacher> mPullToRefreshAttacherSet = new HashSet<PullToRefreshAttacher>();
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMainService = null;
            BaseActivity.this.onMainServiceDisconnected();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMainService = ((MainService.LocalBinder) service).getService();
            BaseActivity.this.onMainServiceConnected();
        }
    };

    /**
     * 获取当前activity的主题资源id(利用反射)
     * @return 当前activity的主题资源id
     */
    public int getThemeResID() {
        try {
            ContextThemeWrapper wrapper = this;
            Class<?> clazz = wrapper.getClass();
            Method method = clazz.getMethod("getThemeResId");
            return (Integer) method.invoke(wrapper);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Enums.Theme getPreferenceTheme() {
        int index = StorageUtils.getSharedPreferences().getInt(SharedPreferenceKey.THEME, Enums.Theme.NORMAL.ordinal());
        return Enums.Theme.values()[index];
    }

    private void setAppTheme(Enums.Theme theme) {
        if (theme != Enums.Theme.NORMAL) {
            int themeResId = getThemeResID();
            int index = Enums.Theme.NORMAL.indexOf(themeResId);
            if (index >= 0 && index < theme.getResIDArray().length) {
                setTheme(theme.getResIDArray()[index]);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppTheme(getPreferenceTheme());
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        ActivityManager.instance().onCreate(this);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        /**  强制显示logo,为解决小米1手机logo显示不出来问题 **/
        ImageView actionbarLogo = (ImageView) findViewById(android.R.id.home);
        if (actionbarLogo != null) {
            actionbarLogo.setVisibility(View.GONE);
            actionbarLogo.setImageResource(R.drawable.actionbar_icon_layer);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (InputMethodUtils.isInputMethodShowing()) {
                InputMethodUtils.hideSoftInput(this);
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        ActivityManager.instance().onDestroy(this);
        if (mAutoRemoveGroup) {
            removeObserverGroup();
        }
    }

    protected void removeObserverGroup() {
        if (mObserverGroup != null) {
            DataChangeNotification.getInstance().removeObserver(mObserverGroup);
            mObserverGroup = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.instance().onPause(this);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.instance().onResume(this);
        //友盟渠道
        MobclickAgent.onResume(this,"53cce49556240bbd9b13e1af", (String) EnvironmentUtils.GeneralParameters.parameters().get("f"));

        if (isConnected()) {
            onPrepared();
        }
        SharedPreferences sharedPreferences= getApplication().getSharedPreferences("timeforlogin",
				 Activity.MODE_PRIVATE); 
		 String isOnceBackstage =sharedPreferences.getString("isOnceBackstage", "");
		 if(isOnceBackstage=="1"){
			 boolean isAlreadyLogin = LoginUtils.isAlreadyLogin();
			 SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy/MM/dd    HH:mm:ss");       
     		 String date = sDateFormat.format(new    java.util.Date());
     		 SharedPreferences mySharedPreferences = getApplication().getSharedPreferences("timeforlogin",
     				 Activity.MODE_PRIVATE);
     		 Editor edit = mySharedPreferences.edit();
     		 edit.putString("logintime", date);
     		 edit.commit();
			 if(isAlreadyLogin){
	            	PostUnion postunion = new PostUnion();
	            	UserInfoResult mUserInfoResult = UserInfoUtils.getUserInfo();
	    				try {
							postunion.unionRequest(getApplication(), Integer.parseInt(String.
									valueOf(mUserInfoResult.getData().getId())), "dau","");
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            }else{
	            	PostUnion postunion = new PostUnion();
	    				try {
							postunion.unionRequest(getApplication(), 0, "dau","");
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    			} 
			 Editor edit2 = mySharedPreferences.edit();
     		 edit2.putString("isOnceBackstage", "0");
     		 edit2.commit();
		 }
    }

    /**
     * 检测软键盘弹出事件
     * @param activity activity
     */
    public void detectSoftKeyBoardEvent(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        final View childOfContent = content.getChildAt(0);
        if (childOfContent != null && childOfContent.getViewTreeObserver() != null) {
            childOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int usableHeightNow = InputMethodUtils.computeUsableHeight(childOfContent);
                    if (usableHeightNow != mUsableHeightPrevious) {
                        int usableHeightSansKeyboard = childOfContent.getRootView().getHeight();
                        int heightDifference = usableHeightSansKeyboard - usableHeightNow;
                        if (heightDifference > (usableHeightSansKeyboard / 4)) {   // keyboard probably just became visible
                            InputMethodUtils.setIsInputMethodShowing(true);
                            getWindow().getDecorView().setBackgroundResource(R.drawable.white);
                        } else {    // keyboard probably just became hidden
                            InputMethodUtils.setIsInputMethodShowing(false);
                            getWindow().getDecorView().setBackgroundResource(0);
                        }
                        mUsableHeightPrevious = usableHeightNow;
                    }
                }
            });
        }
    }

    protected void onPrepared() {
    }

    /**
     * 获取主服务
     *
     * @return 主服务
     */
    public final MainService getMainService() {
        return mMainService;
    }

    /**
     * 是否已经连接到服务
     *
     * @return true - 已经连接到服务
     */
    public final boolean isConnected() {
        return mMainService != null;
    }

    protected void onMainServiceConnected() {
        onPrepared();
    }

    protected void onMainServiceDisconnected() {
    }

    @Override
    public void onAuthCodeConfirmed(String authCode) {
    	//任务id变更
        RequestUtils.requestDrawBonus(mCurMissionId, /*authCode, */this);
        PromptUtils.showProgressDialog(this, getString(R.string.drawing));
    }

    @Override
    public void onDrawBonusError(int code) {
        if (code == ErrorCode.ERROR_NEED_VARIFY_EMAIL) {
            StandardDialog exitDialog = new StandardDialog(this);
            exitDialog.setContentText(R.string.please_varify_email);
            exitDialog.show();
        } else {
            PromptUtils.showToast(code == ErrorCode.AUTH_CODE_ERROR ? R.string.auth_code_error
                    : R.string.draw_bonus_fail, Toast.LENGTH_SHORT);
        }
        PromptUtils.dismissProgressDialog();
    }

    @Override
    public void onDrawBonusSuccess() {
        PromptUtils.dismissProgressDialog();
        PromptUtils.showToast(R.string.get_reward_succeed, Toast.LENGTH_SHORT);
    }

    @Override
    public void onAddAttacher(PullToRefreshAttacher attacher) {
        mPullToRefreshAttacherSet.add(attacher);
    }

    @Override
    public Set<PullToRefreshAttacher> getAttacherSet() {
        return mPullToRefreshAttacherSet;
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	ActivityManager a = ActivityManager.instance();
    	if(a.getLastActivity()==this){
    		if (!isAppOnForeground()) {
        		SharedPreferences mySharedPreferences = getApplication().getSharedPreferences("timeforlogin",
        				 Activity.MODE_PRIVATE); 
        		 Editor edit = mySharedPreferences.edit();
        		 edit.putString("isOnceBackstage", "1");
        		 edit.commit();
        		boolean isAlreadyLogin = LoginUtils.isAlreadyLogin();
        		if(isAlreadyLogin){
                	PostUnion postunion = new PostUnion();
                	UserInfoResult mUserInfoResult = UserInfoUtils.getUserInfo();
        				try {
    						postunion.unionRequest(getApplication(), Integer.parseInt(String.
    								valueOf(mUserInfoResult.getData().getId())), "logout","");
    					} catch (NumberFormatException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					} catch (SocketException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
                }else{
                	PostUnion postunion = new PostUnion();
        				try {
    						postunion.unionRequest(getApplication(), 0, "logout","");
    					} catch (SocketException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
        			} 
        	} 
    	}
    }
    public boolean isAppOnForeground() {  
        // Returns a list of application processes that are running on the  
        // device  
           
        android.app.ActivityManager activityManager = (android.app.ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);  
        String packageName = getApplicationContext().getPackageName();  

        List<RunningAppProcessInfo> appProcesses = activityManager  
                        .getRunningAppProcesses();  
        if (appProcesses == null)  
                return false;  

        for (RunningAppProcessInfo appProcess : appProcesses) {  
                // The name of the process that this object is associated with.  
                if (appProcess.processName.equals(packageName)  
                                && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {  
                        return true;  
                }  
        }  

        return false;  
}  
}
