package show.we.lib.utils;

import android.content.Context;
import android.widget.Toast;
import show.we.sdk.util.JSONUtils;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.LiveAPI;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.model.*;
import show.we.lib.ui.LiveCommonData;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by CG on 13-12-18.
 *
 * @author ll
 * @version 3.3.0
 */
public class LiveUtils {

    private static final long VALID_USER_ID_MIN = 1024000;

    /**
     * 请求主播信息
     */
    public static void requestStarInfo() {
        LiveAPI.starInfo(LiveCommonData.getRoomId()).execute(new BaseRequestCallback<StarInfoResult>() {
            @Override
            public void onRequestSucceed(StarInfoResult starInfoResult) {
                LiveCommonData.setStarInfoResult(starInfoResult);
                Message.To to = AudienceUtils.getChatTargetById(LiveCommonData.getChatTargetList(), LiveCommonData.getStarId());
                if (to != null) {
                    to.setVipType(starInfoResult.getData().getUser().getVipType());
                }
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_LIVE_STAR_INFO_SUCCESS);
            }

            @Override
            public void onRequestFailed(StarInfoResult starInfoResult) {
            }
        });
    }

    /**
     * 从观众榜中找到改用户并设置图片url
     * @param selectedUserInfo selectedUserInfo
     */
    public static void setChatUserPicFromAudienceList(ChatUserInfo selectedUserInfo) {
        AudienceResult audienceResult = LiveCommonData.getAudienceResult();
        if (audienceResult != null) {
            List<AudienceResult.User> users = audienceResult.getData().getUsers();
            if (users != null) {
                for (AudienceResult.User user : users) {
                    if (user.getId() == selectedUserInfo.getId()) {
                        selectedUserInfo.setUserPic(user.getPicUrl());
                    }
                }
            }
        }
    }

    /**
     * 是否显示进场信息
     * @param context context
     * @param msgObject msgObject
     * @return true - 显示
     */
    public static boolean checkIfShowEnterRoomMessage(Context context, JSONObject msgObject) {
        JSONObject data = msgObject.optJSONObject("data_d");
        long id = data.optLong("_id");
        long spend = data.optLong("spend");
        boolean isSelf = false;
        if (LoginUtils.isAlreadyLogin()) {
            isSelf = id == UserInfoUtils.getUserInfo().getData().getId();
        }
        boolean idValid = id > VALID_USER_ID_MIN;
        boolean isVip = data.optInt("vip") == 1 || data.optInt("vip") == 2;
        boolean hasCar = data.optLong("car") > 0;

        /* 进场提示规则
          1020人以下  1021人~3400人  3401人~6800人  6801人以上
          所有        1富及以上      4富及以上      10富及以上*/
        final int visitorCount = LiveCommonData.getVisitorCount();
        final long level = LevelUtils.getUserLevelInfo(spend).getLevel();
        int[] visitorCountFilter = context.getResources().getIntArray(R.array.enter_room_visitor_count_filter);
        int[] levelFilter = context.getResources().getIntArray(R.array.enter_room_level_filter);
        boolean levelEnough = false;
        for (int i = visitorCountFilter.length - 1; i > 0; i--) {
            if (visitorCount > visitorCountFilter[i]) {
                levelEnough = level >= levelFilter[i];
                break;
            }
        }
        return idValid && (isSelf || isVip || hasCar || levelEnough);
    }

    /**
     * addRecentlyViewStar
     */
    public static void addRecentlyViewStar() {
        RecentlyViewStarListResult result;
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.RECENTLY_VIEW_STAR_LIST_KEY)) {
            result = (RecentlyViewStarListResult) CacheUtils.getObjectCache().get(CacheObjectKey.RECENTLY_VIEW_STAR_LIST_KEY);
        } else {
            result = new RecentlyViewStarListResult();
        }

        List<RecentlyViewStarListResult.User> users = result.getUsers();
        for (RecentlyViewStarListResult.User user : users) {
            if (user.getStarId() == LiveCommonData.getStarId()) {
                users.remove(user);
                break;
            }
        }

        final int starSize = 10;
        if (users.size() == starSize) {
            users.remove(starSize - 1);
        }

        RecentlyViewStarListResult.User user = new RecentlyViewStarListResult.User();
        user.setStarId(LiveCommonData.getStarId());
        user.setRoomId(LiveCommonData.getRoomId());
        user.setNickName(LiveCommonData.getStarName());
        user.setIsLive(LiveCommonData.getIsLive());
        user.setLevel(LiveCommonData.getStarLevel());
        user.setVisitorCount(LiveCommonData.getVisitorCount());
        user.setPicUrl(LiveCommonData.getStarPic());
        user.setCoverUrl(LiveCommonData.getRoomCover());
        user.setFollowers(LiveCommonData.getFollowers());

        users.add(0, user);
        result.setUsers(users);
        CacheUtils.getObjectCache().add(CacheObjectKey.RECENTLY_VIEW_STAR_LIST_KEY, result);
    }

    /**
     * 请求禁言信息
     *
     * @param roomId   房间号
     * @param isPrompt 是否提示
     */
    public static void requestShutUpTTL(final long roomId, final boolean isPrompt) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            LiveAPI.shutUpTTL(UserInfoUtils.getAccessToken(), roomId).execute(new BaseRequestCallback<TTLResult>() {
                @Override
                public void onRequestSucceed(TTLResult ttlResult) {
                    if (roomId == LiveCommonData.getRoomId()) {
                        long time = ttlResult.getData().getTTL();
                        if (time > 0) {
                            LiveCommonData.setIsShutUp(true);
                            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SHUT_UP, time);
                        } else {
                            if (LiveCommonData.getIsShutUp() && isPrompt) {
                                PromptUtils.showToast(R.string.recover_prompt, Toast.LENGTH_LONG);
                                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.RECOVER_SHUT_UP);
                            }
                            LiveCommonData.setIsShutUp(false);
                        }
                    }
                }

                @Override
                public void onRequestFailed(TTLResult ttlResult) {
                }
            });
        }
    }

    /**
     * requestKickTTL
     *
     * @param roomId roomId
     */
    public static void requestKickTTL(final long roomId) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            LiveAPI.kickTTL(UserInfoUtils.getAccessToken(), roomId).execute(new BaseRequestCallback<TTLResult>() {

                @Override
                public void onRequestSucceed(TTLResult ttlResult) {
                    if (roomId == LiveCommonData.getRoomId() && ttlResult.getData().getTTL() > 0) {
                        PromptUtils.showToast(R.string.in_kick_up_mode, Toast.LENGTH_SHORT);
                        WebSocketUtils.disconnectWebSocket();
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.NOTIFY_LIVE_ACTIVITY_FINISH);
                    }
                }

                @Override
                public void onRequestFailed(TTLResult ttlResult) {
                }
            });
        }
    }

    /**
     * sendStarWelcomePrivateMessage
     *
     * @param context context
     */
    public static void sendStarWelcomePrivateMessage(Context context) {
        StarInfoResult starInfoResult = LiveCommonData.getStarInfoResult();
        if (starInfoResult.getData().getUser().getId() == LiveCommonData.getStarId()
                && starInfoResult.getData().getRoom().getGreetings() != null) {
            Message.ReceiveModel message = new Message.ReceiveModel();
            long level = LevelUtils.getUserLevelInfo(starInfoResult.getData().getUser().getFinance().getCoinSpendTotal()).getLevel();
            message.setLevel(level);
            Message.From from = new Message.From();
            from.setNickName(starInfoResult.getData().getUser().getNickName());
            from.setId(starInfoResult.getData().getUser().getId());
            from.setVipType(starInfoResult.getData().getUser().getVipType());
            message.setFrom(from);

            for (Message.To target : LiveCommonData.getChatTargetList()) {
                if (target.getId() == starInfoResult.getData().getUser().getId()) {
                    target.setLevel(level);
                    break;
                }
            }

            for (Message.To target : LiveCommonData.getGiftTargetList()) {
                if (target.getId() == starInfoResult.getData().getUser().getId()) {
                    target.setLevel(level);
                    break;
                }
            }

            Message.To to = new Message.To();
            to.setNickName(context.getString(show.we.lib.R.string.you));
            if (LoginUtils.isAlreadyLogin()) {
                to.setId(UserInfoUtils.getUserInfo().getData().getId());
            } else {
                to.setId(0L);
            }
            to.setPrivate(true);

            message.setTo(to);
            String greeting = starInfoResult.getData().getRoom().getGreetings();
            int redirectUrlIndex = greeting.indexOf("redirectUrl");
            if (redirectUrlIndex >= 0) {
                greeting = greeting.substring(0, redirectUrlIndex);
            }
            message.setContent(greeting);
            message.setRoomId(starInfoResult.getData().getUser().getStar().getRoomId());
            try {
                MessageParseUtils.doParse(context, JSONUtils.toJsonString(message), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
