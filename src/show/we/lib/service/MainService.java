package show.we.lib.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import show.we.sdk.util.EnvironmentUtils;
import show.we.sdk.util.SecurityUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Config;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.control.OnDataChangeObserver;
import show.we.lib.monitor.CallMonitor;
import show.we.lib.utils.CacheUtils;
import show.we.lib.utils.PromptUtils;
import show.we.lib.utils.RequestUtils;
import show.we.lib.utils.StorageUtils;

/**
 * MainService
 *
 * @author ll
 * @version 1.0.1
 */
public class MainService extends Service implements OnDataChangeObserver {

    private static final String TAG = "MainService";

    private final LocalBinder mBinder = new LocalBinder();

    /**
     * 绑定服务时的返回对象
     */
    public class LocalBinder extends Binder {
        /**
         * 获取服务本身
         *
         * @return 当前服务本身
         */
        public MainService getService() {
            return MainService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_FOREGROUND);
        FavStarOnlinePush.getInstance(this);
        CallMonitor.init(this);
        if (EnvironmentUtils.Config.isTestMode()) {
            DataChangeNotification.getInstance().addObserver(IssueKey.DOWNLOAD_COMPLETED, this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (StorageUtils.getSharedPreferences().contains(SharedPreferenceKey.ACCESS_TOKEN_OLD_KEY)) {
            StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.ACCESS_TOKEN_KEY
                    , SecurityUtils.RC4.encrypt(StorageUtils.getSharedPreferences().getString(SharedPreferenceKey
                    .ACCESS_TOKEN_OLD_KEY, ""))).commit();
            StorageUtils.getSharedPreferences().edit().remove(SharedPreferenceKey.ACCESS_TOKEN_OLD_KEY).commit();
        }

        String accessToken = StorageUtils.getSharedPreferences().getString(SharedPreferenceKey.ACCESS_TOKEN_KEY, "");
        if (!StringUtils.isEmpty(accessToken)) {
            accessToken = SecurityUtils.RC4.decrypt(accessToken);
            CacheUtils.getObjectCache().add(CacheObjectKey.ACCESS_TOKEN, accessToken);
            RequestUtils.requestUserInfoAfterLogin(this);
        }

        RequestUtils.requestGiftListWithImage();
        RequestUtils.requestMountMall();
        RequestUtils.requestMissionCount();

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FavStarOnlinePush.getInstance(this).stop();
        if (EnvironmentUtils.Config.isTestMode()) {
            DataChangeNotification.getInstance().removeObserver(this);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        CacheUtils.getObjectCache().notifyMemoryLow();
        CacheUtils.getImageCache().notifyMemoryLow();
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if (IssueKey.DOWNLOAD_COMPLETED.equals(issue)) {
            Object[] objects = (Object[]) o;
            String url = (String) objects[0];
            String path = (String) objects[1];
            Object tag = objects[2];
            if (path.startsWith(Config.getRingCacheFolderPath())) {
                String starName = (String) tag;
                PromptUtils.showToast(getString(R.string.ring_download_completed, starName), Toast.LENGTH_SHORT);
            }
        }
    }
}
