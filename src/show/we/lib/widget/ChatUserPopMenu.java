package show.we.lib.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.UserSystemAPI;
import show.we.lib.config.Enums;
import show.we.lib.config.FamilyIntentKey;
import show.we.lib.config.StarRoomKey;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.model.*;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.utils.*;
import show.we.lib.widget.dialog.BaseDialog;
import show.we.lib.widget.dialog.ShutUpDialog;
import show.we.lib.widget.dialog_string.DialogString;

import java.util.List;

/**
 * Created by Administrator on 13-6-26.
 *
 * @author ll
 * @version 2.0.0
 */
public final class ChatUserPopMenu extends BaseDialog implements View.OnClickListener {
    private final static int SHOW_MAX_COUNT = 5;
    private final static int MARGIN_RIGHT = 10;
    private final static int IMAGE_WIDTH = 20;
	private static final String TAG = "ChatUserPopMenu";
    private ChatUserInfo mSelectedUserInfo;

    /**
     * ChatUserPopMenu
     *
     * @param context context
     */
    public ChatUserPopMenu(Context context) {
        super(context, R.layout.chat_user_menu);
		findViewById(R.id.home).setOnClickListener(this);
		findViewById(R.id.public_chat).setOnClickListener(this);
		findViewById(R.id.private_chat).setOnClickListener(this);
		findViewById(R.id.send_gift).setOnClickListener(this);
		findViewById(R.id.family).setOnClickListener(this);
		findViewById(R.id.shut_up).setOnClickListener(this);
    }

	/**
	 * showOperatePanel
	 * @param data data
	 */
	public void showOperatePanel(ActiveRankResult.Data data) {
		ChatUserInfo chatUserInfo = new ChatUserInfo();
		chatUserInfo.setId(data.getId());
		chatUserInfo.setName(data.getNickname());
		chatUserInfo.setVipType(data.getVipType());
		chatUserInfo.setType(data.getPriv());
		chatUserInfo.setLevel(LevelUtils.getUserLevelInfo(data.getFinance().getCoinSpendTotal()).getLevel());
		chatUserInfo.setUserPic(data.getPic());
		showOperatePanel(chatUserInfo);
        requestUserBadge(data.getId());
	}

	/**
	 * showOperatePanel
	 * @param data data
	 */
	public void showOperatePanel(RankSpendResult.Data data) {
		ChatUserInfo chatUserInfo = new ChatUserInfo();
		chatUserInfo.setId(data.getId());
		chatUserInfo.setName(data.getNickName());
		chatUserInfo.setVipType(data.getVipType());
		chatUserInfo.setType(data.getType());
		chatUserInfo.setLevel(LevelUtils.getUserLevelInfo(data.getFinance().getCoinSpendTotal()).getLevel());
		chatUserInfo.setUserPic(data.getPicUrl());
		showOperatePanel(chatUserInfo);
        requestUserBadge(data.getId());
	}

    /**
     * showOperatePanel
     *
     * @param user user
     */
    public void showOperatePanel(AudienceResult.User user) {
        ChatUserInfo chatUserInfo = new ChatUserInfo();
        chatUserInfo.setId(user.getId());
        chatUserInfo.setName(user.getNickName());
        chatUserInfo.setVipType(user.getVipType());
        chatUserInfo.setType(user.getType());
        chatUserInfo.setLevel(LevelUtils.getUserLevelInfo(user.getFinance().getCoinSpendTotal()).getLevel());
        chatUserInfo.setFamily(user.getFamily());
		chatUserInfo.setUserPic(user.getPicUrl());
        showOperatePanel(chatUserInfo);
        requestUserBadge(user.getId());
    }

