package show.we.lib.widget.gif;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * GifView<br>
 * 本类可以显示一个gif动画。<br>
 * 如果要显示的gif太大，会出现OOM的问题。
 *
 * @author ll
 * @version 1.0.0
 */
public class GifView extends ImageView {

    /**
     * Gif 显示回调
     */
    public interface OnGifDisplayListener {
        /**
         * 开始显示
         */
        public void onStarted();

        /**
         * 显示完成
         */
        public void onFinished();
    }

    private static final int LOOP_FOREVER = -1;

    private List<Frame> mFrameList = null;

    private LoadGifAsyncTask mLoadGifAsyncTask;

    private int mLoopTime = LOOP_FOREVER;
    private int mCurFrameIndex = -1;

    private OnGifDisplayListener mListener;

    private static final int MESSAGE_SHOW_NEXT = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_SHOW_NEXT && mFrameList != null && mFrameList.size() > 0) {
                mCurFrameIndex++;
                if (mCurFrameIndex <= mFrameList.size() - 1) {
                    setImageBitmap(mFrameList.get(mCurFrameIndex).getBitmap());
                } else if (mLoopTime == LOOP_FOREVER) {
                    mCurFrameIndex = 0;
                    setImageBitmap(mFrameList.get(mCurFrameIndex).getBitmap());
                } else if (mLoopTime > 0) {
                    mCurFrameIndex = 0;
                    setImageBitmap(mFrameList.get(mCurFrameIndex).getBitmap());
                    mLoopTime--;
                } else if (mListener != null) {
                    mListener.onFinished();
                }
            } else {
                setImageBitmap(null);
            }
        }
    };

    /**
     * GifView 构造函数
     *
     * @param context 上下文
     */
    public GifView(Context context) {
        this(context, null);
    }

    /**
     * constructor
     *
     * @param context context
     * @param attrs   attrs
     */
    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * constructor
     *
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置显示循环次数
     *
     * @param loopTime loopTime
     */
    public void setLoopTime(int loopTime) {
        mLoopTime = loopTime;
    }

    /**
     * 获取循环的次数
     *
     * @return 循环的次数
     */
    public int getLoopTime() {
        return mLoopTime;
    }

    /**
     * 设置显示回调监控
     *
     * @param listener listener
     */
    public void setOnShowCompletedListener(OnGifDisplayListener listener) {
        mListener = listener;
    }

    /**
     * 设置FrameList
     *
     * @param frameList frameList
     */
    public void setGifFrameList(List<Frame> frameList) {
        setGifFrameList(frameList, LOOP_FOREVER);
    }

    /**
     * 设置FrameList
     *
     * @param frameList frameList
     * @param loop      loop
     */
    public void setGifFrameList(List<Frame> frameList, int loop) {
        cancelParseGift();
        start(frameList, loop);
    }

    /**
     * 以资源形式设置gif图片, 并永久循环
     *
     * @param resId gif图片的资源ID
     */
    public void setGifResource(int resId) {
        setGifResource(resId, LOOP_FOREVER);
    }

    /**
     * 以资源形式设置gif图片
     *
     * @param resId gif图片的资源ID
     * @param loop  loop
     */
    public void setGifResource(int resId, int loop) {
        try {
            setGifInputStream(getContext().getResources().openRawResource(resId), loop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 以资源形式设置gif图片
     *
     * @param inputStream gif图片的资源inputStream
     * @param loop        loop
     */
    public void setGifInputStream(InputStream inputStream, int loop) {
        cancelParseGift();
        mLoadGifAsyncTask = new LoadGifAsyncTask(loop);
        mLoadGifAsyncTask.execute(inputStream);
    }

    private void cancelParseGift() {
        // 如果动画已经运行，中途停止并等待解析下一个动画并不优美
        // 所以 不执行 1. mHandler.removeMessages(MESSAGE_SHOW_NEXT); 2. mFrameList = null;
        if (mLoadGifAsyncTask != null) {
            mLoadGifAsyncTask.cancel(true);
        }
    }

    /**
     * runAgain
     *
     * @param loop loop
     */
    public void runAgain(int loop) {
        mLoopTime = loop;
        mCurFrameIndex = -1;
        mHandler.removeMessages(MESSAGE_SHOW_NEXT);
        mHandler.sendEmptyMessage(MESSAGE_SHOW_NEXT);
        notifyStar();
    }

    private void start(List<Frame> frameList, int loop) {
        mLoopTime = loop;
        mCurFrameIndex = -1;
        mFrameList = frameList;
        mHandler.removeMessages(MESSAGE_SHOW_NEXT);
        mHandler.sendEmptyMessage(MESSAGE_SHOW_NEXT);
        notifyStar();
    }

    private void notifyStar() {
        if (mListener != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onStarted();
                }
            });
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mHandler.hasMessages(MESSAGE_SHOW_NEXT) && (mFrameList != null) && mFrameList.size() > 0 && mCurFrameIndex < mFrameList.size()) {
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_NEXT, mFrameList.get(mCurFrameIndex).getDuration());
        }
    }

    private class LoadGifAsyncTask extends AsyncTask<InputStream, Object, Object> {

        private int mLoop;

        public LoadGifAsyncTask(int loop) {
            mLoop = loop;
        }

        @Override
        protected Object doInBackground(InputStream... params) {
            List<Frame> frameList = new ArrayList<Frame>();
            new GifParser().doParse(params[0], frameList);
            return frameList;
        }

        @Override
        protected void onPostExecute(Object o) {
            start((ArrayList<Frame>) o, mLoop);
        }
    }
}
