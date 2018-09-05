/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package show.we.lib.widget.abc_pull_to_refresh.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import show.we.lib.R;
import show.we.lib.widget.abc_pull_to_refresh.listeners.HeaderViewListener;
import show.we.lib.widget.abc_pull_to_refresh.listeners.OnRefreshListener;
import show.we.lib.widget.abc_pull_to_refresh.viewdelegates.ViewDelegate;

/**
 * The main component of the library. You wrap the views you wish to be 'pullable' within this layout.
 * This layout is setup by using the {@link ActionBarPullToRefresh} setup-wizard return by
 * @link ActionBarPullToRefresh#from(android.app.Activity)}.
 */
public class PullToRefreshLayout extends FrameLayout {

    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "PullToRefreshLayout";

    private PullToRefreshAttacher mPullToRefreshAttacher;
    private OnRefreshListener mSavedListener;
    private boolean mTouchRefreshEnabled = true;

    public PullToRefreshLayout(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Manually set this Attacher's refreshing state. The header will be
     * displayed or hidden as requested.
     *
     * @param refreshing - Whether the attacher should be in a refreshing state,
     */
    public final void setRefreshing(final boolean refreshing) {
        ensureAttacher();
        post(new Runnable() {
            @Override
            public void run() {
                if (mPullToRefreshAttacher.isHeaderViewAlreadyAddToActivity()) {
                    mPullToRefreshAttacher.setRefreshing(refreshing);
                } else {
                    post(this);
                }
            }
        });
    }

    /**
     * Manually set this Attacher's refreshing state. The header will be
     * displayed
     */
    public void startRefresh() {
        setRefreshing(true);
    }

    /**
     * @return true if this Attacher is currently in a refreshing state.
     */
    public final boolean isRefreshing() {
        ensureAttacher();
        return mPullToRefreshAttacher != null && mPullToRefreshAttacher.isRefreshing();
    }

    public final boolean isStopping() {
        return mPullToRefreshAttacher.isStopping();
    }

    public void clearRefreshableViews() {
        ensureAttacher();
        mPullToRefreshAttacher.clearRefreshableViews();
    }

    public void setTouchRefreshEnabled(boolean enabled) {
        mTouchRefreshEnabled = enabled;
        if (!enabled) {
            clearRefreshableViews();
        }
    }

    public boolean canRefreshFromTouch() {
        return mTouchRefreshEnabled;
    }

    /**
     * Call this when your refresh is complete and this view should reset itself
     * (header view will be hidden).
     *
     * This is the equivalent of calling <code>setRefreshing(false)</code>.
     */
    public void stopRefresh(boolean isRequestSuccess) {
        ensureAttacher();
        if (mPullToRefreshAttacher != null) {
            mPullToRefreshAttacher.setRefreshComplete();
        }
    }

    /**
     * Set a {@link show.we.lib.widget.abc_pull_to_refresh.listeners.HeaderViewListener} which is called when the visibility
     * state of the Header View has changed.
     *
     * @param listener listener
     */
    public final void setHeaderViewListener(HeaderViewListener listener) {
        ensureAttacher();
        if (mPullToRefreshAttacher != null) {
            mPullToRefreshAttacher.setHeaderViewListener(listener);
        }
    }

    /**
     * @return The Header View which is displayed when the user is pulling, or
     *         we are refreshing.
     */
    public final View getHeaderView() {
        ensureAttacher();
        return mPullToRefreshAttacher != null ? mPullToRefreshAttacher.getHeaderView() : null;
    }

    /**
     * @return The HeaderTransformer currently used by this Attacher.
     */
    public HeaderTransformer getHeaderTransformer() {
        ensureAttacher();
        return mPullToRefreshAttacher != null ? mPullToRefreshAttacher.getHeaderTransformer() : null;
    }


    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (DEBUG) {
            Log.d(LOG_TAG, "onInterceptTouchEvent. " + event.toString());
        }
        if (isEnabled() && mPullToRefreshAttacher != null && getChildCount() > 0) {
            return mPullToRefreshAttacher.onInterceptTouchEvent(event);
        }
        return false;
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        if (DEBUG) {
            Log.d(LOG_TAG, "onTouchEvent. " + event.toString());
        }
        if (isEnabled() && mPullToRefreshAttacher != null) {
            return mPullToRefreshAttacher.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new PullToRefreshLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        // Destroy the PullToRefreshAttacher
        if (mPullToRefreshAttacher != null) {
            /** Activity not finishing, this view may be added in ViewPager
             * , so we need to save the listener and add to the new PullToRefreshAttacher object when onAttachedToWindow() method called**/
            boolean needSaveListener = getContext() instanceof Activity && !((Activity)getContext()).isFinishing();
            if (needSaveListener) {
                mSavedListener = mPullToRefreshAttacher.getOnRefreshListener();
            }
            mPullToRefreshAttacher.destroy();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPullToRefreshAttacher.isDestroyed() && getContext() instanceof Activity && mSavedListener != null) {
            ActionBarPullToRefresh.SetupWizard wizard = ActionBarPullToRefresh.from((Activity) getContext()).listener(mSavedListener);
            if (mTouchRefreshEnabled) {
                wizard.allChildrenArePullable();
                wizard.setup(this);
            } else {
                wizard.setup(this);
                clearRefreshableViews();
            }
        }
        mPullToRefreshAttacher.addHeaderViewToActivity(getHeaderView());
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        if (mPullToRefreshAttacher != null) {
            mPullToRefreshAttacher.onConfigurationChanged(newConfig);
        }
        super.onConfigurationChanged(newConfig);
    }

    void setPullToRefreshAttacher(PullToRefreshAttacher attacher) {
        if (mPullToRefreshAttacher != null) {
            mPullToRefreshAttacher.destroy();
        }
        mPullToRefreshAttacher = attacher;
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }

    void addAllChildrenAsPullable() {
        ensureAttacher();
        if (mPullToRefreshAttacher != null) {
            for (int i = 0, z = getChildCount(); i < z; i++) {
                addRefreshableView(getChildAt(i));
            }
        }
    }

    void addChildrenAsPullable(int[] viewIds) {
        for (int i = 0, z = viewIds.length; i < z; i++) {
            View view = findViewById(viewIds[i]);
            if (view != null) {
                addRefreshableView(findViewById(viewIds[i]));
            }
        }
    }

    void addChildrenAsPullable(View[] views) {
        for (int i = 0, z = views.length; i < z; i++) {
            if (views[i] != null) {
                addRefreshableView(views[i]);
            }
        }
    }

    void addRefreshableView(View view) {
        if (mPullToRefreshAttacher != null) {
            mPullToRefreshAttacher.addRefreshableView(view, getViewDelegateFromLayoutParams(view));
        }
    }

    ViewDelegate getViewDelegateFromLayoutParams(View view) {
        if (view != null && view.getLayoutParams() instanceof LayoutParams) {
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            String clazzName = lp.getViewDelegateClassName();

            if (!TextUtils.isEmpty(clazzName)) {
                // Lets convert any relative class names (i.e. .XYZViewDelegate)
                final int firstDot = clazzName.indexOf('.');
                if (firstDot == -1) {
                    clazzName = getContext().getPackageName() + "." + clazzName;
                } else if (firstDot == 0) {
                    clazzName = getContext().getPackageName() + clazzName;
                }
                return InstanceCreationUtils.instantiateViewDelegate(getContext(), clazzName);
            }
        }
        return null;
    }

    protected PullToRefreshAttacher createPullToRefreshAttacher(Activity activity,
            Options options) {
        return new PullToRefreshAttacher(activity, options != null ? options : new Options());
    }

    private void ensureAttacher() {
        if (mPullToRefreshAttacher == null) {
            throw new IllegalStateException("You need to setup the PullToRefreshLayout before using it");
        }
    }

    static class LayoutParams extends FrameLayout.LayoutParams {
        private final String mViewDelegateClassName;

        LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.PullToRefreshView);
            mViewDelegateClassName = a
                    .getString(R.styleable.PullToRefreshView_ptrViewDelegateClass);
            a.recycle();
        }

        String getViewDelegateClassName() {
            return mViewDelegateClassName;
        }
    }
}
