package show.we.lib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import show.we.lib.R;
import show.we.lib.config.StarRoomKey;
import show.we.lib.ui.LiveCommonData;

/**
 * 比较独立，抽离
 * Created by CG on 13-10-24.
 *
 * @author ll
 * @version 3.0.0
 */
public class ShortCutUtils {

    private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String ACTION_UNINSTALL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    private static final String LAUNCHER_URL = "content://com.android.launcher.settings/favorites?notify=true";
    private static final String LAUNCHER_URL_2 = "content://com.android.launcher2.settings/favorites?notify=true";
    private static final float DEFAULT_ICON_WIDTH = 48.0f;

    /**
     * 检查桌面是否有图标
     *
     * @param context 上下文
     * @return 是否存在桌面图标
     */
    public static boolean hasShortCut(Context context) {
        String url = Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO ? LAUNCHER_URL
                : LAUNCHER_URL_2;

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",
                new String[]{context.getString(R.string.app_name)}, null);
        return cursor != null && cursor.moveToFirst();
    }

    /**
     * 是否需要删除桌面图标
     *
     * @param context 上下文
     * @return 是否需要删除桌面图标
     */
    public static boolean needDelShortCut(Context context) {
        String url = Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO ? LAUNCHER_URL
                : LAUNCHER_URL_2;

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",
                new String[]{context.getString(R.string.del_app_name)}, null);
        return cursor == null || cursor.moveToFirst(); //cursor为null的时候表示找不到该ContentProvider，这时无法判断图标是否存在，只好默认它已经存在
    }

    /**
     * deleteShortCut
     *
     * @param context       context
     * @param activityClass 快捷方式启动的activity
     * @param shortcutName  快捷方式名称
     */
    public static void deleteShortCut(Context context, Class<?> activityClass, String shortcutName) {
        Intent shortcut = new Intent(ACTION_UNINSTALL_SHORTCUT);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, activityClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        context.sendBroadcast(shortcut);
    }

    /**
     * 创建快捷方式
     *
     * @param context       上下文
     * @param activityClass 快捷方式启动的activity
     */
    public static void createLaunchShortcut(Context context, Class<?> activityClass) {
        Intent pendingIntent = new Intent(Intent.ACTION_MAIN);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        pendingIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        pendingIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        pendingIntent.setClass(context.getApplicationContext(), activityClass);

        createShortcut(context, context.getString(R.string.app_name), R.drawable.app_icon, pendingIntent);
    }

    /**
     * 添加主播桌面快捷方式
     * 桌面快捷方式icon必须是1:1，否则在有些机型上很难看；但是封面图片宽高比是4:3，所以需要对图片进行中间剪裁并缩小尺寸
     * @param context context
     * @param bitmap bitmap
     * @param activityClass activityClass
     * @return true - 发送成功
     */
    public static boolean addStarShortCut(Context context, Bitmap bitmap, Class<?> activityClass) {
        int iconWidth = (int) (DEFAULT_ICON_WIDTH * DisplayUtils.getDensity());
        Bitmap iconBitmap = ImageUtils.centerCorpBitmap(bitmap, iconWidth);
        if (iconBitmap == null) {
            return false;
        }
        
        Intent pendingIntent = new Intent(Intent.ACTION_MAIN);
        pendingIntent.setClass(context.getApplicationContext(), activityClass);
        pendingIntent.putExtra(StarRoomKey.STAR_ID_KEY, LiveCommonData.getRoomId());
        pendingIntent.putExtra(StarRoomKey.ROOM_ID_KEY, LiveCommonData.getRoomId());
        pendingIntent.putExtra(StarRoomKey.IS_LIVE_KEY, true);
        pendingIntent.putExtra(StarRoomKey.STAR_NICK_NAME_KEY, LiveCommonData.getStarName());
        pendingIntent.putExtra(StarRoomKey.STAR_PIC_URL_KEY, LiveCommonData.getStarPic());
        pendingIntent.putExtra(StarRoomKey.ROOM_COVER, LiveCommonData.getRoomCover());
        pendingIntent.putExtra(StarRoomKey.STAR_LEVEL_KEY, LiveCommonData.getStarLevel());
        pendingIntent.putExtra(StarRoomKey.VISITOR_COUNT_KEY, LiveCommonData.getVisitorCount());
        pendingIntent.putExtra(StarRoomKey.IS_FROM_UC, LiveCommonData.isFromUc());

        createShortcut(context, LiveCommonData.getStarName(), iconBitmap, pendingIntent);
        return true;
    }

    /**
     * createLaunchShortcut
     * @param context context
     * @param shortcutName shortcutName
     * @param shortcutIcon shortcutIcon
     * @param pendingIntent pendingIntent
     */
    public static void createShortcut(Context context, String shortcutName, Bitmap shortcutIcon, Intent pendingIntent) {
        createShortcut(context, shortcutName, shortcutIcon, 0, pendingIntent);
    }

    /**
     * createLaunchShortcut
     * @param context context
     * @param shortcutName shortcutName
     * @param shortcutIconRes shortcutIconRes
     * @param pendingIntent pendingIntent
     */
    public static void createShortcut(Context context, String shortcutName, int shortcutIconRes, Intent pendingIntent) {
        createShortcut(context, shortcutName, null, shortcutIconRes, pendingIntent);
    }

    private static void createShortcut(Context context, String shortcutName, Bitmap shortcutIcon, int shortcutIconRes, Intent pendingIntent) {
        Intent shortcutIntent = new Intent(ACTION_INSTALL_SHORTCUT);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        if (shortcutIcon != null) {
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, shortcutIcon);
        } else {
            Intent.ShortcutIconResource shortcutIconResource = Intent.ShortcutIconResource.fromContext(context, shortcutIconRes);
            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortcutIconResource);
        }
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, pendingIntent);
        shortcutIntent.putExtra("duplicate", false);
        context.sendBroadcast(shortcutIntent);
    }
}
