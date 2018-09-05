package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;

/**
 * 上传文件所需密钥数据结构
 * @author ll
 * @version 1.0.0
 */
public class UploadTokenResult extends BaseResult implements Serializable {

    @SerializedName("data")
    private Data mData;

    /**
     * @return mData
     */
    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable  {
        @SerializedName("action")
        private String mAction;
        @SerializedName("policy")
        private String mPolicy;
        @SerializedName("signature")
        private String mSignature;

        public String getPolicy() {
            return mPolicy;
        }

        public String getAction() {
            return mAction;
        }

        public String getSignature() {
            return mSignature;
        }
    }
}
