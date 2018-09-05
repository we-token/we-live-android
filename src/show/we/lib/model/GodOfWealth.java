package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.UserInfoUtils;
import show.we.lib.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CG on 14-1-17.
 *
 * @author ll
 * @version 3.4.0
 */
public class GodOfWealth {

    @SerializedName("action")
    private String mAction;

    @SerializedName("data_d")
    private Data mData;

    public String getAction() {
        return mAction;
    }

    public Data getData() {
        return mData;
    }

    public class Data {
        @SerializedName("current_user")
        private String mCurrentUser;
        @SerializedName("other")
        private int mOther;
        @SerializedName("third_user")
        private List<AwardUser> mAwardUser;

        public List<AwardUser> getAwardUser() {
            return mAwardUser == null ? new ArrayList<AwardUser>() : mAwardUser;
        }

        public void setCoinOrigin() {
            if (mAwardUser != null) {
                for (AwardUser awardUser : mAwardUser) {
                    awardUser.setCurrentUser(mCurrentUser);
                }
            }
        }

        public AwardUser getOtherAward() {
            AwardUser awardUser = new AwardUser();
            awardUser.setCurrentUser(mCurrentUser);
            awardUser.mUser = new User();
            awardUser.mUser.mId = UserInfoUtils.getUserInfo().getData().getId();
            awardUser.mUser.mFinance = UserInfoUtils.getUserInfo().getData().getFinance();
            awardUser.mUser.mNickName = UserInfoUtils.getUserInfo().getData().getNickName();
            awardUser.mObtainCoin = mOther;
            return awardUser;
        }
    }

    public static class AwardUser {
        @SerializedName("user")
        private User mUser;
        @SerializedName("obtain_coin")
        private int mObtainCoin;
        @SerializedName("fortune_god")
        private String mFortuneGod;
        @SerializedName("room_id")
        private long mRoomId;
        @SerializedName("t")
        private long mTime;
        private String mCurrentUser;

        public User getUser() {
            return mUser == null ? new User() : mUser;
        }

        public int getObtainCoin() {
            return mObtainCoin;
        }

        public String getFortuneGod() {
            return mFortuneGod;
        }

        public long getRoomId() {
            return mRoomId;
        }

        public long getTime() {
            return mTime;
        }

        public void setCurrentUser(String user) {
            mCurrentUser = user;
        }

        public String getCurrentUser() {
            return Utils.html2String(mCurrentUser);
        }
    }

    public static class User {
        @SerializedName("_id")
        private long mId;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mNickName;

        public long getId() {
            return mId;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public String getNickName() {
            return mNickName == null ? "" : Utils.html2String(mNickName);
        }
    }
}
