package show.we.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;
import show.we.sdk.util.ConstantUtils;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.R;
import show.we.lib.BaseApplication;
import show.we.lib.config.Config;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.config.UMengConfig;
import com.umeng.update.*;

/**
 * Created by CG on 13-10-28.
 *
 * @author ll
 * @version 3.0.0
 */
public class VersionUtils {

    /**
     * 检查更新
     *
     * @param context    上下文对象
     * @param isManually 是否为手动检查
     */
    public static void checkUpdate(final Context context, final boolean isManually) {

        UmengUpdateAgent.update(context);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                PromptUtils.dismissProgressDialog();
                switch (updateStatus) {
                    case 0: // has update
                        UmengUpdateAgent.showUpdateDialog(context, updateInfo);
                        StorageUtils.getSharedPreferences().edit()
                                .putBoolean(SharedPreferenceKey.IS_NEED_UPDATE, true).commit();
                        break;
                    case 1: // has no update
                        if (isManually) {
                            PromptUtils.showToast(R.string.not_updated, Toast.LENGTH_SHORT);
                        }
                        StorageUtils.getSharedPreferences().edit()
                                .putBoolean(SharedPreferenceKey.IS_NEED_UPDATE, false).commit();
                        break;
                    case 2: // none wifi
                        UmengUpdateAgent.showUpdateDialog(context, updateInfo);
                        break;
                    case 3: // time out
                        if (isManually) {
                            PromptUtils.showToast(R.string.time_out, Toast.LENGTH_SHORT);

                        }
                        break;
                    default:
                        break;
                }
            }
        });
        UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {
            /**
             * OnDownloadStart
             */
            @Override
            public void OnDownloadStart() {

            }
            /**
             * OnDownloadUpdate
             */
            @Override
            public void OnDownloadUpdate(int i) {

            }
            /**
             * OnDownloadEnd
             */
            @Override
            public void OnDownloadEnd(int result, String s) {
                String hint = result == 1 ? context.getString(R.string.download_succeed) : context.getString(R.string.download_failed);
                PromptUtils.showToast(hint, Toast.LENGTH_SHORT);
            }
        });
        if (isManually) {
            PromptUtils.showProgressDialog(context, (String) context.getResources().getText(R.string.wait));
        }
    }

    /**
     * 获取当前程序的版本号 
     * @param context 上下文对象
     * @return 版本名
     */
    public static String getVersionName(Context context) {
    	PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String version = null;
		try {
			packInfo = packageManager.getPackageInfo("com.rednovo.weibo",0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return version;
//        final int dateTimeLen = 11;
//        String appVersion = new EnvironmentUtils.Config().getAppVersion();
//        int length = appVersion.length() - dateTimeLen;
//        return (Config.getConnectToServerType() != Config.FORMAL_WS) ? context.getString(R.string.test_server_ws)
//                : (length > 0 ? appVersion.substring(0, length) : "") + new EnvironmentUtils.Config().getVersionName();
    }
    /**
     * 获取应用程序的版本名，这个版本名是写在Config里的，而非AndroidManifest.xml里的
     *
     * @param context 上下文对象
     * @return 版本名
     */
//    public static String getVersionName(Context context) {
//    	final int dateTimeLen = 11;
//    	String appVersion = new EnvironmentUtils.Config().getAppVersion();
//    	int length = appVersion.length() - dateTimeLen;
//    	return (Config.getConnectToServerType() != Config.FORMAL_WS) ? context.getString(R.string.test_server_ws)
//    			: (length > 0 ? appVersion.substring(0, length) : "") + new EnvironmentUtils.Config().getVersionName();
//    }

    /**
     * 获取应用程序的版本名，这个版本名是写在Config里的，而非AndroidManifest.xml里的
     *
     * @param context 上下文对象
     * @return 版本名
     */
    public static String getVersionCode(Context context) {
        final int dateTimeLen = 11;
        String appVersion = new EnvironmentUtils.Config().getAppVersion();
        return (Config.getConnectToServerType() != Config.FORMAL_WS) ? context.getString(R.string.test_server_ws)
                : appVersion.substring(0, appVersion.length() - dateTimeLen);
    }

    /**
     * checkUpdateAuto
     *
     * @param context context
     */
    public static void checkUpdateAuto(final Context context) {
        if (UMengConfig.getUpgraderTrigger(context)
                && UMengConfig.checkUpgradChannelValid(context) && UMengConfig.checkVersionCodeValid(context)) {
            VersionUtils.checkUpdate(context, false);
            UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
                @Override
                public void onClick(int status) {
                    switch (status) {
                        case UpdateStatus.Update:
                            ((Activity)context).finish();
                            break;
                        default:
                            BaseApplication.getApplication().exit();
                    }
                }
            });
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - StorageUtils.getSharedPreferences()
                .getLong(SharedPreferenceKey.PREVIOUS_CHECK_UPDATE_TIME_KEY, 0) > ConstantUtils.MILLS_PER_DAY) {
            VersionUtils.checkUpdate(context, false);
            StorageUtils.getSharedPreferences().edit()
                    .putLong(SharedPreferenceKey.PREVIOUS_CHECK_UPDATE_TIME_KEY, currentTime).commit();
        }
    }

}
