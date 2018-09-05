package show.we.lib.widget.chat.spannable_string;

import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.BaseAdapter;
import android.widget.TextView;
import show.we.sdk.cache.ImageCache;
import show.we.lib.R;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Enums;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.model.Message;
import show.we.lib.model.MountListResult;
import show.we.lib.model.UserInfoResult;
import show.we.lib.utils.CacheUtils;
import show.we.lib.utils.LevelUtils;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.UserInfoUtils;
import show.we.lib.utils.Utils;
import show.we.lib.widget.chat.spannable_event.ClickSpan;

import java.util.List;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class EntryRoomString extends ChatString {

    private static final int VIP_HIDING = 1;
    private UserInfoResult mUserInfoResult;
    /**
     * EntryRoomString
     *
     * @param message message
     * @param view       view
     * @param adapter    adapter
     */
    public EntryRoomString(Message.EnterRoomModel message, TextView view, BaseAdapter adapter) {
        super(view.getContext());
        mBuilder = processRoomChanged(message, view, adapter);
    }

    private SpannableStringBuilder processRoomChanged(Message.EnterRoomModel message, TextView view, BaseAdapter adapter) {
        long id = 0;
        String nickName = "";
        int vip = 0;
        int type = 0;
        long mountId = 0;
        int vipHiding = 0;
        long level = 0;
        if (message != null) {
            Message.EnterRoomModel.Data data = message.getData();
            id = data.getUserId();
            nickName = Utils.html2String(data.getNickName());
            vip = data.getVip();
            type = data.getUserType();
            mountId = data.getMountId();
            vipHiding = data.getVipHidingFlag();
            level = LevelUtils.getUserLevelInfo(data.getSpend()).getLevel();
        }
        Enums.VipType vipType = Enums.VipType.NONE;
        for (Enums.VipType t : Enums.VipType.values()) {
            if (t.getValue() == vip) {
                vipType = t;
            }
        }

        ChatUserInfo user = new ChatUserInfo(id, nickName, vipType, type, 0, vipHiding == VIP_HIDING);
        String fakeName = vipHiding == 1 ? mContext.getString(R.string.mysterious_person) : nickName;
        SpannableStringBuilder stringBuilder = null;
        stringBuilder = new SpannableStringBuilder(fakeName + SPACE_SEPARATION + mInRoom);
        int start = 0;
        int end = start + fakeName.length();
        if (!user.isVipHiding()) {
            ClickSpan clickSpan = new ClickSpan(mContext, vip == 2 ? mPurpleColor : mBlueColor, user);
            stringBuilder.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        start = end;
        end = stringBuilder.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (!user.isVipHiding()) {
            SpannableStringBuilder userMount = processUserMount(mountId, adapter);
            stringBuilder.insert((fakeName + SPACE_SEPARATION).length(), userMount);
        }

        SpannableStringBuilder userType = processUserType(level, type, vipType, id, view);
        stringBuilder.insert(0, userType);
        return stringBuilder;
    }

    private SpannableStringBuilder processUserMount(long mountId, final BaseAdapter adapter) {
        SpannableStringBuilder userMount = new SpannableStringBuilder("");
        if (mountId == 0) {
            userMount.insert(0, mWalk);
            userMount.setSpan(new ForegroundColorSpan(mGrayColor),
                    mWalk.length(), userMount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (CacheUtils.getObjectCache().contain(CacheObjectKey.MOUNT_MALL_KEY)) {
            MountInfo mountInfo = getMountInfoFromMountId(mountId);
            if (mountInfo != null && mountInfo.mMountName != null && mountInfo.mMountPicUrl != null) {
                String action = mRide;
                if (mountInfo.mMountName.equals(mContext.getString(R.string.ferrari))) {
                    action = mContext.getString(R.string.drive);
                }
                userMount.insert(0, action + mountInfo.mMountName);
                Bitmap mountPic = CacheUtils.getImageCache().getBitmapFromMemCache(mountInfo.mMountPicUrl, null
                        , Integer.MAX_VALUE, Integer.MAX_VALUE);
                if (mountPic != null) {
                    userMount.setSpan(new ImageSpan(mContext, mountPic), action.length(), userMount.length()
                            , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    userMount.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.pink)),
                            action.length(), userMount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    CacheUtils.getImageCache().loadImageAsync(mountInfo.mMountPicUrl, Integer.MAX_VALUE, Integer.MAX_VALUE
                            , new ImageCache.Callback() {
                        @Override
                        public void imageLoaded(String s, int i, int i2, Bitmap bitmap) {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
        return userMount;
    }

    private class MountInfo {
        private String mMountName;
        private String mMountPicUrl;
    }

    private MountInfo getMountInfoFromMountId(long mountId) {
        List<MountListResult.MountItem> mountItemList =
                ((MountListResult) CacheUtils.getObjectCache().get(CacheObjectKey.MOUNT_MALL_KEY)).getDataList();
        for (MountListResult.MountItem mountItem : mountItemList) {
            if (mountItem.getId() == mountId) {
                MountInfo mountInfo = new MountInfo();
                mountInfo.mMountName = mountItem.getName();
                mountInfo.mMountPicUrl = mountItem.getPicUrl();
                return mountInfo;
            }
        }
        return null;
    }
}
