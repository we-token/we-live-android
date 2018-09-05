package show.we.lib.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import show.we.lib.config.Enums;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.config.StarRoomKey;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.model.AudienceResult;
import show.we.lib.model.Finance;
import show.we.lib.model.Message;
import show.we.lib.model.StarInfoResult;
import show.we.lib.utils.AudienceUtils;
import show.we.lib.utils.LevelUtils;
import show.we.lib.utils.LiveIntentBuilder;
import show.we.lib.utils.StorageUtils;
import show.we.lib.websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CG on 13-12-1.
 *
 * @author ll
 * @version 3.2.0
 */
public class LiveCommonData {

    private static long mRoomId;
    private static String mRoomCover;
    private static boolean mIsLive;
    private static int mVisitorCount;

    private static int mFollowers;

    private static long mStarId;

    private static String mStarName;
    private static int mStarLevel;
    private static String mStarPic;
    private static long mSpendRankFirstId;

    private static boolean mIsShutUp;
    private static StarInfoResult mStarInfoResult;

    private static AudienceResult mAdminResult;
    private static AudienceResult mAudienceResult;
    private static Message.To mCurrentTarget = null;

    private static boolean mIsPrivate;
    private static List<Message.To> mChatTargetList = new ArrayList<Message.To>();

    private static List<Message.To> mGiftTargetList = new ArrayList<Message.To>();
    private static WebSocket mWebSocket;

    private static String mWebSocketConnectInfo;
    private static View mStarFeatherCountView;

    private static boolean mIsFromUc;

    private static boolean sShowBroadcastMarquee;

    private static boolean sShowGiftMarquee;
    /**
     * setLiveCommonData
     *
     * @param intent intent
     */
    public static void reset(Intent intent) {
        LiveIntentBuilder builder = new LiveIntentBuilder(intent);
        long roomId = builder.getRoomId();
        long starId = builder.getStarId();
        String starPic = builder.getStarPic();
        if (starPic == null) {
            starPic = "";
        }
        String roomCover = builder.getRoomCover();
        if (roomCover == null) {
            roomCover = "";
        }
        String starNick = builder.getStarNickName();
        int starLevel = builder.getStarLevel();
        boolean isLive = builder.getIsLive();
        int visitorCount = builder.getVisitorCount();
        boolean isFromUc = builder.getIsFromUC();
        mFollowers = builder.getFollowers();

        Uri uri = intent.getData();
        if (uri != null) {
            roomId = Long.parseLong(uri.getQueryParameter(StarRoomKey.ROOM_ID_KEY));
            starId = Long.parseLong(uri.getQueryParameter(StarRoomKey.STAR_ID_KEY));
            starLevel = Integer.parseInt(uri.getQueryParameter(StarRoomKey.STAR_LEVEL_KEY));
            isLive = "true".equals(uri.getQueryParameter(StarRoomKey.IS_LIVE_KEY));
            isFromUc = "true".equals(uri.getQueryParameter(StarRoomKey.IS_FROM_UC));
            starNick = uri.getQueryParameter(StarRoomKey.STAR_NICK_NAME_KEY);
            mFollowers = Integer.parseInt(uri.getQueryParameter(StarRoomKey.STAR_FOLLOWERS_KEY));
        }

		if (starNick != null) {
			AudienceUtils.addTargetToList(getChatTargetList(), starId, starNick, Enums.VipType.NONE, 2, 0);
			AudienceUtils.addTargetToList(getGiftTargetList(), starId, starNick, Enums.VipType.NONE, 2, 0);
		}

        setRoomId(roomId);
        setRoomCover(roomCover);
        setIsLive(isLive);
        setVisitorCount(visitorCount);
        setStarId(starId);
        setStarName(starNick);
        setStarLevel(starLevel);
        setStarPic(starPic);
        setIsFromUc(isFromUc);
        setFollowers(mFollowers);
        setShowBroadcastMarquee(StorageUtils.getSharedPreferences().getBoolean(SharedPreferenceKey.SHOW_BROADCAST_MARQUEE, true));
        setShowGiftMarquee(StorageUtils.getSharedPreferences().getBoolean(SharedPreferenceKey.SHOW_GIFT_MARQUEE, true));
    }
    /**
     * 是否显示广播跑道
     * @return true
     */
    public static boolean isShowBroadcastMarquee() {
        return sShowBroadcastMarquee;
    }

    /**
     * 是否显示广播跑道
     * @param showBroadcastMarquee showBroadcastMarquee
     */
    public static void setShowBroadcastMarquee(boolean showBroadcastMarquee) {
        LiveCommonData.sShowBroadcastMarquee = showBroadcastMarquee;
    }

    /**
     * 是否显示礼物跑道
     * @return true
     */
    public static boolean isShowGiftMarquee() {
        return sShowGiftMarquee;
    }

