package show.we.lib.widget.slotgame;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;

/**
 * 摇杆控件
 * @author ll
 * @version 1.0.0
 */
public class SlotRockerView extends RelativeLayout {

    /**
     * 摇杆拉下事件监听
     */
    public interface OnRockerPullListener {
        /**
         * 摇杆拉下
         */
        public void onRockerPulled();
    }

    /**
     *
     */
    public interface DataProvider {
        /**
         * 是否正在滑动
         * @return true - 正在滑动
         */
        public boolean isScrolling();
    }

    private static final int MSG_ADJUST = 0x001;
    private static final int MSG_PULL_UP = 0x002;
    private static final int MSG_PULL_DOWN = 0x003;
    private static final int MSG_ADJUST_INTERVAL = 50;
    private static final float ADJUST_DELTA = 8.0f;
    private static final float RATIO = 0.7f;
    private static final int BEGIN_DEGREE = 210;
    private static final int END_DEGREE = 330;
    private static final double BASE = 2.5;

    private ImageView mBallImageView = null;
    private ImageView mTopHandler = null;
    private ImageView mBottomHandler = null;
    private ImageView mRockerBackground = null;
    private TextView mSlotPriceTextView = null;
    private float mLastMonitorY = -1;

    private int mBallMoveDistance = 0; //圆球滑动最大距离
    private int mTopThreshold = 0; //上面摇杆显示/隐藏点
    private int mBottomThreshold = 0; //下面摇杆显示/隐藏点

    private int mTouchDownMargin = 0;

    private OnRockerPullListener mOnRockerPullListener;
    private DataProvider mDataProvider;

    /**
     * @param context context
     */
    public SlotRockerView(Context context) {
        super(context);
    }

    /**
     * @param context context
     * @param attrs attrs
     */
    public SlotRockerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        mBallImageView = (ImageView) findViewById(R.id.rocker_ball);
        mTopHandler = (ImageView) findViewById(R.id.rocker_top_handle);
        mBottomHandler = (ImageView) findViewById(R.id.rocker_bottom_handle);
        mRockerBackground = (ImageView) findViewById(R.id.rocker_bg);
        mSlotPriceTextView = (TextView) findViewById(R.id.txt_slot_price);
        selectWoodRocker();
        post(new Runnable() {
            @Override
            public void run() {
                int ballHeight = mBallImageView.getHeight();
                int topHandlerPadding = mTopHandler.getPaddingTop();
                int bottomHandlerPadding = mBottomHandler.getPaddingTop();
                int handlerHeight = mTopHandler.getHeight() - topHandlerPadding;
                mTopThreshold = topHandlerPadding;
                mBottomThreshold = topHandlerPadding + handlerHeight;
                mBallMoveDistance = 2 * (handlerHeight + topHandlerPadding) + bottomHandlerPadding - ballHeight;
            }
        });

