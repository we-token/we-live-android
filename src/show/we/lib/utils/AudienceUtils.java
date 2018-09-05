package show.we.lib.utils;

import android.content.Context;
import android.widget.Toast;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.model.AudienceResult;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.model.Message;
import show.we.lib.model.UserInfoResult;

import java.util.List;

/**
 * 处理的业务过多，所以抽离
 * Created by CG on 13-10-24.
 *
 * @author ll
 * @version 3.0.0
 */
public class AudienceUtils {

    /**
     * TYPE_STAR
     */
    public static final int TYPE_STAR = 3;
    /**
     * TYPE_ADMIN
     */
    public static final int TYPE_ADMIN = 2;
    /**
     * TYPE_AUDIENCE
     */
    public static final int TYPE_AUDIENCE = 1;

    /**
     * getUserType
     *
     * @param userId          userId
     * @param starId          starId
     * @param adminInfoResult adminInfoResult
     * @return UserType
     */
    public static int getUserType(long userId, long starId, AudienceResult adminInfoResult) {
        int userType;
        if (userId == starId) {
            userType = TYPE_STAR;
        } else {
            if (isAdmin(userId, adminInfoResult)) {
                userType = TYPE_ADMIN;
            } else {
                userType = TYPE_AUDIENCE;
            }
        }
        return userType;
    }

    /**
     * 判断一个用户是不是管理员
     *
     * @param userId          用户id
     * @param adminInfoResult 管理员数据
     * @return true 管理员
     */
    public static boolean isAdmin(long userId, AudienceResult adminInfoResult) {
        if (adminInfoResult == null) {
            return false;
        }
        List<AudienceResult.User> userList = adminInfoResult.getData().getUsers();
        boolean isAdmin = false;
        for (AudienceResult.User user : userList) {
            if (userId == user.getId()) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

    /**
     * getChatTargetById
     *
     * @param targetList targetList
     * @param userId     userId
     * @return Message.To
     */
    public static Message.To getChatTargetById(List<Message.To> targetList, long userId) {
        Message.To targetMessageTo = null;
        for (Message.To messageTo : targetList) {
            if (messageTo.getId() == userId) {
                targetMessageTo = messageTo;
                break;
            }
        }
        return targetMessageTo;
    }

    /**
     * addTargetToList
     *
     * @param targetList targetList
     * @param userId     userId
     * @param nickName   nickName
     * @param vipType    vipType
     * @param type       type
     * @param level      level
     * @return Message.To
     */
    public static Message.To addTargetToList(List<Message.To> targetList, long userId, String nickName, Enums.VipType vipType, int type, long level) {
        Message.To targetMessageTo = getChatTargetById(targetList, userId);
        if (targetMessageTo == null) {
            targetMessageTo = new Message.To();
            targetList.add(targetMessageTo);
        }
        targetMessageTo.setId(userId);
        targetMessageTo.setNickName(nickName);
        targetMessageTo.setVipType(vipType);
        targetMessageTo.setType(type);
        targetMessageTo.setLevel(level);
        return targetMessageTo;
    }

    /**
     * 添加聊天对象到列表
     *
     * @param chatUserInfo 聊天对象信息
     * @param targetList   聊天对象列表
     * @return 添加的对象
     */
    public static Message.To addTargetToList(ChatUserInfo chatUserInfo, List<Message.To> targetList) {
        long userId = UserInfoUtils.getUserInfo().getData().getId();
        if (chatUserInfo.getId() == 0) {
            return null;
        }
        if (chatUserInfo.getId() == userId) {
            PromptUtils.showToast(R.string.cant_chat_with_self, Toast.LENGTH_SHORT);
            return null;
        }
        return addTargetToList(targetList, chatUserInfo.getId(), chatUserInfo.getName(), chatUserInfo.getVipType()
                , chatUserInfo.getType(), chatUserInfo.getLevel());
    }

    /**
     * 检查是否允许悄悄话
     *
     * @param isToastTips ToastTips
     * @param context     context
     * @return 是否允许
     */
    public static boolean checkPrivateTalkPermission(boolean isToastTips, Context context) {
        UserInfoResult userInfoResult = UserInfoUtils.getUserInfo();
        long spendCoins = userInfoResult != null ? userInfoResult.getData().getFinance().getCoinSpendTotal() : 0;
        LevelUtils.UserLevelInfo userLevelInfo = LevelUtils.getUserLevelInfo(spendCoins);

        if (userLevelInfo.isCanPrivateTalk()) {
            return true;
        }

        if (isToastTips) {
            PromptUtils.showToast(String.format(context.getString(R.string.level_too_low_to_talk_prvivately)), Toast.LENGTH_SHORT);
        }
        return false;
    }
}