    /**
     * 是否显示礼物跑道
     * @param showGiftMarquee showGiftMarquee
     */
    public static void setShowGiftMarquee(boolean showGiftMarquee) {
        LiveCommonData.sShowGiftMarquee = showGiftMarquee;
    }

    /**
     * setRoomId
     *
     * @param roomId roomId
     */
    public static void setRoomId(long roomId) {
        mRoomId = roomId;
    }

    /**
     * getmFollowers
     *
     * @return mFollowers
     */
    public static int getFollowers() {
        return mFollowers;
    }

    /**
     * setmFollowers
     *
     * @param followers followers
     */
    public static void setFollowers(int followers) {
        LiveCommonData.mFollowers = followers;
    }

    /**
     * getRoomId
     *
     * @return mRoomId
     */
    public static long getRoomId() {
        return mRoomId;
    }

    /**
     * setRoomCover
     *
     * @param roomCover roomCover
     */
    public static void setRoomCover(String roomCover) {
        mRoomCover = roomCover;
    }

    /**
     * getRoomCover
     *
     * @return roomCover
     */
    public static String getRoomCover() {
        return mStarInfoResult != null ? mStarInfoResult.getData().getRoom().getPicUrl() : mRoomCover;
    }

    /**
     * setIsLive
     *
     * @param isLive isLive
     */
    public static void setIsLive(boolean isLive) {
        mIsLive = isLive;
    }

    /**
     * isLive
     *
     * @return isLive
     */
    public static boolean getIsLive() {
        return mIsLive;
    }

    /**
     * setVisitorCount
     *
     * @param visitorCount visitorCount
     */
    public static void setVisitorCount(int visitorCount) {
        mVisitorCount = visitorCount;
    }

    /**
     * getVisitorCount
     *
     * @return visitorCount
     */
    public static int getVisitorCount() {
        return mVisitorCount;
    }

    /**
     * setStarId
     *
     * @param starId starId
     */
    public static void setStarId(long starId) {
        mStarId = starId;
    }

    /**
     * getStarId
     *
     * @return starId
     */
    public static long getStarId() {
        return mStarId;
    }

    /**
     * setStarName
     *
     * @param starName starName
     */
    public static void setStarName(String starName) {
        mStarName = starName;
    }

    /**
     * getStarName
     *
     * @return starName
     */
    public static String getStarName() {
        return mStarName != null ? mStarName : "主播";
    }

    /**
     * setStarLevel
     *
     * @param level level
     */
    public static void setStarLevel(int level) {
        mStarLevel = level;
    }

    /**
     * getStarLevel
     *
     * @return level
     */
    public static int getStarLevel() {
        return mStarLevel;
    }

    /**
     * setStarPic
     *
     * @param starPic starPic
     */
    public static void setStarPic(String starPic) {
        mStarPic = starPic;
    }

    /**
     * isFromUc
     *
     * @return mIsFromUc
     */
    public static boolean isFromUc() {
        return mIsFromUc;
    }

    /**
     * setIsFromUc
     *
     * @param isFromUc isFromUc
     */
    public static void setIsFromUc(boolean isFromUc) {
        LiveCommonData.mIsFromUc = isFromUc;
    }

    /**
     * getStarPic
     *
     * @return starPic
     */
    public static String getStarPic() {
        return mStarInfoResult != null ? mStarInfoResult.getData().getUser().getPicUrl() : mStarPic;
    }

    /**
     * setSpendRankFirstId
     *
     * @param id id
     */
    public static void setSpendRankFirstId(long id) {
        mSpendRankFirstId = id;
    }

    /**
     * getSpendRankFirstId
     *
     * @return id
     */
    public static long getSpendRankFirstId() {
        return mSpendRankFirstId;
    }

    /**
     * setIsShutUp
     *
     * @param isShutUp isShutUp
     */
    public static void setIsShutUp(boolean isShutUp) {
        mIsShutUp = isShutUp;
    }

    /**
     * getIsShutUp
     *
     * @return isShutUp
     */
    public static boolean getIsShutUp() {
        return mIsShutUp;
    }

