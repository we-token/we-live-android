package show.we.lib.utils;

import android.content.Intent;

import show.we.lib.config.StarRoomKey;

/**
 * 直播间intent数据太多，经常搞错，所以写这个类包装一下，防止出错
 * @author ll
 * @version 1.0.0
 */
public class LiveIntentBuilder {

    private Intent mIntent;

    /**
     * @param intent intent
     */
    public LiveIntentBuilder(Intent intent) {
        mIntent = intent;
    }

    /**
     *
     * @return Intent
     */
    public Intent getIntent() {
        return mIntent;
    }

    /**
     * 设置主播空间intent数据
     * @param isLive isLive
     * @param roomId roomId
     * @param starId starId
     * @param nickName nickName
     * @param level level
     * @param picUrl picUrl
     * @param roomCover roomCover
     * @param visitorCount visitorCount
     * @param constellation constellation
     * @param sex sex
     * @param location location
     * @param isFromLiveRoom isFromLiveRoom
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarZoneExtras(boolean isLive, long roomId, long starId, String nickName, int level
            , String picUrl, String roomCover, int visitorCount, int constellation, int sex, String location, boolean isFromLiveRoom) {
          return putIsLive(isLive)
                .putRoomId(roomId)
                .putStarId(starId)
                .putStarNickName(nickName)
                .putStarLevel(level)
                .putStarPic(picUrl)
                .putRoomCover(roomCover)
                .putVisitorCount(visitorCount)
                .putStarConstellation(constellation)
                .putStarSex(sex)
                .putStarLocation(location)
                .putIsFromLiveRoom(isFromLiveRoom);
    }

    /**
     * 设置直播间intent数据
     * @param isLive isLive
     * @param roomId roomId
     * @param starId starId
     * @param nickName nickName
     * @param level level
     * @param picUrl picUrl
     * @param roomCover roomCover
     * @param visitorCount visitorCount
     * @param followers followers
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putLiveExtras(boolean isLive, long roomId, long starId, String nickName, int level
            , String picUrl, String roomCover, int visitorCount, int followers) {
        return  putIsLive(isLive)
                .putRoomId(roomId)
                .putStarId(starId)
                .putStarNickName(nickName)
                .putStarLevel(level)
                .putStarPic(picUrl)
                .putRoomCover(roomCover)
                .putVisitorCount(visitorCount)
                .putFollowers(followers);
    }

    /**
     * @param followers followers
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putFollowers(int followers) {
        mIntent.putExtra(StarRoomKey.STAR_FOLLOWERS_KEY, followers);
        return this;
    }

    /**
     * @param isLive isLive
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putIsLive(boolean isLive) {
        mIntent.putExtra(StarRoomKey.IS_LIVE_KEY, isLive);
        return this;
    }

    /**
     * @param roomId roomId
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putRoomId(long roomId) {
        mIntent.putExtra(StarRoomKey.ROOM_ID_KEY, roomId);
        return this;
    }

    /**
     * @param starId starId
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarId(long starId) {
        mIntent.putExtra(StarRoomKey.STAR_ID_KEY, starId);
        return this;
    }

    /**
     * @param starNickName starNickName
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarNickName(String starNickName) {
        mIntent.putExtra(StarRoomKey.STAR_NICK_NAME_KEY, starNickName);
        return this;
    }

    /**
     * @param starLevel starLevel
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarLevel(int starLevel) {
        mIntent.putExtra(StarRoomKey.STAR_LEVEL_KEY, starLevel);
        return this;
    }

    /**
     * @param starPic starPic
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarPic(String starPic) {
        mIntent.putExtra(StarRoomKey.STAR_PIC_URL_KEY, starPic);
        return this;
    }

    /**
     * @param roomCover roomCover
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putRoomCover(String roomCover) {
        mIntent.putExtra(StarRoomKey.ROOM_COVER, roomCover);
        return this;
    }

    /**
     * @param visitorCount visitorCount
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putVisitorCount(int visitorCount) {
        mIntent.putExtra(StarRoomKey.VISITOR_COUNT_KEY, visitorCount);
        return this;
    }

    /**
     * @param starConstellation starConstellation
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarConstellation(int starConstellation) {
        mIntent.putExtra(StarRoomKey.STAR_CONSTELLATION_KEY, starConstellation);
        return this;
    }

    /**
     * @param starSex starSex
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarSex(int starSex) {
        mIntent.putExtra(StarRoomKey.STAR_SEX_KEY, starSex);
        return this;
    }

    /**
     * @param location location
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putStarLocation(String location) {
        mIntent.putExtra(StarRoomKey.STAR_LOCATION_KEY, location);
        return this;
    }

    /**
     * @param isFromLiveRoom isFromLiveRoom
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putIsFromLiveRoom(boolean isFromLiveRoom) {
        mIntent.putExtra(StarRoomKey.IS_FROM_LIVE_ROOM, isFromLiveRoom);
        return this;
    }

    /**
     * @param isFromUC isFromUC
     * @return LiveIntentBuilder
     */
    public LiveIntentBuilder putIsFromUC(boolean isFromUC) {
        mIntent.putExtra(StarRoomKey.IS_FROM_UC, isFromUC);
        return this;
    }

