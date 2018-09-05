package show.we.lib.monitor;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import show.we.lib.ActivityManager;

import java.util.Set;

/**
 * 电话监控
 *
 * @author ll
 * @version 1.0.0
 */
public class CallMonitor {
    /**
     * 电话状态改变接口
     */
    public static interface OnCallStateChangeListener {

        /**
         * 电话状态改变
         *
         * @param status 电话当前状态
         */
        public void onPhoneStateChanged(int status);
    }

    /**
     * 这里必须使用Activity和Service的环境上下文，不可使用Application(回调不正常)
     * 初始化电话监听模块
     *
     * @param context context
     */
    public static void init(Context context) {
        ((TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE))
                .listen(sListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private static PhoneStateListener sListener = new PhoneStateListener() {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Set<Activity> activities = ActivityManager.instance().getActivities();
            for (Activity activity : activities) {
                if (activity instanceof OnCallStateChangeListener) {
                    ((OnCallStateChangeListener) activity).onPhoneStateChanged(state);
                }
            }
        }
    };
}


