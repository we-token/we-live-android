package show.we.lib.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import show.we.lib.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class HorizontalElasticityContainerView extends ViewPager {

    /**
     * HorizontalElasticityContainerView
     *
     * @param context context
     */
    public HorizontalElasticityContainerView(Context context) {
        super(context);
        setOnPageChangeListener(new PageChangeListener());
    }

    /**
     * setContentView
     *
     * @param contentView contentView
     */
    public void setContentView(View contentView) {
        View leftView = new View(getContext());
        View rightView = new View(getContext());
        List<View> views = new ArrayList<View>(3);
        views.add(leftView);
        views.add(contentView);
        views.add(rightView);
        setAdapter(new ViewPagerAdapter<View>(views));
        setCurrentItem(1);
    }

    private class PageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            if (position != 1) {
                setCurrentItem(1);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
