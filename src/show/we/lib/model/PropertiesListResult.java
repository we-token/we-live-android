package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ll on 14-6-4.
 * @version v1.0.0
 */
public class PropertiesListResult extends BaseListResult<PropertiesListResult.Data> {
    public static final String FIRST_CHARGE_TIP = "first_charge_Marquee_tip";
    public static final String REPORT_TIP = "report_tip";
    public static class Data {
        @SerializedName("_id")
        private String mId;

        @SerializedName("content")
        private String mContent;

        public String getmId() {
            return mId;
        }

        public String getmContent() {
            return mContent;
        }
    }
}
