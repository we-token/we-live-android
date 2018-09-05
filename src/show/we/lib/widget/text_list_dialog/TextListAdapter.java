package show.we.lib.widget.text_list_dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.widget.SimpleBaseAdapter;

/**
 * Created by CG on 14-2-27.
 *
 * @author ll
 * @version 4.0.0
 */
public class TextListAdapter extends SimpleBaseAdapter {

    private TextListControler mControler;

    /**
     * TextListAdapter
     *
     * @param controler controler
     */
    public TextListAdapter(TextListControler controler) {
        mControler = controler;
    }

    @Override
    public int getCount() {
        int[] showDataRes = mControler.getShowDataRes();
        String[] showData = mControler.getShowData();
        return showData != null ? showData.length : (showDataRes != null ? showDataRes.length : 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int[] showDataRes = mControler.getShowDataRes();
        String[] showData = mControler.getShowData();
        int[] leftDrawableRes = mControler.getLeftDrawableRes();
        ListView listView = mControler.getListView();

        if (convertView == null) {
            convertView = View.inflate(listView.getContext(), listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE
                    ? R.layout.layout_list_single_choice_item : R.layout.layout_text_list_item, null);
        }
        if (mControler.getItemBackgroundRes() != 0) {
            convertView.setBackgroundResource(mControler.getItemBackgroundRes());
        }
        if (showData != null) {
            ((TextView) convertView.findViewById(R.id.txt)).setText(showData[position]);
        } else if (showDataRes != null) {
            ((TextView) convertView.findViewById(R.id.txt)).setText(showDataRes[position]);
        }
        if (mControler.getTextSize() != 0) {
            ((TextView) convertView.findViewById(R.id.txt)).setTextSize(mControler.getTextSize());
        }
        if (mControler.getTextColor() != 0) {
            ((TextView) convertView.findViewById(R.id.txt)).setTextColor(mControler.getTextColor());
        }
        if (mControler.getTextColorStateList() != null) {
            ((TextView) convertView.findViewById(R.id.txt)).setTextColor(mControler.getTextColorStateList());
        }
        if (leftDrawableRes != null && listView.getChoiceMode() != ListView.CHOICE_MODE_SINGLE) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(leftDrawableRes[position]);
        }
        return convertView;
    }
}
