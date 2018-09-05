package show.we.lib;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import show.we.sdk.util.EnvironmentUtils;
import com.umeng.analytics.MobclickAgent;
import show.we.lib.config.Config;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.service.MainService;
import show.we.lib.utils.*;
import show.we.lib.widget.dialog_string.DialogString;


/**
 * 应用程序类
 *
 * @author ll
 * @version 1.0.0
 */
public class BaseApplication extends Application {
    /**
     * Application实例
     */
    protected static BaseApplication sApplication;

    /**
     * 退出程序的延迟
     */
    private static final int KILL_SELF_DELAY = 200; //ms

    /**
     * 应用程序类型（平板 或者 手机）
     */
    public static final String APPLICAITON_TYPE = "application_type";

    /**
     * 手机
     */
    public static final int PHONE_TYPE = 0;

    /**
     * 平板
     */
    public static final int PAD_TYPE = 1;

    @Override
    public void onCreate() {
        sApplication = this;

        EnvironmentUtils.init(this);

        DisplayUtils.init(this);

        PromptUtils.init(this);

        Config.init(this);

        CacheUtils.init(this);

        MobclickAgent.updateOnlineConfig(this);

        DialogString.init(this);

        GifCacheUtils.init();

        StorageUtils.initSharePreferences(this);

        StorageUtils.getSharedPreferences().edit().remove(SharedPreferenceKey.WIFI_TIP_DONE).apply();

        PerformanceCheckUtils.init();

        Config.setIsSendGiftMarquee(StorageUtils.getSharedPreferences().getBoolean(SharedPreferenceKey.SEND_GIFT_MARQUEE, true));
    }

    /**
     * 获取应用实例
     *
     * @return 应用实例
     */
    public static BaseApplication getApplication() {
        return sApplication;
    }

    /**
     * 在millSeconds后退出
     *
     * @param millSeconds millSeconds
     */
    public static void exitInMillSeconds(long millSeconds) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getApplication().exit();
            }
        }, millSeconds);
    }

    /**
     * 退出应用程序
     */
    public void exit() {
        ActivityManager.instance().finishActivities();
        exitProcess();
    }

    /**
     * exiteProcess
     */
    public void exitProcess() {
        Intent i = new Intent(sApplication, MainService.class);
        sApplication.stopService(i);
        StorageUtils.close();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                CacheUtils.getImageCache().clearDiskCache(Config.CLEAR_CACHE_INTERVAL);
                CacheUtils.close();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, KILL_SELF_DELAY);
    }
}

