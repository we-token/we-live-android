package show.we.lib.widget.text_list_dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * Created by CG on 14-2-27.
 *
 * @param <T> 泛型
 * @author ll
 * @version 4.0.0
 */
public class TextListControler<T> extends TextData<T> implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private TextListAdapter mAdapter;

    /**
     * TextListControler
     *
     * @param listView    listView
     * @param dialog      dialog
     * @param popupWindow popupWindow
     */
    public TextListControler(ListView listView, Dialog dialog, PopupWindow popupWindow) {
        super(dialog, popupWindow);
        mListView = listView;
        mAdapter = new TextListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * getListView
     *
     * @return mListView
     */
    public ListView getListView() {
        return mListView;
    }

    @Override
    public void setBackgroundRes(int backgroundRes) {
        super.setBackgroundRes(backgroundRes);
        if (mBackgroundRes != 0) {
            mListView.setBackgroundResource(mBackgroundRes);
        }
    }

    /**
     * setListSelectorColor
     * @param color color
     */
    public void setListSelectorColor(int color) {
        mListView.setSelector(new ColorDrawable(color));
    }

    /**
     * setListSelectorResId
     * @param resId resId
     */
    public void setListSelectorResId(int resId) {
        mListView.setSelector(resId);
    }

    /**
     * setListSelector
     * @param selector selector
     */
    public void setListSelector(Drawable selector) {
        mListView.setSelector(selector);
    }

    @Override
    public void setDividerRes(int dividerRes) {
        super.setDividerRes(dividerRes);
        if (mDividerRes != 0) {
            mListView.setDivider(mListView.getResources().getDrawable(mDividerRes));
        }
    }

    @Override
    public void setDividerColor(int dividerColor) {
        super.setDividerColor(dividerColor);
        if (mDividerColor != 0) {
            mListView.setDivider(new ColorDrawable(mDividerColor));
        }
    }

    @Override
    public void setDividerHeight(int dividerHeight) {
        super.setDividerHeight(dividerHeight);
        if (mDividerHeight != 0) {
            mListView.setDividerHeight(mDividerHeight);
        }
    }

    @Override
    public void setShowDataRes(int[] showDataRes) {
        super.setShowDataRes(showDataRes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setShowData(String[] showData) {
        super.setShowData(showData);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * setSingleChoice
     */
    public void setSingleChoice() {
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    /**
     * setItemChecked
     *
     * @param position position
     */
    public void setItemChecked(int position) {
        mListView.setItemChecked(position, true);
        mListView.setSelection(position);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mValueSelectListener != null) {
            position -= mListView.getHeaderViewsCount();
            if (position < 0 || position >= mAdapter.getCount()) {
                return;
            }
            mValueSelectListener.onValueSelected(mDialog, mPopupWindow, position
                    , mShowData == null ? (mShowDataRes == null ? "" : mListView.getContext().getString(mShowDataRes[position]))
                    : mShowData[position]
                    , mMessages == null ? "" : mMessages[position]
                    , mValues == null ? 0 : mValues[position]
                    , mObjects == null ? null : mObjects[position]
            );
        }
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }
}
