package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 上传文件所需密钥数据结构
 *
 * @author ll
 * @version 1.0.0
 */

public class SecretKeyResult extends BaseResult {

    @SerializedName("data")
    private Data mData;

    /**
     * @return mData
     */
    public Data getData() {
        return mData;
    }

    /**
     * 秘钥数据
     */
    public static class Data {
        @SerializedName("authorization")
        private String mAuthorization;   //签名

        @SerializedName("date")
        private String mCurrentTime; //服务器当前时间

        @SerializedName("path")
        private String mPath; //服务器返回路径

        @SerializedName("host")
        private String mHost; //云存储host

        @SerializedName("flag")
        private int mFlag; //flag

        /**
         * @return 签名
         */
        public String getAuthorization() {
            return mAuthorization;
        }

        /**
         * @return 服务器时间
         */
        public String getCurrentTime() {
            return mCurrentTime;
        }

        /**
         * @return 服务器返回路径
         */
        public String getPath() {
            return mPath;
        }

        /**
         * @return 云存储Host
         */
        public String getHost() {
            return mHost;
        }

        /**
         * @return 标识
         */
        public int getFlag() {
            return mFlag;
        }
    }
}
