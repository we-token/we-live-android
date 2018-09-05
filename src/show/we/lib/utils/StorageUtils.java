package show.we.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 存储相关Utils总汇
 *
 * @author ll
 * @version 1.0.0
 */
public class StorageUtils {

    private static SharedPreferences sSharedPreferences;

    /**
     * 初始化SharedPreference
     *
     * @param context context
     */
    public static void initSharePreferences(Context context) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 获取SharedPreference存储的实例
     *
     * @return SharedPreference存储的实例
     */
    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    /**
     * close Storage
     */
    public static void close() {
    }
}
