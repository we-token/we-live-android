package show.we.lib.utils;

import android.content.Context;
import android.widget.Toast;
import show.we.sdk.util.FileUtils;
import show.we.sdk.util.JSONUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.config.Config;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.config.UMengConfig;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.download.DownloadManager;
import show.we.lib.model.*;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.widget.animation.GiftShapeEffect;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by CG on 13-12-18.
 *
 * @author ll
 * @version 3.3.0
 */
public class MessageParseUtils {
	
	public static void doParse(Context context, String message) {
		doParse(context, message, true);
	}
    /**
     * 解析数据
     *
     * @param context context
     * @param message message
     */
    public static void doParse(Context context, String message, boolean isPrivate) {
        try {
            // 在这一步之前不可以进行编码转换，否则下面产生JSONException
            JSONObject msgObject = new JSONObject(message);

            String action = msgObject.optString("action");
            if (!StringUtils.isEmpty(action)) {
                if (action.equals("treasure.room")) {
                    Message.TreasureRoomModel treasureRoomModel = JSONUtils.fromJsonString(message, Message.TreasureRoomModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_TREASURE_ROOM, treasureRoomModel);
                } else if (action.equals("treasure.notify")) {
                    Message.TreasureNotifyModel treasureNotifyModel = JSONUtils.fromJsonString(message, Message.TreasureNotifyModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, treasureNotifyModel);
                } else if (action.equals("fortune.notify")) {
                    Message.FortuneModel fortuneModel = JSONUtils.fromJsonString(message, Message.FortuneModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, fortuneModel);
                } else if (action.equals("gift.notify")) {
                    Message.SendGiftModel sendGiftModel = JSONUtils.fromJsonString(message, Message.SendGiftModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_GIFT_NOTIFY, sendGiftModel);
                    checkIsShowGiftAnimation(context, sendGiftModel);
                } else if (action.equals("gift.feather")) {
                    Message.SendFeatherModel sendFeatherModel = JSONUtils.fromJsonString(message, Message.SendFeatherModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_FEATHER_NOTIFY, sendFeatherModel);
                } else if (action.equals("room.live")) {
                    if (msgObject.optJSONObject("data_d").optBoolean("live")) {
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_OPEN_ROOM_LIVE);
                    } else {
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_CLOSE_ROOM_LIVE);
                    }
                } else if (action.equals("gift.marquee")) {
                    marquee(msgObject);
                } else if (action.equals("live.puzzle_begin")) {
                    msgObject = msgObject.optJSONObject("data_d");
                    String picUrl = msgObject.optString("pic");
                    int row = msgObject.optInt("x");
                    int column = msgObject.optInt("y");
                    if (!StringUtils.isEmpty(picUrl) && row > 0 && column > 0) {
                        Object[] objects = {Utils.html2String(msgObject.optString("msg")), picUrl, row, column};
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_PUZZLE_BEGIN, objects);
                    }
                } else if (action.equals("live.puzzle_win")) {
                    if (LoginUtils.isAlreadyLogin()) {
                        Message.PuzzleWinModel puzzleWinModel = JSONUtils.fromJsonString(message, Message.PuzzleWinModel.class);
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_PUZZLE_WIN, puzzleWinModel);
                    }
                } else if (action.equals("message.broadcast")) {
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_LIVE_BROADCAST_NOTIFY
                            , JSONUtils.fromJsonString(message, Message.BroadCastModel.class));
                } else if (action.equals("manage.kick")) {
                    if (LoginUtils.isAlreadyLogin()) {
                        if (msgObject.optJSONObject("data_d").optLong("xy_user_id") == UserInfoUtils.getUserInfo().getData().getId()) {
                            PromptUtils.showToast(R.string.in_kick_up_mode, Toast.LENGTH_SHORT);
                            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.NOTIFY_LIVE_ACTIVITY_FINISH);
                        }
                    }
                } else if (action.equals("sofa.list")) {
                    SofaListResult.Data sofaData = JSONUtils.fromJsonString(msgObject.optJSONObject("data_d").toString(), SofaListResult.Data.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_LIVE_SOFA_NOTIFY, sofaData);
                } else if (action.equals("manage.recover")) {
                    LiveCommonData.setIsShutUp(false);
                } else if (action.equals("manage.shutup_ttl")) {
                    PromptUtils.showToast(R.string.in_shut_up_mode, Toast.LENGTH_SHORT);
                } else if (action.equals("fortunegod.room")) {
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_GOD_OF_WEALTH_MESSAGE, message);
                } else if (action.equals("egg.room")) {
                    SlotWinResult.Data eggData = JSONUtils.fromJsonString(msgObject.optJSONObject("data_d").toString(), SlotWinResult.Data.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SLOT_WIN_ROOM, eggData);
                } else if (action.equals("egg.all")) {
                    SlotWinResult.Data eggData = JSONUtils.fromJsonString(msgObject.optJSONObject("data_d").toString(), SlotWinResult.Data.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_MARQUEE_NOTIFY, eggData);
                } else if (action.equals("treasure.notice")) {
                     Message.TreasureNoticeModel treasureNoticeModel = JSONUtils.fromJsonString(message, Message.TreasureNoticeModel.class);
                     DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_MARQUEE_NOTIFY, treasureNoticeModel);
                 }  else if (action.equals("treasure.award")) {
                     Message.TreasureAwardModel treasureAwardModel = JSONUtils.fromJsonString(message, Message.TreasureAwardModel.class);
                     DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_MARQUEE_NOTIFY, treasureAwardModel);
                } else if (action.equals("treasure.marquee")) {
                    Message.TreasureMarqueeModel treasureMarqueeModel = JSONUtils.fromJsonString(message, Message.TreasureMarqueeModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_MARQUEE_NOTIFY, treasureMarqueeModel);
                } else if (action.equals("room.change")) {
                    if (StorageUtils.getSharedPreferences().getBoolean(SharedPreferenceKey.SHOW_ENTER_MESSAGE, true)
                            && LiveUtils.checkIfShowEnterRoomMessage(context, msgObject)) {
                        Message.EnterRoomModel enterRoomModel = JSONUtils.fromJsonString(message, Message.EnterRoomModel.class);
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, enterRoomModel);
                    }
                } else if (action.equals("manage.shutup")) {
                    if (LoginUtils.isAlreadyLogin() && msgObject.optJSONObject("data_d").optLong("xy_user_id")
                            == UserInfoUtils.getUserInfo().getData().getId()) {
                        LiveCommonData.setIsShutUp(true);
                        LiveUtils.requestShutUpTTL(LiveCommonData.getRoomId(), true);
                    }
                    Message.ShutUpModel shutUpModel = JSONUtils.fromJsonString(message, Message.ShutUpModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, shutUpModel);
                } else if (action.equals("song.agree_notify")) {
                    Message.SongAgreeModel songAgreeModel = JSONUtils.fromJsonString(message, Message.SongAgreeModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, songAgreeModel);
                } else if (action.equals("song.refuse_notify")) {
                    Message.SongRefuseModel songRefuseModel = JSONUtils.fromJsonString(message, Message.SongRefuseModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, songRefuseModel);
                    RequestUtils.requestUpdateUserInfo(context, false, false, false, false, false, false);
                } else if (action.equals("sys.notice")) {
                    Message.SystemNoticeModel systemNoticeModel = JSONUtils.fromJsonString(message, Message.SystemNoticeModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, systemNoticeModel);
                } else if (action.equals("live.puzzle_win")) {
                    Message.PuzzleWinModel puzzleWinModel = JSONUtils.fromJsonString(message, Message.PuzzleWinModel.class);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CHAT_WINDOW_INTEGRATED_MESSAGE, puzzleWinModel);
                }
            } else {
                Message.ReceiveModel msg = JSONUtils.fromJsonString(message, Message.ReceiveModel.class);
                Message.From from = msg.getFrom();
                if (from != null) {
                    Message.To to = msg.getTo();
                    if (to == null || !to.getPrivate()) {
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_RECEIVE_PUBLIC_MESSAGE, msg);
                    }
                    UserInfoResult userInfo = UserInfoUtils.getUserInfo();
                    boolean starGreetings = from.getId() == LiveCommonData.getStarId() && to != null && to.getId() == 0;
                    boolean fromYou = to != null && userInfo != null && from.getId() == userInfo.getData().getId();
                    boolean toYou = to != null && userInfo != null && to.getId() == userInfo.getData().getId();
                    if ((starGreetings || fromYou || toYou) && isPrivate) {
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_RECEIVE_NEW_PRIVATE_MESSAGE, msg);
                        if (to == null && !to.getPrivate()) {
                        	DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_RECEIVE_PUBLIC_MESSAGE, msg);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkIsShowGiftAnimation(Context context, Message.SendGiftModel msgObject) {
        GiftListResult.Gift gift = GiftUtils.getGift(msgObject.getData().getGift().getId());
        long giftCount = msgObject.getData().getGift().getCount();
        if (giftCount == GiftShapeEffect.SHAPE_SMILE_FACE || giftCount == GiftShapeEffect.SHAPE_HEART
                || giftCount == GiftShapeEffect.SHAPE_DOUBLE_HEART || giftCount == GiftShapeEffect.SHAPE_LOVE
                || giftCount == GiftShapeEffect.SHAPE_1314 || giftCount == GiftShapeEffect.SHAPE_3344
                || giftCount == GiftShapeEffect.SHAPE_9999) { //运行形状动画的时候不显示gif动画
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.GIFT_SHAPE_ANIMATION_NOTIFY, msgObject);
        } else {
            final int showEffectMinPrice = 200;
            if (gift.getCoinPrice() * giftCount >= showEffectMinPrice) {
                MobclickAgent.onEvent(context, UMengConfig.KEY_GIFT_ANIM_COUNT);
                String savePath = Config.getGifCacheFolderPath() + File.separator + gift.getPicPreUrl().replaceAll("([{/\\\\:*?\"<>|}]+)", "_");
                if (FileUtils.fileExists(savePath)) {
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_GIFT_ANIMATION_NOTIFY, savePath);
                } else {
                    DownloadManager.instance().download(gift.getPicPreUrl(), savePath, msgObject);
                }
            }
        }

        int[] winCoins = msgObject.getData().getWinCoin();
        if (winCoins != null && winCoins.length > 0 && LoginUtils.isAlreadyLogin()
                && msgObject.getData().getFrom().getId() == UserInfoUtils.getUserInfo().getData().getId()) {
            RequestUtils.requestUpdateUserInfo(context, false, false, false, false, true, false);
        }
    }

    private static void marquee(JSONObject msgObject) {
        JSONObject dataObject = msgObject.optJSONObject("data_d");
        Message.SendGiftModel sendGiftModel = new Message.SendGiftModel();
        sendGiftModel.setData(new Message.SendGiftModel.Data());

        Message.From from = new Message.From();
        String fromNickname = dataObject.optJSONObject("from").optString("nick_name");
        from.setNickName(Utils.html2String(fromNickname));
        from.setId(dataObject.optJSONObject("from").optLong("_id"));
        from.setFinance(new Finance());
        long coinTotal = dataObject.optJSONObject("from").optJSONObject("finance").optLong("coin_spend_total");
        from.getFinance().setCoinSpendTotal(coinTotal, false);
        long beanTotal = dataObject.optJSONObject("from").optJSONObject("finance").optLong("bean_count_total");
        from.getFinance().setBeanCountTotal(beanTotal);
        sendGiftModel.getData().setFrom(from);

        Message.To to = new Message.To();
        String toNickname = dataObject.optJSONObject("to").optString("nick_name");
        to.setNickName(Utils.html2String(toNickname));
        to.setId(dataObject.optJSONObject("to").optLong("_id"));
        to.setFinance(new Finance());
        coinTotal = dataObject.optJSONObject("to").optJSONObject("finance").optLong("coin_spend_total");
        to.getFinance().setCoinSpendTotal(coinTotal, false);
        beanTotal = dataObject.optJSONObject("to").optJSONObject("finance").optLong("bean_count_total");
        to.getFinance().setBeanCountTotal(beanTotal);
        sendGiftModel.getData().setTo(to);

        Message.Gift gift = new Message.Gift();
        gift.setId(dataObject.optJSONObject("gift").optLong("_id"));
        gift.setName(dataObject.optJSONObject("gift").optString("name"));
        gift.setCount(dataObject.optJSONObject("gift").optLong("count"));
        sendGiftModel.getData().setGift(gift);

        sendGiftModel.getData().setRoomId(dataObject.optLong("room_id"));
        sendGiftModel.getData().setTime(dataObject.optLong("t"));

        if (GiftUtils.getGift(sendGiftModel.getData().getGift().getId()) != null) {
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MESSAGE_PARSE_MARQUEE_NOTIFY, sendGiftModel);
        }
    }
}
