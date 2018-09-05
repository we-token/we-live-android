package show.we.lib.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.view.WindowManager;

/**
 * Created by CG on 14-1-1.
 *
 * @author ll
 * @version 3.3.0
 */
public class ScreenUtils {

    private static PowerManager.WakeLock mWakeLock;

    /**
     * turnScreenOn
     *
     * @param context context
     */
    public static void turnScreenOn(Context context) {
        mWakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "BelleClock");
        mWakeLock.acquire();
    }

    /**
     * turnScreenOff
     */
    public static void turnScreenOff() {
        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }

    private static KeyguardManager.KeyguardLock mKeyguardLock;

    /**
     * 去掉键盘锁
     *
     * @param context context
     */
    public static void disableKeyguard(Context context) {
        mKeyguardLock = ((KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE))
                .newKeyguardLock("");
        mKeyguardLock.disableKeyguard();
    }

    /**
     * 恢复键盘锁
     */
    public static void reenableKeyguard() {
        if (mKeyguardLock != null) {
            mKeyguardLock.reenableKeyguard();
        }
    }

    /**
     * 设置去全屏
     * @param activity activity
     */
    public static void enableFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    /**
     * 设置退出全屏
     * @param activity activity
     */
    public static void disableFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
