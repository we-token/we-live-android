package show.we.lib.widget.text_list_dialog;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import show.we.lib.R;

/**
 * Created by CG on 14-3-6.
 *
 * @param <T> 泛型
 * @author ll
 * @version 1.0.0
 */
public class TextItemsController<T> extends TextData<T> {

    private LinearLayout mContentView;

    /**
     * TextItemsController
     *
     * @param contentView contentView
     * @param dialog      dialog
     * @param popupWindow popupWindow
     */
    public TextItemsController(LinearLayout contentView, Dialog dialog, PopupWindow popupWindow) {
        super(dialog, popupWindow);
        mContentView = contentView;
    }

    @Override
    public void setBackgroundRes(int backgroundRes) {
        super.setBackgroundRes(backgroundRes);
        if (mBackgroundRes != 0) {
            mContentView.setBackgroundResource(mBackgroundRes);
        }
    }

    @Override
    public void setShowDataRes(int[] showDataRes) {
        super.setShowDataRes(showDataRes);
        setShowData(showDataRes.length);
    }

    @Override
    public void setShowData(String[] showData) {
        super.setShowData(showData);
        setShowData(showData.length);
    }

    private void setShowData(int count) {
        mContentView.removeAllViews();
        for (int position = 0; position < count; position++) {
            if (position != 0) {
                addDivider();
            }
            addItem(position);
        }
    }

    private void addItem(final int position) {
        View itemView = View.inflate(mContentView.getContext(), R.layout.layout_text_list_item, null);
        if (mItemBackgroundRes != 0) {
            itemView.setBackgroundResource(R.drawable.xml_popup_menu_item_bg);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mValueSelectListener != null) {
                    mValueSelectListener.onValueSelected(mDialog, mPopupWindow, position
                            , mShowData == null ? (mShowDataRes == null ? "" : mContentView.getContext().getString(mShowDataRes[position]))
                            : mShowData[position]
                            , mMessages == null ? "" : mMessages[position]
                            , mValues == null ? 0 : mValues[position]
                            , mObjects == null ? null : mObjects[position]
                    );
                }
            }
        });

        int[] showDataRes = getShowDataRes();
        String[] showData = getShowData();
        int[] leftDrawableRes = getLeftDrawableRes();
        if (showData != null) {
            ((TextView) itemView.findViewById(R.id.txt)).setText(showData[position]);
        } else if (showDataRes != null) {
            ((TextView) itemView.findViewById(R.id.txt)).setText(showDataRes[position]);
        }
        if (mTextSize != 0) {
            ((TextView) itemView.findViewById(R.id.txt)).setTextSize(mTextSize);
        }
        if (mTextColor != 0) {
            ((TextView) itemView.findViewById(R.id.txt)).setTextColor(mTextColor);
        }
        if (mGravity != 0) {
            ((TextView) itemView.findViewById(R.id.txt)).setGravity(mGravity);
        }
        if (leftDrawableRes != null) {
            ImageView imageView = (ImageView) itemView.findViewById(R.id.img);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(leftDrawableRes[position]);
        }

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mContentView.addView(itemView, new ViewGroup.LayoutParams(width, height));
    }

    private void addDivider() {
        View view = new View(mContentView.getContext());
        view.setBackgroundColor(0xff332838);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = 1;
        if (mDividerRes != 0) {
            view.setBackgroundResource(mDividerRes);
        }
        if (mDividerColor != 0) {
            view.setBackgroundColor(mDividerColor);
        }
        if (mDividerHeight != 0) {
            height = mDividerHeight;
        }
        mContentView.addView(view, new ViewGroup.LayoutParams(width, height));
    }
}
