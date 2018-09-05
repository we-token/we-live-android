package show.we.lib.download;

import show.we.sdk.download.Manager;
import show.we.sdk.download.Task;
import show.we.sdk.download.TaskInfo;
import show.we.sdk.util.FileUtils;
import show.we.lib.ActivityManager;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载管理
 *
 * @author ll
 * @version 1.0.0
 */
public final class DownloadManager {

    /**
     * 下载完成回调
     */
    public static interface OnDownloadFinishedListener {
        /**
         * 下载完成回调
         *
         * @param url      下载路径
         * @param savePath 存储路径
         * @param tag      tag
         */
        public void onDownloadFinished(String url, String savePath, Object tag);
    }

    private static DownloadManager sInstance;

    private final static String TMP_EXT = ".tmp";
    private final static String DOWNLOAD_ISSUE = "file_download";
    private final static int MAX_DOWNLOAD_THREAD_COUNT = 3;

    private Map<String, TaskInfo> mTaskInfoMap = new HashMap<String, TaskInfo>();

    /**
     * 单例
     *
     * @return DownloadManager 实例
     */
    public final static DownloadManager instance() {
        if (sInstance == null) {
            sInstance = new DownloadManager();
            Manager.instance().addIssue(DOWNLOAD_ISSUE, MAX_DOWNLOAD_THREAD_COUNT);
        }

        return sInstance;
    }

    /**
     * 下载文件
     *
     * @param url      下载路径
     * @param savePath 存储路径
     * @param tag      tag
     */
    public void download(String url, String savePath, Object tag) {
        savePath += TMP_EXT;
        if (!mTaskInfoMap.containsKey(savePath)) {
            TaskInfo taskInfo = new TaskInfo(url, savePath);
            taskInfo.setTag(new ArrayList<Object>());
            ((ArrayList<Object>) taskInfo.getTag()).add(tag);
            mTaskInfoMap.put(savePath, taskInfo);
            Manager.instance().execute(DOWNLOAD_ISSUE, taskInfo, mCallback);
        } else {
            ((ArrayList<Object>) mTaskInfoMap.get(savePath).getTag()).add(tag);
        }
    }

    /**
     * 是否正在下载
     *
     * @param savePath savePath
     * @return true表示正在下载
     */
    public boolean isDownloading(String savePath) {
        savePath += TMP_EXT;
        return mTaskInfoMap.containsKey(savePath);
    }

    private Task.Callback mCallback = new Task.Callback() {
        @Override
        public void onFinished(TaskInfo taskInfo) {
            super.onFinished(taskInfo);
            String savePath = taskInfo.getSavePath();
            mTaskInfoMap.remove(savePath);
            String dstSavePath = savePath.substring(0, savePath.length() - TMP_EXT.length());
            FileUtils.rename(savePath, dstSavePath);

            OnDownloadFinishedListener listener = null;
            if (ActivityManager.instance().getCurrentActivity() instanceof OnDownloadFinishedListener) {
                listener = (OnDownloadFinishedListener) ActivityManager.instance().getCurrentActivity();
            }
            ArrayList<Object> tags = (ArrayList<Object>) taskInfo.getTag();
            for (Object tag : tags) {
                if (listener != null) {
                    listener.onDownloadFinished(taskInfo.getSourceUrl(), dstSavePath, tag);
                }

                Object[] objects = new Object[]{taskInfo.getSourceUrl(), dstSavePath, tag};
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.DOWNLOAD_COMPLETED, objects);
            }
        }

//        @Override
//        public void onError(TaskInfo taskInfo, Task.DownloadError msg) {
//            super.onError(taskInfo, msg);
//            mTaskInfoMap.remove(taskInfo.getSavePath());
//            FileUtils.delete(taskInfo.getSavePath());
//        }
    };
}
