package show.we.lib.upload;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import show.we.sdk.util.FileUtils;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.PublicAPI;
import show.we.lib.model.SecretKeyResult;
import show.we.lib.upload.upyun.UpYun;
import show.we.lib.utils.MessageSendUtils;
import show.we.lib.utils.PromptUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 声音上传管理
 *
 * @author ll
 * @version 1.0.0
 */
public class VoiceUploadUtils {

    /**
     * 声音上传
     *
     * @param context     context
     * @param accessToken accessToken
     * @param sourcePath  文件路径
     * @param duration    录音时长
     * @throws java.io.FileNotFoundException 文件未找到的异常
     */
    public static void upload(final Context context, String accessToken, final String sourcePath, final int duration) throws FileNotFoundException {
        if (!FileUtils.fileExists(sourcePath)) {
            throw new FileNotFoundException(sourcePath + " not exists");
        }
        PublicAPI.secretKey(accessToken, FileUtils.fileLength(sourcePath), duration).execute(new BaseRequestCallback<SecretKeyResult>() {
            @Override
            public void onRequestSucceed(SecretKeyResult secretKeyResult) {
                new UploadTask(context, sourcePath, secretKeyResult, duration).execute();
            }

            @Override
            public void onRequestFailed(SecretKeyResult secretKeyResult) {
                notifyUploadFailure();
            }
        });
    }

    private static void notifyUploadFailure() {
        PromptUtils.showToast(R.string.recorder_file_upload_failure, Toast.LENGTH_SHORT);
    }

    private static void notifyUploadVoiceSuccess(Context context, String dstPath, int duration) {
        PromptUtils.showToast(R.string.recorder_file_upload_success, Toast.LENGTH_SHORT);
        MessageSendUtils.sendVoiceMessage(context, dstPath, duration);
    }

    private static class UploadTask extends AsyncTask<String, Integer, String> {
        private Context mContext;
        private SecretKeyResult mSecretKeyResult;
        private String mSourcePath;
        private int mDuration;

        public UploadTask(Context context, String sourcePath, SecretKeyResult secretKeyResult, int duration) {
            mContext = context;
            mSourcePath = sourcePath;
            mSecretKeyResult = secretKeyResult;
            mDuration = duration;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return new UpYun(UpYun.FILE_BUCKET_NAME).writeFile(mSecretKeyResult.getData().getPath(), new File(mSourcePath),
                        mSecretKeyResult.getData().getHost(), mSecretKeyResult.getData().getCurrentTime(), mSecretKeyResult.getData().getAuthorization());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String md5) {
            if (md5 != null) {
                String path = "http://" + UpYun.FILE_BUCKET_NAME + "." + UpYun.HOST + mSecretKeyResult.getData().getPath();
                notifyUploadVoiceSuccess(mContext, path, mDuration);
            } else {
                notifyUploadFailure();
            }
        }
    }
}
