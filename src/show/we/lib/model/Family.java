package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class Family implements Serializable {

    @SerializedName("family_id")
    private long mFamilyId;

    @SerializedName("family_priv")
    private int mFamilyPriv;

    @SerializedName("timestamp")
    private long mTimeStamp;

    @SerializedName("family_name")
    private String mFamilyName;

    @SerializedName("badge_name")
    private String mBadgeName;

    /**
     * week_support = 1就是荣耀家族
     */
    @SerializedName("week_support")
    private int mWeekSupport;

    public long getFamilyId() {
        return mFamilyId;
    }

    public int getFamilyPriv() {
        return mFamilyPriv;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public String getFamilyName() {
        return Utils.html2String(mFamilyName);
    }

    public String getBadgeName() {
        return Utils.html2String(mBadgeName);
    }

    public int getWeekSupport() {
        return mWeekSupport;
    }

    public void setFamilyId(long mFamilyId) {
        this.mFamilyId = mFamilyId;
    }

    public void setFamilyPriv(int mFamilyPriv) {
        this.mFamilyPriv = mFamilyPriv;
    }

    public void setTimeStamp(long mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public void setFamilyName(String mFamilyName) {
        this.mFamilyName = mFamilyName;
    }

    public void setBadgeName(String mBadgeName) {
        this.mBadgeName = mBadgeName;
    }

    public void setWeekSupport(int mWeekSupport) {
        this.mWeekSupport = mWeekSupport;
    }
}
