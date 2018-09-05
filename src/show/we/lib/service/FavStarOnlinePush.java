package show.we.lib.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import show.we.sdk.cache.ImageCache;
import show.we.lib.R;
import show.we.lib.config.Config;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.config.StarRoomKey;
import show.we.lib.model.FavStarListResult;
import show.we.lib.utils.*;

/**
 * 主播上线通知
 *
 * @author ll
 * @version 1.0.0
 */
public final class FavStarOnlinePush implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static FavStarOnlinePush mInstance;

    private static final int MSG_REQUEST_FAV_STAR = 0x03;
    private static final long REQUEST_FAV_STAR_INTERVAL = 60 * 1000; //1分钟
    private static final int NOTIFY_ID = 0x012;

    private Class mClass;
    private Context mContext;
    private FavStarListResult mPreFavStarListResult;
    private boolean mIsCheck;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_REQUEST_FAV_STAR && mIsCheck) {
                RequestUtils.requestFavStar(mContext);
            }
        }
    };

    /**
     * getInstance
     *
     * @param context context
     * @return FavStarOnlinePush
     */
    public static FavStarOnlinePush getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FavStarOnlinePush(context);
        }
        return mInstance;
    }

    private FavStarOnlinePush(Context context) {
        mContext = context;
        String fillName = "show.we.ktv.activity.LiveActivity";
        String fillNameHD = "com.hongdou.activity_hd.ui.LiveActivityHD";
        mClass = Utils.getClass(fillName, fillNameHD);
        mIsCheck = StorageUtils.getSharedPreferences().getBoolean(SharedPreferenceKey.FAVORITE_STAR_ONLINE_HINT_KEY, true);
        StorageUtils.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * stop
     */
    public void stop() {
        StorageUtils.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        mIsCheck = false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (SharedPreferenceKey.FAVORITE_STAR_ONLINE_HINT_KEY.equals(key)) {
            boolean isCheck = sharedPreferences.getBoolean(key, true);
            if (isCheck && !mIsCheck) {
                RequestUtils.requestFavStar(mContext);
            }
            mIsCheck = isCheck;
        }
    }

    /**
     * checkFavStarOnline
     * @param context context
     * @param favStarListResult favStarListResult
     */
    public void checkFavStarOnline(Context context, FavStarListResult favStarListResult) {
        if (mIsCheck) {
            if (favStarListResult != null) {
                new FavStarOnlineTask(context).execute(favStarListResult);
            } else {
                updateFavStarOnlineDelay();
            }
        }
    }

    /**
     * updateFavStarOnlineDelay
     */
    public void updateFavStarOnlineDelay() {
        if (mIsCheck) {
            mHandler.removeMessages(MSG_REQUEST_FAV_STAR);
            mHandler.sendEmptyMessageDelayed(MSG_REQUEST_FAV_STAR, REQUEST_FAV_STAR_INTERVAL);
        }
    }

    class FavStarOnlineTask extends AsyncTask<FavStarListResult, FavStarListResult.StarInfo, FavStarListResult> {

        private Context mContext;
        public FavStarOnlineTask(Context context) {
            mContext = context;
        }

        @Override
        protected FavStarListResult doInBackground(FavStarListResult... params) {
            if (mPreFavStarListResult != null) {
                for (FavStarListResult.StarInfo starInfo : params[0].getData().getStarInfoList()) {
                    if (starInfo.getRoom().isLive()) {
                        boolean bNeedNotify = true;
                        for (FavStarListResult.StarInfo curStarInfo : mPreFavStarListResult.getData().getStarInfoList()) {
                            if (starInfo.getRoom().getXyStarId() == curStarInfo.getRoom().getXyStarId()) {
                                bNeedNotify = !curStarInfo.getRoom().isLive();
                                break;
                            }
                        }
                        if (bNeedNotify) {
                            publishProgress(starInfo);
                            break;
                        }
                    }
                }
            }
            return params[0];
        }

        @Override
        protected void onProgressUpdate(FavStarListResult.StarInfo... values) {
            pushNotification(mContext, values[0]);
        }

        @Override
        protected void onPostExecute(FavStarListResult favStarListResult) {
            mPreFavStarListResult = favStarListResult;
            updateFavStarOnlineDelay();
        }
    }

    private void pushNotification(Context context, FavStarListResult.StarInfo starInfo) {
        if (mClass == null) {
            return;
        }

        Intent resultIntent = new Intent(mContext, mClass);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra(StarRoomKey.ROOM_ID_KEY, starInfo.getRoom().getId());
        resultIntent.putExtra(StarRoomKey.STAR_ID_KEY, starInfo.getRoom().getXyStarId());
        resultIntent.putExtra(StarRoomKey.STAR_LEVEL_KEY,
                (int) LevelUtils.getStarLevelInfo(starInfo.getUser().getFinance().getBeanCountTotal()).getLevel());
        resultIntent.putExtra(StarRoomKey.IS_LIVE_KEY, true);
        resultIntent.putExtra(StarRoomKey.STAR_NICK_NAME_KEY, starInfo.getUser().getNickName());
        resultIntent.putExtra(StarRoomKey.IS_FROM_NOTIFICATION, true);
        resultIntent.putExtra(StarRoomKey.ROOM_COVER, starInfo.getUser().getPicUrl());

        long curTime = System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(
                context,
                (int) curTime,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String title = mContext.getString(R.string.notify_title, starInfo.getUser().getNickName());
        String text = mContext.getString(R.string.notify_sub_title);
        Notification n = new Notification(R.drawable.app_icon, title, curTime);
        n.flags = Notification.FLAG_AUTO_CANCEL; //该标志表示当用户点击 Clear 之后，能够清除该通知
        n.setLatestEventInfo(
                context,
                title,
                text,
                contentIntent);

        final RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.fav_star_notification);
        Bitmap bitmap = CacheUtils.getImageCache().getBitmapFromMemCache(starInfo.getRoom().getPicUrl()
                , null, Config.getRoomCoverWidth(), Config.getRoomCoverHeight());
        if (bitmap == null) {
            contentView.setImageViewResource(R.id.fav_notify_star_head, R.drawable.app_icon);
            CacheUtils.getImageCache().loadImageAsync(starInfo.getRoom().getPicUrl()
                    , Config.getRoomCoverWidth(), Config.getRoomCoverHeight(), new ImageCache.Callback() {
                @Override
                public void imageLoaded(String s, int i, int i2, Bitmap bitmap) {
                    contentView.setImageViewBitmap(R.id.fav_notify_star_head, bitmap);
                }
            });
        } else {
            contentView.setImageViewBitmap(R.id.fav_notify_star_head, bitmap);
        }
        contentView.setTextViewText(R.id.fav_notify_title, title);
        contentView.setTextViewText(R.id.fav_notify_content, text);
        n.contentView = contentView;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFY_ID);
        notificationManager.notify(NOTIFY_ID, n);
    }
}