    /**
     * @return IsLive
     */
    public boolean getIsLive() {
        return mIntent.getBooleanExtra(StarRoomKey.IS_LIVE_KEY, false);
    }

    /**
     * @return RoomId
     */
    public long getRoomId() {
        return mIntent.getLongExtra(StarRoomKey.ROOM_ID_KEY, 0);
    }

    /**
     * @return StarId
     */
    public long getStarId() {
        return mIntent.getLongExtra(StarRoomKey.STAR_ID_KEY, 0);
    }

    /**
     * @return StarNickName
     */
    public String getStarNickName() {
        return mIntent.getStringExtra(StarRoomKey.STAR_NICK_NAME_KEY);
    }

    /**
     * @return StarLevel
     */
    public int getStarLevel() {
        return mIntent.getIntExtra(StarRoomKey.STAR_LEVEL_KEY, 0);
    }

    /**
     * @return StarPic
     */
    public String getStarPic() {
        return mIntent.getStringExtra(StarRoomKey.STAR_PIC_URL_KEY);
    }

    /**
     * @return RoomCover
     */
    public String getRoomCover() {
        return mIntent.getStringExtra(StarRoomKey.ROOM_COVER);
    }

    /**
     * @return VisitorCount
     */
    public int getVisitorCount() {
        return mIntent.getIntExtra(StarRoomKey.VISITOR_COUNT_KEY, 0);
    }

    /**
     * @return StarConstellation
     */
    public int getStarConstellation() {
        return mIntent.getIntExtra(StarRoomKey.STAR_CONSTELLATION_KEY, 0);
    }

    /**
     * @return StarSex
     */
    public int getStarSex() {
        return mIntent.getIntExtra(StarRoomKey.STAR_SEX_KEY, 0);
    }

    /**
     * @return StarLocation
     */
    public String getStarLocation() {
        return mIntent.getStringExtra(StarRoomKey.STAR_LOCATION_KEY);
    }

    /**
     * @return IsFromLiveRoom
     */
    public boolean getIsFromLiveRoom() {
        return mIntent.getBooleanExtra(StarRoomKey.IS_FROM_LIVE_ROOM, false);
    }

    /**
     * @return IsFromLiveRoom
     */
    public boolean getIsFromUC() {
        return mIntent.getBooleanExtra(StarRoomKey.IS_FROM_UC, false);
    }

    /**
     * @return Followers
     */
    public int getFollowers() {
        return mIntent.getIntExtra(StarRoomKey.STAR_FOLLOWERS_KEY, 0);
    }
}
