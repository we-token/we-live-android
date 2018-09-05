package show.we.lib.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import show.we.lib.widget.dialog.LoadingPromptDialog;
import show.we.lib.widget.dialog.StandardDialog;

/**
 * 提示Util总汇
 *
 * @author ll
 * @version 1.0.0
 */
public class PromptUtils {

    private static Context sContext; //Application 的context
    private static Toast sToast;
    private static LoadingPromptDialog sProgressDialog;
    private static StandardDialog sTokenExpiredDialog;

    /**
     * 初始化
     *
     * @param context context
     */
    public static void init(Context context) {
        sContext = context;
        sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个Toast提示
     *
     * @param message  提示信息
     * @param duration 显示时间
     */
    public static void showToast(String message, int duration) {
        sToast.setText(message);
        sToast.setDuration(duration);
        sToast.show();
    }

    /**
     * 显示一个Toast提示
     *
     * @param resId    提示信息资源Id
     * @param duration 显示时间
     */
    public static void showToast(int resId, int duration) {
        showToast(sContext.getString(resId), duration);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context 上下文对象
     * @param resId   提示信息资源ID
     */
    public static synchronized void showProgressDialog(Context context, int resId) {
        showProgressDialog(context, context.getResources().getString(resId), true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context 上下文对象
     * @param message 提示信息
     */
    public static synchronized void showProgressDialog(Context context, String message) {
        showProgressDialog(context, message, true, true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象
     * @param resId                        提示信息资源ID
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, int resId, boolean canceledOnTouchOutsideEnable) {
        showProgressDialog(context, context.getResources().getString(resId), canceledOnTouchOutsideEnable, true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象
     * @param message                      提示信息
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, String message, boolean canceledOnTouchOutsideEnable) {
        showProgressDialog(context, message, canceledOnTouchOutsideEnable, true);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象
     * @param resId                        提示信息资源ID
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     * @param cancel                       点击back键时，是否关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, int resId, boolean canceledOnTouchOutsideEnable, boolean cancel) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null!!");
        }
        showProgressDialog(context, context.getString(resId), canceledOnTouchOutsideEnable, cancel);
    }

    /**
     * 显示一个进度对话框
     *
     * @param context                      上下文对象 此处的上下文对象必须使用对应调取Activity的上下文，不然报错
     * @param message                      提示信息
     * @param canceledOnTouchOutsideEnable 是否允许触摸对话框以外的地方，关闭对话框
     * @param cancel                       点击back键时，是否关闭对话框
     */
    public static synchronized void showProgressDialog(Context context, String message, boolean canceledOnTouchOutsideEnable, boolean cancel) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null!!");
        }

        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }

            sProgressDialog = new LoadingPromptDialog(context);
            sProgressDialog.setCanceledOnTouchOutside(canceledOnTouchOutsideEnable);
            sProgressDialog.setCancelable(cancel);
            sProgressDialog.setLoadingText(message);
            sProgressDialog.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭对话框
     */
    public static void dismissProgressDialog() {
        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 进度对话框是否在显示中
     *
     * @return true - 显示中
     */
    public static boolean isProgressDialogShowing() {
        return sProgressDialog != null && sProgressDialog.isShowing();
    }

    /**
     * 设置进度对话框关闭监听
     *
     * @param listener 进度对话框关闭监听
     */
    public static void setProgressDialogDismissListener(DialogInterface.OnDismissListener listener) {
        if (sProgressDialog != null && listener != null) {
            sProgressDialog.setOnDismissListener(listener);
        }
    }

    /**
     * showTokenExpiredDialog
     */
    public static void showTokenExpiredDialog() {
        try {
            if (sTokenExpiredDialog != null && sTokenExpiredDialog.isShowing()) {
                return;
            } else {
                sTokenExpiredDialog = new StandardDialog(sContext);
                sTokenExpiredDialog.setContentText("Token失效，请重新登录！");
                sTokenExpiredDialog.setPositiveButtonText("去登录");
                sTokenExpiredDialog.setNegativeButtonText("取消");
                sTokenExpiredDialog.setPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.entryLoginActivity(sContext);
                    }
                });
                sTokenExpiredDialog.show();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * dismissTokenExpiredDialog
     */
    public static void dismissTokenExpiredDialog() {
        try {
            if (sProgressDialog != null) {
                sProgressDialog.dismiss();
                sProgressDialog = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