    /**
     * showOperatePanel
     *
     * @param selectedUserInfo selectedUserInfo
     */
    public void showOperatePanel(ChatUserInfo selectedUserInfo) {
        if (selectedUserInfo.getId() == 0) {
            return;
        }
        UserInfoResult userInfoResult = UserInfoUtils.getUserInfo();
        if (userInfoResult == null) {
            Utils.showDifferentLoginAlertDialog(getContext(), DialogString.getmLoginNow()
                    , DialogString.getmNexttime(), DialogString.getmClickOtherUserPrompt());
            return;
        }
        AudienceResult adminInfoResult = LiveCommonData.getAdminResult();
        if (adminInfoResult == null) {
            PromptUtils.showToast(R.string.get_admin_info, Toast.LENGTH_SHORT);
            return;
        }
        if (userInfoResult.getData().getId() == selectedUserInfo.getId()) {
            PromptUtils.showToast(R.string.can_not_select_self, Toast.LENGTH_SHORT);
            return;
        }

        mSelectedUserInfo = selectedUserInfo;

		findViewById(R.id.family).setVisibility(selectedUserInfo.getFamily() == null ? View.GONE : View.VISIBLE);

		((TextView)findViewById(R.id.name)).setText(selectedUserInfo.getName());

        int userType = AudienceUtils.getUserType(userInfoResult.getData().getId(), LiveCommonData.getStarId(), adminInfoResult);
        int selectedUserType = AudienceUtils.getUserType(selectedUserInfo.getId(), LiveCommonData.getStarId(), adminInfoResult);
		selectedUserInfo.setUserType(selectedUserType);
		boolean shutUpPermission = false;
        if (userType == AudienceUtils.TYPE_AUDIENCE) {
            Enums.VipType vipType = UserInfoUtils.getUserInfo().getData().getVipType();
            if (vipType == Enums.VipType.SUPER_VIP && selectedUserType != AudienceUtils.TYPE_ADMIN && selectedUserType != AudienceUtils.TYPE_STAR
                    && selectedUserInfo.getVipType() != Enums.VipType.SUPER_VIP) {
				shutUpPermission = true;
            }
        } else if (userType == AudienceUtils.TYPE_ADMIN) {
            if (selectedUserType != AudienceUtils.TYPE_ADMIN && selectedUserType != AudienceUtils.TYPE_STAR
                    && selectedUserInfo.getVipType() == Enums.VipType.NONE) {
				shutUpPermission = true;
            }
        } else {
			shutUpPermission = true;
        }

		findViewById(R.id.shut_up).setVisibility(shutUpPermission ? View.VISIBLE : View.GONE);
        findViewById(R.id.shut_up_divider).setVisibility(shutUpPermission ? View.VISIBLE : View.GONE);

		if (selectedUserType == AudienceUtils.TYPE_STAR) {
			selectedUserInfo.setUserPic(LiveCommonData.getStarPic());
		} else {
            LiveUtils.setChatUserPicFromAudienceList(selectedUserInfo);
        }
        ImageView userPic = (ImageView)findViewById(R.id.user_head_portrait);
        ImageUtils.requestImage(userPic, selectedUserInfo.getUserPic(), Utils.getHeadPortraitWidth(userPic)
                , Utils.getHeadPortraitHeight(userPic), R.drawable.default_user_round_bg);

		if (selectedUserType == AudienceUtils.TYPE_STAR) {
			((ImageView)findViewById(R.id.star_level_gif)).setImageResource(LevelUtils.getStarLevelIcons(LiveCommonData.getStarLevel()));
			((ImageView)findViewById(R.id.user_level_gif)).setImageResource(LevelUtils.getUserLevelIcons((int) selectedUserInfo.getLevel()));
		} else {
			findViewById(R.id.star_level_gif).setVisibility(View.GONE);
			((ImageView)findViewById(R.id.user_level_gif)).setImageResource(LevelUtils.getUserLevelIcons((int) selectedUserInfo.getLevel()));
		}

		if (selectedUserInfo.getVipType() == Enums.VipType.NONE) {
			findViewById(R.id.id_vip).setVisibility(View.GONE);
		} else if (selectedUserInfo.getVipType() == Enums.VipType.COMMON_VIP) {
            ((ImageView) findViewById(R.id.id_vip)).setImageResource(R.drawable.img_vip_normal);
		} else if (selectedUserInfo.getVipType() == Enums.VipType.SUPER_VIP) {
            ((ImageView) findViewById(R.id.id_vip)).setImageResource(R.drawable.img_vip_extreme);
		} else if (selectedUserInfo.getVipType() == Enums.VipType.TRIAL_VIP) {
            ((ImageView) findViewById(R.id.id_vip)).setImageResource(R.drawable.img_trial_vip);
        }
        show();
    }
    private void requestUserBadge(long mStarId) {
        UserSystemAPI.requestBadge(mStarId)
                .execute(new BaseRequestCallback<UserBadgeResult>() {
                    @Override
                    public void onRequestSucceed(UserBadgeResult userBadgeResult) {
                        if (userBadgeResult != null) {
                            showUserBadge(userBadgeResult.getDataList());
                        }
                    }

                    @Override
                    public void onRequestFailed(UserBadgeResult result) {

                    }
                });
    }

    private void showUserBadge(List<UserBadgeResult.Data> UserBadgeResultData) {
        int marginRight = DisplayUtils.dp2px(MARGIN_RIGHT);
        int width = DisplayUtils.dp2px(IMAGE_WIDTH);
        int height = width;
        int mountCount = Math.min(UserBadgeResultData.size(), SHOW_MAX_COUNT);
        ((TableRow) findViewById(R.id.id_user_info_badge_row)).removeAllViews();

        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(width, height);
        for (int i = 0; i < mountCount; i++) {
            ImageView imageView = new ImageView(getContext());
            if (UserBadgeResultData.get(i).ismAward()) {
                ImageUtils.requestImage(imageView, UserBadgeResultData.get(i).getmSmallPic(), width, height, R.drawable.app_icon);
            } else {
                ImageUtils.requestImage(imageView, UserBadgeResultData.get(i).getmGreyPic(), width, height, R.drawable.app_icon);
            }
            imageView.invalidate();
            tableRowParams.setMargins(0, 0, marginRight, 0);
            ((TableRow) findViewById(R.id.id_user_info_badge_row)).addView(imageView, tableRowParams);
        }
        findViewById(R.id.id_user_info_badge_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(UserZoneActivity.this, MyMountActivity.class));
            }
        });
    }
