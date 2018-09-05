package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author ll
 * @version 3.9.0
 * Date: 2014.05.07
 */
public class PublicInformResult extends BaseListResult<PublicInformResult.Data> {
    public static class Data {

        @SerializedName("msg")
        private String mMsg;

        @SerializedName("url")
        private String mUrl;

        public String getmMsg() {
            return mMsg;
        }

        public String getmUrl() {
            return mUrl;
        }
    }
}
