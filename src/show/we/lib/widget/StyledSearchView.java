package show.we.lib.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import show.we.lib.R;

/**
 * 自定义风格的SearchView(系统的SearchView修改style无效)
 * @author ll
 * @version 1.0.0
 */
public class StyledSearchView extends SearchView {

    private static final float SEARCH_TEXT_SIZE = 14;
    /**
     * @param context context
     */
    public StyledSearchView(Context context) {
        super(context);
        initViews();
    }

    /**
     * @param context context
     * @param attrs attrs
     */
    public StyledSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.search_plate).setBackgroundResource(R.drawable.xml_bg_search_et);
        ImageView closeImageView = (ImageView) findViewById(R.id.search_close_btn);
        closeImageView.setImageResource(R.drawable.xml_icon_search_close);
        closeImageView.setBackgroundColor(Color.TRANSPARENT);
        AutoCompleteTextView autoCompleteText = (AutoCompleteTextView) findViewById(R.id.search_src_text);
        autoCompleteText.setTextSize(SEARCH_TEXT_SIZE);
        autoCompleteText.setHintTextColor(Color.WHITE);
    }
}
