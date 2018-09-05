package show.we.lib.widget.text_list_dialog;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.widget.PopupWindow;

/**
 * Created by CG on 14-3-6.
 *
 * @param <T> 泛型
 * @author ll
 * @version 4.0.0
 */
public class TextData<T> {

    /**
     * 对话框使用数据
     */
    protected Dialog mDialog;
    /**
     * 弹出窗口使用数据
     */
    protected PopupWindow mPopupWindow;
    /**
     * 选择一项的回调
     */
    protected OnValueSelectListener<T> mValueSelectListener;
    /**
     * 每一项左边的图标资源
     */
    protected int[] mLeftDrawableRes;
    /**
     * 每一项显示的文字资源
     */
    protected int[] mShowDataRes;
    /**
     * 每一项显示的文字
     */
    protected String[] mShowData;
    /**
     * 每一项对应的消息
     */
    protected String[] mMessages;
    /**
     * 每一项对应的值
     */
    protected long[] mValues;
    /**
     * 每一项对应的对象
     */
    protected T[] mObjects;

    /**
     * 整体的背景
     */
    protected int mBackgroundRes;
    /**
     * 每一项的背景
     */
    protected int mItemBackgroundRes;
    /**
     * 字体的颜色
     */
    protected int mTextColor;
    /**
     * 字体状态列表的颜色
     */
    protected ColorStateList mColorStateList;
    /**
     * 字体的大小
     */
    protected int mTextSize;
    /**
     * 分割线资源
     */
    protected int mDividerRes;
    /**
     * 分割线颜色
     */
    protected int mDividerColor;
    /**
     * 分割线高度
     */
    protected int mDividerHeight;
    /**
     *
     */
    protected int mGravity;

    /**
     * TextData
     *
     * @param dialog      dialog
     * @param popupWindow popupWindow
     */
    public TextData(Dialog dialog, PopupWindow popupWindow) {
        mDialog = dialog;
        mPopupWindow = popupWindow;
    }

    /**
     * setSelectedListener
     *
     * @param listener listener
     */
    public void setSelectedListener(OnValueSelectListener<T> listener) {
        mValueSelectListener = listener;
    }

    /**
     * setLeftDrawableRes
     *
     * @param leftDrawableRes leftDrawableRes
     */
    public void  setLeftDrawableRes(int[] leftDrawableRes) {
        mLeftDrawableRes = leftDrawableRes;
    }

    /**
     * getLeftDrawableRes
     *
     * @return LeftDrawableRes
     */
    public int[] getLeftDrawableRes() {
        return mLeftDrawableRes;
    }

    /**
     * 设置adapter数据资源
     *
     * @param showDataRes adapter数据资源
     */
    public void setShowDataRes(int[] showDataRes) {
        mShowDataRes = showDataRes;
    }

    /**
     * getShowDataRes
     *
     * @return ShowDataRes
     */
    public int[] getShowDataRes() {
        return mShowDataRes;
    }

    /**
     * 设置adapter数据
     *
     * @param showData adapter数据
     */
    public void setShowData(String[] showData) {
        mShowData = showData;
    }

    /**
     * getShowData
     *
     * @return String []
     */
    public String[] getShowData() {
        return mShowData;
    }

    /**
     * setMessages
     *
     * @param messages messages
     */
    public void setMessages(String[] messages) {
        mMessages = messages;
    }

    /**
     * setValues
     *
     * @param values values
     */
    public void setValues(long[] values) {
        mValues = values;
    }

    /**
     * 设置一个泛型数据
     *
     * @param objects objects
     */
    public void setT(T[] objects) {
        mObjects = objects;
    }

    /**
     * setBackgroundRes
     *
     * @param backgroundRes backgroundRes
     */
    public void setBackgroundRes(int backgroundRes) {
        mBackgroundRes = backgroundRes;
    }

    /**
     * setItemBackgroundRes
     *
     * @param itemBackgroundRes itemBackgroundRes
     */
    public void setItemBackgroundRes(int itemBackgroundRes) {
        mItemBackgroundRes = itemBackgroundRes;
    }

    /**
     * getItemBackgroundRes
     *
     * @return mItemBackgroundRes
     */
    public int getItemBackgroundRes() {
        return mItemBackgroundRes;
    }

    /**
     * setTextColor
     *
     * @param textColor textColor
     */
    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    /**
     * getTextColor
     *
     * @return mTextColor
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * setTextColor
     *
     * @param colorStateList colorStateList
     */
    public void setTextColorStateList(ColorStateList colorStateList) {
        mColorStateList = colorStateList;
    }

    /**
     * getTextColor
     *
     * @return mTextColor
     */
    public ColorStateList getTextColorStateList() {
        return mColorStateList;
    }
    /**
     * setTextSize
     *
     * @param textSize textSize
     */
    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    /**
     * getTextSize
     *
     * @return mTextSize
     */
    public int getTextSize() {
        return mTextSize;
    }

    /**
     *
     * @param gravity gravity
     */
    public void setItemTextGravity(int gravity) {
        mGravity = gravity;
    }

    /**
     * setDividerRes
     *
     * @param dividerRes dividerRes
     */
    public void setDividerRes(int dividerRes) {
        mDividerRes = dividerRes;
    }

    /**
     * setDividerColor
     *
     * @param dividerColor dividerColor
     */
    public void setDividerColor(int dividerColor) {
        mDividerColor = dividerColor;
    }

    /**
     * setDividerHeight
     *
     * @param dividerHeight dividerHeight
     */
    public void setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
    }
}
