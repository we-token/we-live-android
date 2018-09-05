package show.we.lib.widget.expression;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import show.we.lib.R;
import show.we.lib.ui.adapter.ViewPagerAdapter;
import show.we.lib.widget.NestedViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class ExpressionViewPager extends NestedViewPager {

    private static final int NUM_PER_PAGER = 27;

    private List<ExpressionGridView> mGridViewList;

    /**
     * ExpressionViewPager
     *
     * @param context context
     */
    public ExpressionViewPager(Context context) {
        super(context);
        init();
    }

    /**
     * ExpressionViewPager
     *
     * @param context context
     * @param attrs   attrs
     */
    public ExpressionViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        List<String> allExpression = Arrays.asList(getResources().getStringArray(R.array.array_expression));
        int pageCount = (int) Math.ceil(((double) allExpression.size()) / NUM_PER_PAGER);
        mGridViewList = new ArrayList<ExpressionGridView>(pageCount);
        for (int i = 0; i < pageCount; i++) {
            ExpressionGridView gridView = new ExpressionGridView(getContext());
            int start = i * NUM_PER_PAGER;
            int end = Math.min(start + NUM_PER_PAGER, allExpression.size());
            gridView.setExpression(allExpression, allExpression.subList(start, end), start);
            mGridViewList.add(gridView);
        }
        setAdapter(new ViewPagerAdapter<ExpressionGridView>(mGridViewList));
    }

    /**
     * setTextView
     *
     * @param textView           MessageEditText
     * @param showFirstFrameOnly showFirstFrameOnly
     */
    public void setTextView(EditText textView, boolean showFirstFrameOnly) {
        for (ExpressionGridView gridView : mGridViewList) {
            gridView.setTextView(textView, showFirstFrameOnly);
        }
    }

    /**
     * 设置长度
     *
     * @param maxLength 长度
     */
    public void setMaxLength(int maxLength) {
        for (ExpressionGridView gridView : mGridViewList) {
            gridView.setMaxLength(maxLength);
        }
    }
}
