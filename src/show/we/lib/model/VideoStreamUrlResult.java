package show.we.lib.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * Created by Administrator on 13-7-29.
 *
 * @author ll
 * @version 1.0.0
 */
public class VideoStreamUrlResult extends BaseResult implements Serializable {

	@SerializedName("videoStreamUrl")
	private String mVideoStreamUrl;
	@SerializedName("streamUrl")
	private String mStreamUrl;
	@SerializedName("type")
	private String mType;

	public String getVideoStreamUrl() {
		return mVideoStreamUrl;
	}

	public String getStreamUrl() {
		return mStreamUrl;
	}

	public String getType() {
		return mType;
	}
}
