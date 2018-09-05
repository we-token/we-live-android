package show.we.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import show.we.lib.widget.DetectSoftInputEventFrameLayout;

/**
 * @author ll
 * @version 1.0.0
 *          Date: 13-7-25
 *          Time: 上午11:39
 *          To change this template use File | Settings | File Templates.
 */
public class InputMethodUtils {

    /**
     * 监听软键盘弹出/关闭接口
     */
    public interface OnSoftInputEventListener {
        /**
         * 软键盘弹出
         */
        public void onSoftInputOpened();
        /**
         * 软键盘关闭
         */
        public void onSoftInputClosed();
    }

    private static boolean sIsInputMethodShowing;
    /**
     * @return sIsInputMethodShowing
     */
    public static boolean isInputMethodShowing() {
        return sIsInputMethodShowing;
    }

    /**
     * @param isInputMethodShowing isInputMethodShowing
     */
    public static void setIsInputMethodShowing(boolean isInputMethodShowing) {
        InputMethodUtils.sIsInputMethodShowing = isInputMethodShowing;
    }

    /**
     * 判断是否需要关闭输入法
     *
     * @param v     输入框的view
     * @param event 点击事件
     * @return 是否需要关闭输入法
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] location = {0, 0};
            v.getLocationInWindow(location);
            int left = location[0], top = location[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏输入法
     *
     * @param currentFocusView 当前焦点view
     */
    public static void hideSoftInput(View currentFocusView) {
        if (currentFocusView != null) {
            IBinder token = currentFocusView.getWindowToken();
            if (token != null) {
                InputMethodManager im = (InputMethodManager) currentFocusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token, 0);
            }
        }
    }

    /**
     * 隐藏输入法
     * @param activity activity
     */
    public static void hideSoftInput(Activity activity) {
        View currentFocusView = activity.getCurrentFocus();
       hideSoftInput(currentFocusView);
    }

    /**
     * 开关输入法
     *
     * @param currentFocusView 当前焦点view
     */
    public static void toggleSoftInput(View currentFocusView) {
        InputMethodManager imm = (InputMethodManager) currentFocusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(currentFocusView, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 限制输入文本的长度
     * @param editText editText
     * @param maxLength maxLength
     * @param hint hint
     */
    public static void limitTextLength(final EditText editText, final int maxLength, final String hint) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > maxLength) {
                    int cutLength = 0;
                    if (start == 0 && count > before) {
                        cutLength = count - before;
                    }

                    if (start > 0) {
                        cutLength = count;
                    }

                    if (cutLength > 0 && s.length() > cutLength) {
                        PromptUtils.showToast(hint, Toast.LENGTH_SHORT);
                        int subLength = s.length() - cutLength;
                        editText.setText(s.subSequence(0, subLength));
                        editText.setSelection(subLength);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 按下回车键回调接口
     */
    public static abstract class OnEnterKeyDownListener implements View.OnKeyListener {
        /**
         * 按下回车键回调
         *
         * @param v view
         */
        public abstract void onEnterKeyDown(View v);

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                onEnterKeyDown(v);
                return true;
            }
            return false;
        }
    }

    /**
     * 监听软键盘弹出事件
     * @param activity activity
     */
    public static void detectInputMethodEvent(Activity activity) {
        detectInputMethodEvent(activity, null);
    }

    /**
     * 监听软键盘弹出事件
     * @param activity activity
     * @param listener listener
     */
    public static void detectInputMethodEvent(Activity activity, OnSoftInputEventListener listener) {
        detectInputMethodEvent(activity, listener, true);
    }

    /**
     * 监听软键盘弹出事件
     * @param activity activity
     * @param listener listener
     * @param closeSoftInputOnTouchOutside closeSoftInputOnTouchOutside
     */
    public static void detectInputMethodEvent(Activity activity, OnSoftInputEventListener listener, boolean closeSoftInputOnTouchOutside) {
        detectInputMethodEvent(activity, listener, closeSoftInputOnTouchOutside, false);
    }

    /**
     * 监听软键盘弹出事件
     * @param activity activity
     * @param listener listener
     * @param closeSoftInputOnTouchOutside closeSoftInputOnTouchOutside
     * @param interceptTouchEvent intercepTouchEvent
     */
    public static void detectInputMethodEvent(Activity activity, OnSoftInputEventListener listener
            , boolean closeSoftInputOnTouchOutside, boolean interceptTouchEvent) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        DetectSoftInputEventFrameLayout frameLayout = new DetectSoftInputEventFrameLayout(activity);
        decorView.addView(frameLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.registerDetect();
        frameLayout.setOnSoftInputEventListener(listener);
        frameLayout.canCloseSoftInputOnTouchOutside(closeSoftInputOnTouchOutside);
        frameLayout.interceptTouchEvent(interceptTouchEvent);
    }

    /**
     * computeUsableHeight
     * @param view view
     * @return height
     */
    public static int computeUsableHeight(View view) {
        Rect r = new Rect();
        view.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
}
