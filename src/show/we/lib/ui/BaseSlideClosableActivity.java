package show.we.lib.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import show.we.lib.utils.DisplayUtils;
import show.we.lib.utils.InputMethodUtils;
import show.we.lib.widget.SlideClosableRelativeLayout;

/**
 * @author ll
 * @version 1.0.0
 */
public class BaseSlideClosableActivity extends BaseActivity {
    /** 滑动可以关闭的布局 */
    protected SlideClosableRelativeLayout mSlideLayout;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ViewGroup decorView = (ViewGroup)getWindow().getDecorView();
        View contentView = decorView.getChildAt(0);
        decorView.removeView(contentView);
        mSlideLayout = new SlideClosableRelativeLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        mSlideLayout.addView(contentView);
        decorView.addView(mSlideLayout, params);
        mSlideLayout.setWillNotDraw(true);
        mSlideLayout.setSlidingCloseMode(SlideClosableRelativeLayout.SLIDING_CLOSE_MODE_HORIZONTAL_RIGHT);
        mSlideLayout.setOnSlidingScrollListener(new SlideClosableRelativeLayout.OnSlidingScrollListener() {
            @Override
            public void onScrollStarted() {
                 if (InputMethodUtils.isInputMethodShowing()) {
                     hideInputSoft();
                 }
            }

            @Override
            public void onScrollEnded() {
            }
        });
        mSlideLayout.setOnSlidingCloseListener(new SlideClosableRelativeLayout.OnSlidingCloseListener() {
            @Override
            public void onSlidingClosed() {
                finish();
            }
        });
        mSlideLayout.setInitSlidingOpenState(true);
        detectSoftKeyBoardEvent(this);
    }

    @Override
    public void finish() {
        if (mSlideLayout != null && !mSlideLayout.isClosing() && !mSlideLayout.isClosed()) {
            mSlideLayout.close(true);
        } else {
            super.finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setControlBarHitViewRect();
    }

    private void setControlBarHitViewRect() {
        if (mSlideLayout != null && !mSlideLayout.isClosing()) {
            Rect rect = new Rect(0, 0, DisplayUtils.getWidthPixels(), DisplayUtils.getHeightPixels());
            mSlideLayout.setSlidingOpenHitRect(rect.left, rect.top, rect.right, rect.bottom);
            mSlideLayout.setEnableMarginOpen(true);
        }
    }

    /**
     * 隐藏软件盘
     */
    protected void hideInputSoft() {

    }  
}