        mBallImageView.setOnTouchListener(mBallOnTouchListener);
    }

    /**
     * 设置摇杆拉下事件监听
     * @param listener listener
     */
    public void setOnRockerPullListener(OnRockerPullListener listener) {
        mOnRockerPullListener = listener;
    }

    /**
     * @param provider provider
     */
    public void setDataProvider(DataProvider provider) {
        mDataProvider = provider;
    }

    /**
     * 选择木摇杆
     */
    public void selectWoodRocker() {
        mBallImageView.setImageResource(R.drawable.slot_wood_ball);
        mTopHandler.setImageResource(R.drawable.slot_wood_bar_inverse);
        mBottomHandler.setImageResource(R.drawable.slot_wood_bar);
        mRockerBackground.setBackgroundResource(R.drawable.slot_wood_bg);
        mSlotPriceTextView.setText(getContext().getString(R.string.slot_price_wood, Enums.HammerType.WOOD.getCost()));
    }

    /**
     * 选择铁摇杆
     */
    public void selectIronRocker() {
        mBallImageView.setImageResource(R.drawable.slot_iron_ball);
        mTopHandler.setImageResource(R.drawable.slot_iron_bar_inverse);
        mBottomHandler.setImageResource(R.drawable.slot_iron_bar);
        mRockerBackground.setBackgroundResource(R.drawable.slot_iron_bg);
        mSlotPriceTextView.setText(getContext().getString(R.string.slot_price_iron, Enums.HammerType.IRON.getCost()));
    }

    /**
     * 选择金摇杆
     */
    public void selectGoldRocker() {
        mBallImageView.setImageResource(R.drawable.slot_gold_ball);
        mTopHandler.setImageResource(R.drawable.slot_gold_bar_inverse);
        mBottomHandler.setImageResource(R.drawable.slot_gold_bar);
        mRockerBackground.setBackgroundResource(R.drawable.slot_gold_bg);
        mSlotPriceTextView.setText(getContext().getString(R.string.slot_price_gold, Enums.HammerType.GOLD.getCost()));
    }

    /**
     * 开始摇奖
     */
    public void start() {
        mHandler.removeMessages(MSG_ADJUST);
        mHandler.removeMessages(MSG_PULL_UP);
        mHandler.removeMessages(MSG_PULL_DOWN);
        mHandler.sendEmptyMessageDelayed(MSG_PULL_DOWN, MSG_ADJUST_INTERVAL);
        if (mOnRockerPullListener != null) {
            mOnRockerPullListener.onRockerPulled();
        }
    }

    private double calcDelta(int distance, double delta) {
        //实现摇杆从上往下拉的过程，速度开头结尾快中间慢的效果，用正弦函数值(210°~330°)乘以滑动距离
        int degree = (int) (BEGIN_DEGREE + (END_DEGREE - BEGIN_DEGREE) * (float) distance / mBallMoveDistance);
        return delta * RATIO * (BASE + Math.sin(Math.toRadians(degree)));
    }

    private void checkThreshold(int oldMargin, int newMargin) {
        if (oldMargin <= mTopThreshold && newMargin > mTopThreshold) {
            mTopHandler.setVisibility(View.INVISIBLE);
        } else if (oldMargin <= mBottomThreshold && newMargin > mBottomThreshold) {
            mBottomHandler.setVisibility(View.VISIBLE);
        } else if (oldMargin >= mBottomThreshold && newMargin < mBottomThreshold) {
            mBottomHandler.setVisibility(View.INVISIBLE);
        } else if (oldMargin >= mTopThreshold && newMargin < mTopThreshold) {
            mTopHandler.setVisibility(View.VISIBLE);
        }
        if (newMargin > mTopThreshold && mTopHandler.getVisibility() != View.INVISIBLE) {
            mTopHandler.setVisibility(View.INVISIBLE);
        } else if (newMargin > mBottomThreshold && mBottomHandler.getVisibility() != View.VISIBLE) {
            mBottomHandler.setVisibility(View.VISIBLE);
        } else if (newMargin < mTopThreshold && mTopHandler.getVisibility() != View.VISIBLE) {
            mTopHandler.setVisibility(View.VISIBLE);
        } else if (newMargin < mBottomThreshold && mBottomHandler.getVisibility() != View.INVISIBLE) {
            mBottomHandler.setVisibility(View.INVISIBLE);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (MSG_ADJUST == msg.what) {
                removeMessages(MSG_ADJUST);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBallImageView.getLayoutParams();
                int oldMargin = params.topMargin;
                if (oldMargin > 0 && oldMargin < mBallMoveDistance) {
                    double delta = oldMargin < mBallMoveDistance / 2 ? -calcDelta(oldMargin, ADJUST_DELTA) : calcDelta(oldMargin, ADJUST_DELTA);
                    int newMargin = (int) (oldMargin + delta);
                    newMargin = Math.max(0, newMargin);
                    newMargin = Math.min(mBallMoveDistance, newMargin);
                    checkThreshold(oldMargin, newMargin);
                    params.topMargin = newMargin;
                    mBallImageView.setLayoutParams(params);
                    sendEmptyMessageDelayed(MSG_ADJUST, MSG_ADJUST_INTERVAL);
                } else if (oldMargin >= mBallMoveDistance) {
                    sendEmptyMessageDelayed(MSG_PULL_UP, MSG_ADJUST_INTERVAL);
                }
            } else if (MSG_PULL_UP == msg.what) {
                removeMessages(MSG_PULL_UP);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBallImageView.getLayoutParams();
                int oldMargin = params.topMargin;
                if (oldMargin > 0) {
                    double delta = -calcDelta(oldMargin, ADJUST_DELTA);
                    int newMargin = (int) (oldMargin + delta);
                    newMargin = Math.max(0, newMargin);
                    newMargin = Math.min(mBallMoveDistance, newMargin);
                    checkThreshold(oldMargin, newMargin);
                    params.topMargin = newMargin;
                    mBallImageView.setLayoutParams(params);
                    sendEmptyMessageDelayed(MSG_PULL_UP, MSG_ADJUST_INTERVAL);
                }
            } else if (MSG_PULL_DOWN == msg.what) {
                removeMessages(MSG_PULL_DOWN);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBallImageView.getLayoutParams();
                int oldMargin = params.topMargin;
                if (oldMargin < mBallMoveDistance) {
                    double delta = calcDelta(oldMargin, ADJUST_DELTA);
                    int newMargin = (int) (oldMargin + delta);
                    newMargin = Math.max(0, newMargin);
                    newMargin = Math.min(mBallMoveDistance, newMargin);
                    checkThreshold(oldMargin, newMargin);
                    params.topMargin = newMargin;
                    mBallImageView.setLayoutParams(params);
                    sendEmptyMessageDelayed(MSG_PULL_DOWN, MSG_ADJUST_INTERVAL);
                } else if (oldMargin >= mBallMoveDistance) {
                    sendEmptyMessageDelayed(MSG_PULL_UP, MSG_ADJUST_INTERVAL);
                }
            }
        }
    };

    private OnTouchListener mBallOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent ev) {
            if (mDataProvider != null && mDataProvider.isScrolling()) {
                return true;
            }
            if (mLastMonitorY == -1) {
                mLastMonitorY = ev.getRawY();
            }

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLastMonitorY = ev.getRawY();
                    RelativeLayout.LayoutParams parameters = (RelativeLayout.LayoutParams) mBallImageView.getLayoutParams();
                    mTouchDownMargin = parameters.topMargin;
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float deltaY = ev.getRawY() - mLastMonitorY;
                    mLastMonitorY = ev.getRawY();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBallImageView.getLayoutParams();
                    int oldMargin = params.topMargin;
                    int newMargin = (int) (oldMargin + calcDelta(oldMargin, deltaY));
                    if (newMargin >= 0 && newMargin <= mBallMoveDistance) {
                        checkThreshold(oldMargin, newMargin);
                        params.topMargin = newMargin;
                        mBallImageView.setLayoutParams(params);
                    }
                    break;
                default:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBallImageView.getLayoutParams();
                    int margin = layoutParams.topMargin;
                    if (mTouchDownMargin < mBallMoveDistance / 2 && margin >= mBallMoveDistance / 2 && mOnRockerPullListener != null) {
                        mOnRockerPullListener.onRockerPulled();
                    }
                    mHandler.sendEmptyMessage(MSG_ADJUST);
                    break;
            }
            return true;
        }
    };
}
