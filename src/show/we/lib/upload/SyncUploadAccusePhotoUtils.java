package show.we.lib.upload;

import show.we.sdk.util.FileUtils;
import show.we.lib.cloudapi.UserSystemAPI;
import show.we.lib.model.UploadTokenResult;
import show.we.lib.upload.upyun.UpYun;
import show.we.lib.upload.upyun.UpYunException;
import show.we.lib.upload.upyun.Uploader;

/**
 * 同步上传举报图片，放在子线程里面做
 * @author ll
 * @version 1.0.0
 */
public class SyncUploadAccusePhotoUtils {

    /**
     * 上传图片
     * @param accessToken accessToken
     * @param sourcePath sourcePath
     * @return url
     */
    public static String upload(String accessToken, final String sourcePath) {
        if (!FileUtils.fileExists(sourcePath)) {
            return null;
        }
        UploadTokenResult tokenResult = UserSystemAPI.accusePhotoToken(accessToken).execute();
        if (tokenResult != null && tokenResult.isSuccess()) {
            try {
                return "http://" + UpYun.ACCUSE_PHOTO + "." + UpYun.HOST
                        + Uploader.upload(tokenResult.getData().getPolicy(), tokenResult.getData().getSignature(), UpYun.ACCUSE_PHOTO, sourcePath);
            } catch (UpYunException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
