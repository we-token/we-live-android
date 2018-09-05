package show.we.lib.widget.expression;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.utils.GifCacheUtils;
import show.we.lib.widget.gif.GifSpan;

import java.util.List;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class ExpressionGridView extends GridView implements AdapterView.OnItemClickListener {

    private static final int SIZE = 28;
    private static final int COLUMN = 7;

    private List<String> mAllExpression;
    private List<String> mUsedExpression;
    private int mMaxLength = Integer.MAX_VALUE;
    private EditText mShowView;
    private boolean mShowFirstFrameOnly;
    private boolean mIsEmoticonChanged;

    /**
     * ExpressionGridView
     *
     * @param context context
     */
    public ExpressionGridView(Context context) {
        super(context);
        init();
    }

    /**
     * ExpressionGridView
     *
     * @param context context
     * @param attrs   attrs
     */
    public ExpressionGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setNumColumns(COLUMN);
        setHorizontalSpacing(1);
        setVerticalSpacing(1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        setBackgroundColor(Color.TRANSPARENT);
        setCacheColorHint(0);
        setPadding(4, 0, 4, 0);
        setSelector(R.drawable.transparent);
        setGravity(Gravity.CENTER);
    }

    /**
     * setExpression
     *
     * @param allExpression  allExpression
     * @param usedExpression usedExpression
     * @param start          start
     */
    public void setExpression(List<String> allExpression, List<String> usedExpression, int start) {
        mAllExpression = allExpression;
        mUsedExpression = usedExpression;
        setAdapter(new ExpressionGridAdapter(getContext(), start, usedExpression.size()));
        setOnItemClickListener(this);
    }

    /**
     * setTextView
     *
     * @param textView           MessageEditText
     * @param showFirstFrameOnly showFirstFrameOnly
     */
    public void setTextView(EditText textView, boolean showFirstFrameOnly) {
        if (mShowView != null) {
            mShowView.removeTextChangedListener(mTextWatcher);
        }
        mShowView = textView;
        mShowView.addTextChangedListener(mTextWatcher);
        mShowFirstFrameOnly = showFirstFrameOnly;
    }

    /**
     * 设置长度
     *
     * @param maxLength 长度
     */
    public void setMaxLength(int maxLength) {
        mMaxLength = maxLength;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mShowView != null) {
            String str = mShowView.getText().toString();
            int start = mShowView.getSelectionStart();
            int end = mShowView.getSelectionEnd();
            String leftStr = str.substring(0, start);
            String rightStr = str.substring(end, str.length());
            if (position == SIZE - 1) {
                mIsEmoticonChanged = true;
                if (start == end && leftStr.length() > 0) {
                    leftStr = processDelete(leftStr);
                }
                mShowView.setText(leftStr + rightStr);
                mShowView.setSelection(leftStr.length());
            } else if (position < mUsedExpression.size()) {
                if (str.length() + mUsedExpression.get(position).length() <= mMaxLength) {
                    mIsEmoticonChanged = true;
                    mShowView.setText(leftStr + mUsedExpression.get(position) + rightStr);
                    int length = mShowView.getText().toString().length();
                    mShowView.setSelection(Math.min(leftStr.length() + mUsedExpression.get(position).length(), length));
                }
            }
        }
    }

    private String processDelete(String srcStr) {
        String dstStr = srcStr;
        if (!StringUtils.isEmpty(srcStr)) {
            boolean bExpressionFound = false;
            for (String expression : mAllExpression) {
                if (srcStr.endsWith(expression)) {
                    dstStr = srcStr.substring(0, srcStr.length() - expression.length());
                    bExpressionFound = true;
                    break;
                }
            }
            if (!bExpressionFound) {
                dstStr = srcStr.substring(0, srcStr.length() - 1);
            }
        }
        return dstStr;
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        private Editable mPreEditable;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals(mPreEditable)) {
                mPreEditable = s;
                if (mIsEmoticonChanged) {
                    mIsEmoticonChanged = false;

                    /*processExpression(mPreEditable.toString());*/
                    mShowView.setSelection(mShowView.getSelectionStart(), mShowView.getSelectionEnd());
                }
            }
        }
    };

    private void processExpression(String mPreString) {
        SpannableStringBuilder builder = new SpannableStringBuilder(mPreString);
        String[] splits = mPreString.split("/");
        int start = splits[0].length();
        for (int i = 1; i < splits.length; i++) {
            String split = "/" + splits[i];
            for (int j = 0; j < mAllExpression.size(); j++) {
                String expression = mAllExpression.get(j);
                if (split.startsWith(expression)) {
                    final float zeroPointFive = 0.5f;
                    final int emoticonGifSizeDip = 18;
                    int size = (int) (getResources().getDisplayMetrics().density * emoticonGifSizeDip + zeroPointFive);
                    builder.setSpan(new GifSpan(getContext(), GifCacheUtils.getExpressionGifIcon(getContext(), j),
                            mShowView, DynamicDrawableSpan.ALIGN_BASELINE, mShowFirstFrameOnly, size, size),
                            start, start + expression.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
            start += split.length();
        }
        mShowView.setText(builder, TextView.BufferType.SPANNABLE);
    }
}
