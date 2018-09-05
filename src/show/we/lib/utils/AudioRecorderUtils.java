package show.we.lib.utils;

import android.media.MediaRecorder;
import show.we.sdk.util.FileUtils;
import show.we.lib.config.Config;

import java.io.IOException;

/**
 * Created by Administrator on 13-6-20.
 *
 * @author longfei.zhang
 * @version 2.0.0
 */
public final class AudioRecorderUtils {

    /**
     * listener to the recorder file reached the max size
     */
    public static interface OnRecorderInfoListener {
        /**
         * called when recorder file size reached max
         *
         * @param duration 时长
         */
        void onRecordExceedMaxDuration(int duration);
    }

    private static final int MAX_DURATION_TIME = 1000 * 60;
    private static final int AMPLITUDE_BASE = 600;
    private static final int RATIO_BASE = 20;

    private String mFilePath;
    private MediaRecorder mMediaRecorder;
    private MediaRecorder.OnInfoListener mOnInfoListener;
    private OnRecorderInfoListener mListener;

    private boolean mIsRecording;
    private long mStartTime;
    private int mDuration;

    /**
     * ktvAudioRecorder constructor method
     *
     * @param listener OnRecorderInfoListener
     */
    public AudioRecorderUtils(OnRecorderInfoListener listener) {
        String filePath = Config.getRecorderCacheFolderPath() + "/ktv_recorder.aac";
        if (!FileUtils.fileExists(filePath)) {
            FileUtils.createFile(filePath);
        }
        try {
            mMediaRecorder = new MediaRecorder();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        mListener = listener;
        mFilePath = filePath;

        mOnInfoListener = new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    stopRecording();
                    if (mListener != null) {
                        mListener.onRecordExceedMaxDuration(MAX_DURATION_TIME);
                    }
                }
            }
        };
    }

    /**
     * file path get method
     *
     * @return 录音文件路径
     */
    public String getFilePath() {
        return mFilePath;
    }

    /**
     * mIsRecording get method
     *
     * @return is or not recording
     */
    public boolean isRecording() {
        return mIsRecording;
    }

    /**
     * get the audio recorder duration
     *
     * @return duration
     */
    public int getDuration() {
        return mDuration;
    }

    /**
     * start to record audio
     *
     * @throws IOException 录音初始化时的IO异常
     */
    public void startRecording() throws IOException {
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.reset();
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mMediaRecorder.setOutputFile(mFilePath);
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                mMediaRecorder.setMaxDuration(MAX_DURATION_TIME);
                mMediaRecorder.setOnInfoListener(mOnInfoListener);
                mMediaRecorder.prepare();

                mMediaRecorder.start();
            } catch (RuntimeException re) {
                re.printStackTrace();
            }

            mIsRecording = true;
            mStartTime = System.currentTimeMillis();
        }
    }

    /**
     * get current max amplitude
     *
     * @return max amplitude
     */
    public int getMicMaxVolume() {
        if (mMediaRecorder != null) {
            try {
                int ratio = mMediaRecorder.getMaxAmplitude() / AMPLITUDE_BASE;
                int db = 0;
                if (ratio > 1) {
                    db = (int) (RATIO_BASE * Math.log10(ratio));
                }
                return db;
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * stop to record audio and reset to
     */
    public void stopRecording() {
        if (mIsRecording && mMediaRecorder != null) {
            try {
                mMediaRecorder.stop();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
            mIsRecording = false;
            mDuration = (int) (System.currentTimeMillis() - mStartTime);
        }
    }

    /**
     * release media recorder resources and destroy this object
     */
    public void destroy() {
        stopRecording();
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
}
