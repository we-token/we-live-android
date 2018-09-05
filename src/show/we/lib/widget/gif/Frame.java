package show.we.lib.widget.gif;

import android.graphics.Bitmap;

/**
 * 帧数据结构
 *
 * @author ll
 * @version 1.0.0
 */
public class Frame {

    private Bitmap mBitmap;
    private int mDuration;

    /**
     * 构造函数
     *
     * @param bitmap   bitmap
     * @param duration duration
     */
    public Frame(Bitmap bitmap, int duration) {
        mBitmap = bitmap;
        mDuration = duration;
    }

    /**
     * 获取bitmap
     *
     * @return bitmap
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * 设置bitmap
     *
     * @param bitmap bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    /**
     * 获取帧时长  (ms)
     *
     * @return 帧时长
     */
    public int getDuration() {
        return mDuration;
    }

    /**
     * 设置帧时长  (ms)
     *
     * @param duration 时长
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }
}
