package show.we.lib.widget.dialog;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import show.we.sdk.request.BaseResult;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.UserManageAPI;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.utils.UserInfoUtils;

/**
 * Created by CG on 14-3-11.
 * @author ll
 * @version 3.5.0
 */
public class ShutUpDialog extends BaseDialog implements View.OnClickListener {

	private ChatUserInfo mSelectedUserInfo;

	/**
	 * ShutUpDialog
	 * @param context context
	 * @param selectedUserInfo selectedUserInfo
	 */
	public ShutUpDialog(Context context, ChatUserInfo selectedUserInfo) {
		super(context, R.layout.shutup);
		findViewById(R.id.shut_up_five).setOnClickListener(this);
		findViewById(R.id.shut_up_a_hour).setOnClickListener(this);
		findViewById(R.id.shut_up_twelve_hours).setOnClickListener(this);
		findViewById(R.id.recover).setOnClickListener(this);
		mSelectedUserInfo = selectedUserInfo;
	}

	@Override
	public void onClick(View v) {
		String token = UserInfoUtils.getAccessToken();
		long roomId = LiveCommonData.getRoomId();
		int id = v.getId();
		if (id == R.id.shut_up_five) {
            final int fiveMinutes = 5; // minute
            UserManageAPI.shutUp(token, roomId, mSelectedUserInfo.getId(), fiveMinutes).execute(new ShutUpCallback(fiveMinutes));
		} else if (id == R.id.shut_up_a_hour) {
            final int sixtyMinutes = 60; // minute
            UserManageAPI.shutUp(token, roomId, mSelectedUserInfo.getId(), sixtyMinutes).execute(new ShutUpCallback(sixtyMinutes));
		} else if (id == R.id.shut_up_twelve_hours) {
            final int twelveHours = 720; // minute
            UserManageAPI.shutUp(token, roomId, mSelectedUserInfo.getId(), twelveHours).execute(new ShutUpCallback(twelveHours));
		} else if (id == R.id.recover) {
			UserManageAPI.recover(token, roomId, mSelectedUserInfo.getId()).execute(new ShutUpCallback(0));
		}
		dismiss();
	}

	private class ShutUpCallback extends BaseRequestCallback<BaseResult> {

		private int mMinute;

		/**
		 * ShutUpCallback
		 * @param minute minute
		 */
		public ShutUpCallback(int minute) {
			mMinute = minute;
		}

		@Override
		public void onRequestSucceed(BaseResult dataResult) {
			String message1 = getContext().getString(R.string.shut_up_info) + "\n";
			String name = mSelectedUserInfo.getName() + "\n";
			String message2;
			if (mMinute > 0) {
				final int fiveMinutes = 5; // minute
				final int sixtyMinutes = 60; // minute
				if (mMinute == fiveMinutes) {
					message2 = getContext().getString(R.string.shut_up_five_minutes);
				} else if (mMinute == sixtyMinutes) {
					message2 = getContext().getString(R.string.shut_up_a_hour);
				} else {
					message2 = getContext().getString(R.string.shut_up_twelve_hours);
				}
			} else {
				message2 = getContext().getString(R.string.recover);
			}
			SpannableString message = new SpannableString(message1 + name + message2);
			final int color = 0xff0099cc;
			message.setSpan(new ForegroundColorSpan(color), message1.length(), (message1 + name).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			new MessageDialog(getContext(), message).show();
		}

		@Override
		public void onRequestFailed(BaseResult dataResult) {
		}
	}
}
