package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * Created by CG on 13-11-18.
 *
 * @author ll
 * @version 3.1.0
 */
public class FamilyMemberData implements Serializable {
    @SerializedName("_id")
    private long mId;

    @SerializedName("finance")
    private Finance mFinance;

    @SerializedName("nick_name")
    private String mNickName;

    @SerializedName("pic")
    private String mPic;

    // 1表示为族长， 2表示为副族长， 其他值表示为成员
    public static final int BIG_LEADER = 1;
    public static final int NORMAL_LEADER = 2;
    private int mLeaderTag = -1;


    public long getId() {
        return mId;
    }

    public Finance getFinance() {
        return mFinance;
    }

    public String getNickName() {
        return Utils.html2String(mNickName);
    }

    public String getPic() {
        return mPic;
    }

    public int getLeaderTag() {
        return mLeaderTag;
    }

    public void setLeaderTag(int leaderTag) {
        this.mLeaderTag = leaderTag;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FamilyMemberData) {
            FamilyMemberData data = (FamilyMemberData) o;
            if (data.getId() == getId()) {
                return true;
            }
        }
        return false;
    }
}
