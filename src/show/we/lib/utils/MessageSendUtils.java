package show.we.lib.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import show.we.sdk.request.BaseResult;
import show.we.sdk.util.JSONUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.UserSystemAPI;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.UMengConfig;
import show.we.lib.model.Message;
import show.we.lib.model.UserInfoResult;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.widget.dialog.StandardDialog;
import com.umeng.analytics.MobclickAgent;
/**
 * Created by CG on 13-12-10.
 *
 * @author ll
 * @version 3.3.0
 */
public class MessageSendUtils {

    private static long mPreSendMessageTime;

    /**
     * checkMessageValidity
     *
     * @param context context
     * @param message message
     * @return true 有效
     */
    public static boolean checkMessageValidity(Context context, String message) {
        boolean isPrivate = LiveCommonData.isPrivate();
        Message.To to = LiveCommonData.getCurrentTarget();
        Boolean isShutUp = LiveCommonData.getIsShutUp();
        if (StringUtils.isEmpty(message)) {
            PromptUtils.showToast(R.string.message_cant_be_null, Toast.LENGTH_SHORT);
            return false;
        }

        if (to == null && isPrivate) {
            PromptUtils.showToast(context.getString(R.string.all_cant_do_private_chat), Toast.LENGTH_SHORT);
            return false;
        }

        if (isShutUp) {
            PromptUtils.showToast(context.getString(R.string.in_shut_up_mode), Toast.LENGTH_SHORT);
            return false;
        }

        UserInfoResult userInfoResult = UserInfoUtils.getUserInfo();
        long spendCoins = userInfoResult != null ? userInfoResult.getData().getFinance().getCoinSpendTotal() : 0;
        LevelUtils.UserLevelInfo userLevelInfo = LevelUtils.getUserLevelInfo(spendCoins, userInfoResult.getData().getVipType());

        int maxMessageLength = userLevelInfo.getMaxMessageLength();
        if (message.length() > maxMessageLength) {
            showMaxMessageLengthToast(context, maxMessageLength);
            return false;
        }

        if (!AudienceUtils.checkPrivateTalkPermission(isPrivate, context)) {
            if (isPrivate) {
                return false;
            }
            long messageInterval = System.currentTimeMillis() - mPreSendMessageTime; //ms
            if (messageInterval < userLevelInfo.getMinSendMessageInterval()) {
                switch (userLevelInfo.getMinSendMessageInterval()) {
                    case LevelUtils.MIN_SEND_MESSAGE_INTERVAL_ALFA:
                        showLimitConfirmDialog(context, R.string.talk_too_fast_alfa);
                        break;
                    case LevelUtils.MIN_SEND_MESSAGE_INTERVAL_BETA:
                        PromptUtils.showToast(R.string.talk_too_fast_beta, Toast.LENGTH_SHORT);
                        break;
                    case LevelUtils.MIN_SEND_MESSAGE_INTERVAL_GAMMA:
                        PromptUtils.showToast(R.string.talk_too_fast_gamma, Toast.LENGTH_SHORT);
                        break;
                    case LevelUtils.MIN_SEND_MESSAGE_INTERVAL_DELTA:
                        PromptUtils.showToast(R.string.talk_too_fast_delta, Toast.LENGTH_SHORT);
                        break;
                    default:
                        break;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 显示最大消息长度Toast
     *
     * @param context          context
     * @param maxMessageLength maxMessageLength
     */
    public static void showMaxMessageLengthToast(Context context, int maxMessageLength) {
        switch (maxMessageLength) {
            case LevelUtils.MAX_MESSAGE_LENGTH_NEW_PLAYER:
                showLimitConfirmDialog(context, R.string.word_too_long_alfa);
                break;
            case LevelUtils.MAX_MESSAGE_LENGTH_COMMON:
                PromptUtils.showToast(R.string.word_too_long_beta, Toast.LENGTH_SHORT);
                break;
            case LevelUtils.MAX_MESSAGE_LENGTH_NORMAL_VIP:
                PromptUtils.showToast(R.string.word_too_long_gamma, Toast.LENGTH_SHORT);
                break;
            case LevelUtils.MAX_MESSAGE_LENGTH_SUPER_VIP:
                PromptUtils.showToast(R.string.word_too_long_delta, Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }
    }

    private static void showLimitConfirmDialog(final Context context, int resId) {
        StandardDialog exitDialog = new StandardDialog(context);
        exitDialog.setContentText(resId);
        exitDialog.setPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.entryRechargeActivity(context);
            }
        });
        exitDialog.setPositiveButtonText(context.getString(R.string.recharge));
        exitDialog.show();
    }

    /**
     * sendTextMessage
     *
     * @param context context
     * @param message message
     */
    public static void sendTextMessage(Context context, String message) {
        Message.To to = LiveCommonData.getCurrentTarget();
        boolean isPrivate = LiveCommonData.isPrivate();
        long roomId = LiveCommonData.getRoomId();
        StringBuilder strBuilder = new StringBuilder(message);
        String[] sensitiveWords = (String[]) CacheUtils.getObjectCache().get(CacheObjectKey.SENSITIVE_WORD);
        if (sensitiveWords != null) {
            for (String word : sensitiveWords) {
                int pos = strBuilder.indexOf(word);
                if (pos != -1) {
                    strBuilder.replace(pos, pos + word.length(), "***");
                }
            }
        }

        Message.SendModel sendModel = createMessage(to, strBuilder.toString(), null, isPrivate);
        if (sendModel != null) {
            sendMessage(context, sendModel, isPrivate);
            mPreSendMessageTime = System.currentTimeMillis();
            if (isPrivate && to != null) {
                UserSystemAPI.addWhisper(UserInfoUtils.getAccessToken(), to.getId(), roomId, message)
                        .execute(new BaseRequestCallback<BaseResult>() {
                            @Override
                            public void onRequestSucceed(BaseResult baseResult) {
                            }

                            @Override
                            public void onRequestFailed(BaseResult baseResult) {
                            }
                        });
            }
            StatisticUtils.addSingleEvent(StatisticUtils.MODULE_LIVE
                    , StatisticUtils.TYPE_CLICK, StatisticUtils.ORIGIN_CHAT_CONTENTS, 1);
        }
    }

    /**
     * 创建文字消息或者语音消息（只能给主播）
     *
     * @param to        消息发送对象
     * @param message   消息
     * @param voice     语音
     * @param isPrivate 是否悄悄话
     * @return 消息对象
     */
    public static Message.SendModel createMessage(Message.To to, String message, Message.SendModel.Msg.Voice voice, boolean isPrivate) {
        Message.SendModel pushMessage = new Message.SendModel();
        pushMessage.setMsg(new Message.SendModel.Msg());
        pushMessage.getMsg().setContent(message);
        long coinCount = UserInfoUtils.getUserInfo().getData().getFinance().getCoinSpendTotal();
        long level = LevelUtils.getUserLevelInfo(coinCount).getLevel();
        pushMessage.getMsg().setLevel(level);
        if (to != null && to.getNickName() != null) {
            to.setPrivate(isPrivate);
            pushMessage.getMsg().setTo(to);
            if (isPrivate) {
                pushMessage.setUserId(to.getId());
            }
        }
        pushMessage.getMsg().setVoice(voice);
        return pushMessage;
    }

    /**
     * 发送消息
     *
     * @param context     context
     * @param pushMessage 消息对象
     * @param isPrivate   是否私聊
     */
    public static void sendMessage(Context context, Message.SendModel pushMessage, boolean isPrivate) {
        try {
            Message.To to = pushMessage.getMsg().getTo();
            String msg = JSONUtils.toJsonString(pushMessage);
            LiveCommonData.getWebSocket().sendMessage(msg);
            MobclickAgent.onEvent(context, UMengConfig.KEY_LIVEROOM_SEND_MESSAGE_CLICK, UMengConfig.SEND_COUNT_STR);

            if (isPrivate) {
                UserInfoResult userInfo = UserInfoUtils.getUserInfo();
                Message.ReceiveModel privateMessage = new Message.ReceiveModel();
                Message.From privateFrom = new Message.From();
                privateFrom.setNickName(userInfo.getData().getNickName());
                privateFrom.setId(userInfo.getData().getId());
                privateMessage.setFrom(privateFrom);

                Message.To privateTo = new Message.To();
                privateTo.setNickName(to.getNickName());
                privateTo.setId(to.getId());
                privateTo.setPrivate(true);
                privateTo.setLevel(to.getLevel());

                privateMessage.setTo(privateTo);
                privateMessage.setContent(pushMessage.getMsg().getContent());
                privateMessage.setRoomId(LiveCommonData.getRoomId());
                privateMessage.setLevel(pushMessage.getMsg().getLevel());
                MessageParseUtils.doParse(context, JSONUtils.toJsonString(privateMessage), isPrivate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * sendVoiceMessage
     *
     * @param context  context
     * @param filePath filePath
     * @param duration duration
     */
    public static void sendVoiceMessage(Context context, String filePath, long duration) {
        Message.To to = AudienceUtils.getChatTargetById(LiveCommonData.getChatTargetList(), LiveCommonData.getStarId());
        if (to == null) {
            return;
        }
        Message.SendModel.Msg.Voice voice = new Message.SendModel.Msg.Voice(filePath, duration);
        Message.SendModel pushMessage = MessageSendUtils.createMessage(to, context.getString(R.string.recorder_message_content)
                , voice, false);
        sendMessage(context, pushMessage, false);

        StatisticUtils.addSingleEvent(StatisticUtils.MODULE_LIVE_MENU, StatisticUtils.TYPE_CLICK,
                StatisticUtils.ORIGIN_LIVEMENU_VOICE_BUTTON, StatisticUtils.VALUE_DEFAULT);
    }
}
