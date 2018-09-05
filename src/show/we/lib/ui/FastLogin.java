package show.we.lib.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import show.we.sdk.util.EnvironmentUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.utils.Utils;
import show.we.lib.widget.text_list_dialog.OnValueSelectListener;
import show.we.lib.widget.text_list_dialog.TextItemsPopWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 在测试模式下，可以选择账号登陆
 * Created by CG on 13-10-22.
 *
 * @author ll
 * @version 1.1.0
 */
public class FastLogin implements View.OnClickListener {

    private static final String TEST_HELP = "test_help";
    private static final String TEST_HELP_USER_NAME = "test_help_user_name";
    private static final String TEST_HELP_PASSWORD = "test_help_password";

    private SharedPreferences mTestHelpSharedPreferences;
    private ArrayList<String> mUserNames;
    private ArrayList<String> mPasswords;
    private TextItemsPopWindow mPopWindow;

    /**
     * 帮助程序员和测试员快速登录
     *
     * @param userName userName
     * @param password password
     * @param loginBtn loginBtn
     */
    public FastLogin(final EditText userName, final EditText password, final View loginBtn) {
        if (EnvironmentUtils.Config.isTestMode()) {
            userName.setOnClickListener(this);
            password.setOnClickListener(this);

            mUserNames = new ArrayList<String>();
            mPasswords = new ArrayList<String>();
            mTestHelpSharedPreferences = userName.getContext().getSharedPreferences(TEST_HELP, Context.MODE_PRIVATE);
            mPopWindow = new TextItemsPopWindow<Object>(userName.getContext(), new OnValueSelectListener<Object>() {
                @Override
                public void onValueSelected(Dialog dialog, PopupWindow popupWindow, int position, String show
                        , String message, long value, Object o) {
                    userName.setText(show);
                    password.setText(mPasswords.get(mUserNames.indexOf(show)));
                    loginBtn.performClick();
                    popupWindow.dismiss();
                }
            });
            mPopWindow.getTextItemsControler().setTextColor(loginBtn.getContext().getResources().getColor(R.color.sub_title_text));
            parser();
        }
    }

    /**
     * updateUserInfo
     *
     * @param userName userName
     * @param password password
     */
    public void updateUserInfo(String userName, String password) {
        if (EnvironmentUtils.Config.isTestMode()) {
            if (mUserNames.contains(userName)) {
                mPasswords.set(mUserNames.indexOf(userName), password);
                try {
                    save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                mUserNames.add(userName);
                mPasswords.add(password);
                try {
                    save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parser() {
        String userInfo = mTestHelpSharedPreferences.getString(TEST_HELP, null);
        if (StringUtils.isEmpty(userInfo)) {
            return;
        }
        try {
            JSONArray userNameJsonArray = new JSONArray(userInfo);
            int length = userNameJsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = userNameJsonArray.optJSONObject(i);
                if (jsonObject != null) {
                    String userName = jsonObject.optString(TEST_HELP_USER_NAME);
                    String password = jsonObject.optString(TEST_HELP_PASSWORD);
                    if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
                        mUserNames.add(userName);
                        mPasswords.add(password);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void save() throws JSONException {
        int size = mUserNames.size();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < size; i++) {
            String userName = mUserNames.get(i);
            String password = mPasswords.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TEST_HELP_USER_NAME, userName);
            jsonObject.put(TEST_HELP_PASSWORD, password);
            jsonArray.put(jsonObject);
        }
        mTestHelpSharedPreferences.edit().putString(TEST_HELP, jsonArray.toString()).commit();
    }

    @Override
    public void onClick(View v) {
        if (mUserNames.size() > 0) {
            mPopWindow.getTextItemsControler().setShowData(Utils.listToArray(mUserNames));
            mPopWindow.showWithSameWidth(v, 0);
        }
    }
}
