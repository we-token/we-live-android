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

package show.we.lib.widget.abc_pull_to_refresh.actionbarcompat;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import show.we.lib.widget.abc_pull_to_refresh.base.Options;
import show.we.lib.widget.abc_pull_to_refresh.base.PullToRefreshAttacher;
import show.we.lib.widget.abc_pull_to_refresh.base.PullToRefreshLayout;

/**
 * @see show.we.lib.widget.abc_pull_to_refresh.base.PullToRefreshLayout
 */
public class AbcPullToRefreshLayout extends
        PullToRefreshLayout {

    public AbcPullToRefreshLayout(Context context) {
        super(context);
    }

    public AbcPullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbcPullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected PullToRefreshAttacher createPullToRefreshAttacher(Activity activity,
            Options options) {
        return new AbcPullToRefreshAttacher(activity, options);
    }
}
