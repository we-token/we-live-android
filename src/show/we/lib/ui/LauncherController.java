package show.we.lib.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.R;
import show.we.lib.config.Config;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.utils.CacheUtils;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.StorageUtils;

/**
 * Created by Administrator on 13-7-16.
 *
 * @author ll
 * @version 1.0.0
 */
public class LauncherController {

    private static final int MESSAGE_DELAY_LAUNCH = 1;
    private static final int MESSAGE_DELAY_LAUNCH_TIME = 1000;

    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mContext.startActivity(new Intent(mContext, mActivityCls));
            mContext.finish();
        }
    };

    private Activity mContext;
    private Class mActivityCls;
    private Class mServiceCls;

    /**
     * LauncherController
     *
     * @param context     context
     * @param activityCls class
     * @param serviceCls  serviceCls
     */
    public LauncherController(Activity context, Class activityCls, Class serviceCls) {
        mContext = context;
        mActivityCls = activityCls;
        mServiceCls = serviceCls;
    }

    /**
     * prepareLaunch
     */
    public void prepareLaunch() {
        if (new EnvironmentUtils.Config().isTestMode()) {
            showSelectModeDialog();
        } else {
            if (StorageUtils.getSharedPreferences().getInt(SharedPreferenceKey.LAST_CONNECT_SERVER_TYPE_KEY
                    , Config.FORMAL_WS) != Config.FORMAL_WS) {
                clearCache();
                LoginUtils.logout();
            }
            mContext.startService(new Intent(mContext, mServiceCls));
            mHandle.sendEmptyMessageDelayed(MESSAGE_DELAY_LAUNCH, MESSAGE_DELAY_LAUNCH_TIME);
        }
    }

    /**
     * stop
     */
    public void stop() {
        mHandle.removeMessages(MESSAGE_DELAY_LAUNCH);
    }

    private void showSelectModeDialog() {
        new AlertDialog.Builder(mContext).setTitle(mContext.getResources().getString(R.string.select_mode)).setItems(
                new String[]{mContext.getResources().getString(R.string.test_server_ws)
                        , mContext.getResources().getString(R.string.formal_server_ws)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Config.setConnectToTestServer(which);
                boolean currentIsOfficial = which == Config.FORMAL_WS;
                int lastConnectType = StorageUtils.getSharedPreferences().getInt(SharedPreferenceKey.LAST_CONNECT_SERVER_TYPE_KEY, Config.FORMAL_WS);
                boolean lastIsOfficial = lastConnectType == Config.FORMAL_WS;
                if (currentIsOfficial != lastIsOfficial) {
                    clearCache();
                    LoginUtils.logout();
                }
                StorageUtils.getSharedPreferences().edit().putInt(SharedPreferenceKey.LAST_CONNECT_SERVER_TYPE_KEY, which).commit();

                mContext.startService(new Intent(mContext, mServiceCls));
                mHandle.sendEmptyMessageDelayed(MESSAGE_DELAY_LAUNCH, MESSAGE_DELAY_LAUNCH_TIME);
                dialog.dismiss();
            }
        }).setCancelable(false).show();
    }

    private void clearCache() {
        CacheUtils.getImageCache().clearDiskCache(0);

        CacheUtils.getObjectCache().clear();

        StorageUtils.getSharedPreferences().edit().clear().apply();
    }
}