/*	private void setFocusStarState() {
		findViewById(R.id.icon_focus).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (FollowedStarUtils.isMyFavStar(LiveCommonData.getStarId())) {
					RemoveFavoriteStarDialog.show(getContext(), LiveCommonData.getStarName(), LiveCommonData.getStarId());
				} else {
					RequestUtils.requestAddFavoriteStar(LiveCommonData.getStarId(), LiveCommonData.getStarName()
							, LiveCommonData.getStarPic(), LiveCommonData.getRoomCover(), LiveCommonData.isLive(), getContext());
				}
                updateFavStar(Utils.getFavStarIdList().contains(LiveCommonData.getStarId()));
			}
		});
		DataChangeNotification.getInstance().addObserver(IssueKey.ADD_FOLLOWING_SUCCESS, this, TAG);
		DataChangeNotification.getInstance().addObserver(IssueKey.CANCEL_FOCUS_STAR_SUCCESS, this, TAG);
	}

	private void updateFavStar(boolean isFollowed) {
		((ImageView)findViewById(R.id.icon_focus)).setImageResource(isFollowed
				? R.drawable.img_favorite_selected : R.drawable.img_favorite_normal);
	}*/

/*	@Override
	public void onDataChanged(IssueKey issue, Object o) {
		if (IssueKey.ADD_FOLLOWING_SUCCESS.equals(issue)) {
			updateFavStar(true);
		} else if (IssueKey.CANCEL_FOCUS_STAR_SUCCESS.equals(issue)) {
			updateFavStar(false);
		}
	}*/

	@Override
	public void dismiss() {
		super.dismiss();
//		DataChangeNotification.getInstance().removeObserver(TAG);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.home) {
			if (mSelectedUserInfo.getUserType() == AudienceUtils.TYPE_STAR) {
				enterStarZone();
			} else {
				enterUserCenter();
			}
		} else if (id == R.id.public_chat) {
			DataChangeNotification.getInstance().notifyDataChanged(IssueKey.PUBLIC_MESSAGE, mSelectedUserInfo);
		} else if (id == R.id.private_chat) {
			if (AudienceUtils.checkPrivateTalkPermission(true, getContext())) {
				DataChangeNotification.getInstance().notifyDataChanged(IssueKey.PRIVATE_MESSAGE, mSelectedUserInfo);
			}
		} else if (id == R.id.send_gift) {
			DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SELECT_SEND_GIFT, mSelectedUserInfo);
		} else if (id == R.id.family) {
			entersFamily();
		} else if (id == R.id.shut_up) {
			new ShutUpDialog(getContext(), mSelectedUserInfo).show();
		}
		dismiss();
	}

	private void enterStarZone() {
		Class cls = Utils.getClass("com.rednovo.weibo.activity.StarZoneActivity"
                , "com.rednovo.weibo_hd.ui.StarZoneActivity");
		Intent userCenterIntent = new Intent(getContext(), cls);
		userCenterIntent.putExtra(StarRoomKey.IS_LIVE_KEY, LiveCommonData.getIsLive());
		userCenterIntent.putExtra(StarRoomKey.ROOM_ID_KEY, LiveCommonData.getRoomId());
		userCenterIntent.putExtra(StarRoomKey.STAR_NICK_NAME_KEY, LiveCommonData.getStarName());
		userCenterIntent.putExtra(StarRoomKey.STAR_LEVEL_KEY, LiveCommonData.getStarLevel());
		userCenterIntent.putExtra(StarRoomKey.STAR_ID_KEY, LiveCommonData.getStarId());
		userCenterIntent.putExtra(StarRoomKey.IS_VIEW_STAR_ZONE, true);
		userCenterIntent.putExtra(StarRoomKey.IS_FROM_LIVE_ROOM, true);
		getContext().startActivity(userCenterIntent);
	}

	private void enterUserCenter() {
		Class cls = Utils.getClass("com.rednovo.weibo.activity.OtherUserZoneActivity"
                , "com.rednovo.weibo_hd.ui.OtherUserZoneActivity");
		Intent userCenterIntent = new Intent(getContext(), cls);
		userCenterIntent.putExtra("other_user_id", mSelectedUserInfo.getId());
		getContext().startActivity(userCenterIntent);
	}

	private void entersFamily() {
		if (mSelectedUserInfo.getFamily() != null) {
			String fillName = "com.rednovo.weibo.activity.FamilyDetailsActivity";
			String fillNameHD = "com.rednovo.weibo_hd.ui.FamilyDetailsActivity";
			Class cls = Utils.getClass(fillName, fillNameHD);
			if (cls != null) {
				Family mData = mSelectedUserInfo.getFamily();
				Intent intent = new Intent(getContext(), cls);
				intent.putExtra(FamilyIntentKey.FAMILY_ID, mData.getFamilyId());
				intent.putExtra(FamilyIntentKey.FAMILY_NAME, mData.getFamilyName());
				intent.putExtra(FamilyIntentKey.FAMILY_CREATE_DATE_STAMP, mData.getTimeStamp());
				intent.putExtra(FamilyIntentKey.FAMILY_BADGE_NAME, mData.getBadgeName());
				getContext().startActivity(intent);
			}
		}
	}
}