    /**
     * setStarInfoResult
     *
     * @param starInfoResult starInfoResult
     */
    public static void setStarInfoResult(StarInfoResult starInfoResult) {
		long starId = starInfoResult.getData().getUser().getId();
		String starNick = starInfoResult.getData().getUser().getNickName();
		setStarId(starId);
		setStarName(starNick);
		if (starNick != null) {
			AudienceUtils.addTargetToList(getChatTargetList(), starId, starNick, Enums.VipType.NONE, 2, 0);
			AudienceUtils.addTargetToList(getGiftTargetList(), starId, starNick, Enums.VipType.NONE, 2, 0);
		}

        Finance finance = starInfoResult.getData().getUser().getFinance();
        int newLevel = finance != null ? (int) LevelUtils.getStarLevelInfo(finance.getBeanCountTotal()).getLevel() : 0;
        if (mStarInfoResult != null && mStarInfoResult.getData().getUser().getId() == starInfoResult.getData().getUser().getId()) {
            if (newLevel > mStarLevel) {
                mStarLevel = newLevel;
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.STAR_UPGRADE);
            }
        }
        mStarInfoResult = starInfoResult;
        mStarLevel = newLevel;
    }

    /**
     * getStarInfoResult
     *
     * @return starInfoResult
     */
    public static StarInfoResult getStarInfoResult() {
        return mStarInfoResult;
    }

    /**
     * getSongPrice
     *
     * @return SongPrice
     */
    public static long getSongPrice() {
        return mStarInfoResult != null ? mStarInfoResult.getData().getRoom().getSongPrice() : 0;
    }

    /**
     * getLiveId
     *
     * @return LiveId
     */
    public static String getLiveId() {
        return mStarInfoResult != null ? mStarInfoResult.getData().getRoom().getLiveId() : "";
    }

    /**
     * getGiftWeek
     *
     * @return GiftWeek
     */
    public static long[] getGiftWeek() {
        return mStarInfoResult != null ? mStarInfoResult.getData().getUser().getStar().getGiftWeek() : null;
    }

    /**
     * setAdminResult
     *
     * @param adminResult adminResult
     */
    public static void setAdminResult(AudienceResult adminResult) {
        mAdminResult = adminResult;
    }

    /**
     * getAdminResult
     *
     * @return adminResult
     */
    public static AudienceResult getAdminResult() {
        return mAdminResult;
    }

    /**
     * setAudienceResult
     *
     * @param audienceResult audienceResult
     */
    public static void setAudienceResult(AudienceResult audienceResult) {
        mAudienceResult = audienceResult;
    }

    /**
     * getAudienceResult
     *
     * @return audienceResult
     */
    public static AudienceResult getAudienceResult() {
        return mAudienceResult;
    }

    /**
     * setCurrentTarget
     *
     * @param target target
     */
    public static void setCurrentTarget(Message.To target) {
        mCurrentTarget = target;
    }

    /**
     * getCurrentTarget
     *
     * @return mCurrentTarget
     */
    public static Message.To getCurrentTarget() {
        return mCurrentTarget;
    }

    /**
     * setIsPrivate
     *
     * @param isPrivate isPrivate
     */
    public static void setIsPrivate(boolean isPrivate) {
        mIsPrivate = isPrivate;
    }

    /**
     * isPrivate
     *
     * @return mIsPrivate
     */
    public static boolean isPrivate() {
        return mIsPrivate;
    }

    /**
     * getChatTargetList
     *
     * @return mChatTargetList
     */
    public static List<Message.To> getChatTargetList() {
        return mChatTargetList;
    }

    /**
     * getGiftTargetList
     *
     * @return mGiftTargetList
     */
    public static List<Message.To> getGiftTargetList() {
        return mGiftTargetList;
    }

    /**
     * setWebSocket
     *
     * @param webSocket webSocket
     */
    public static void setWebSocket(WebSocket webSocket) {
        mWebSocket = webSocket;
    }

    /**
     * getWebSocket
     *
     * @return mWebSocket
     */
    public static WebSocket getWebSocket() {
        return mWebSocket;
    }

    /**
     * setWebSocketConnectInfo
     *
     * @param connectInfo connectInfo
     */
    public static void setWebSocketConnectInfo(String connectInfo) {
        mWebSocketConnectInfo = connectInfo;
    }

    /**
     * getWebSocketConnectInfo
     *
     * @return mWebSocketConnectInfo
     */
    public static String getWebSocketConnectInfo() {
        return mWebSocketConnectInfo;
    }

    /**
     * setStarFeatherCountView
     *
     * @param view view
     */
    public static void setStarFeatherCountView(View view) {
        mStarFeatherCountView = view;
    }

    /**
     * getStarFeatherCountView
     *
     * @return mStarFeatherCountView
     */
    public static View getStarFeatherCountView() {
        return mStarFeatherCountView;
    }

    /**
     * clear
     */
    public static void clear() {
        mRoomId = 0;
        mRoomCover = "";
        mIsLive = false;
        mVisitorCount = 0;

        mStarId = 0;
        mStarName = "";
        mStarLevel = 0;
        mStarPic = "";

        mSpendRankFirstId = 0;
        mIsShutUp = false;
        mIsFromUc = false;

        mStarInfoResult = null;
        mAdminResult = null;
        mAudienceResult = null;

        mCurrentTarget = null;
        mIsPrivate = false;
        mChatTargetList.clear();
        mGiftTargetList.clear();

        if (mWebSocket != null) {
            mWebSocket.setWebSocketListener(null);
            mWebSocket.disconnect();
            mWebSocket = null;
        }
        mWebSocketConnectInfo = null;

        mStarFeatherCountView = null;
    }
}
