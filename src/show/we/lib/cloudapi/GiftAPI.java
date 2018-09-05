package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.model.BagGiftResult;
import show.we.lib.model.GiftListResult;

/**
 * 礼物相关API
 *
 * @author ll
 * @version 1.0.0
 */
public final class GiftAPI {

    private static final String METHOD_GIFT_LIST = "show/gift_list2";
    private static final String METHOD_SEND_GIFT = "room/send_gift";
    private static final String METHOD_GET_BAG_GIFT = "user/bag_info";
    private static final String METHOD_SEND_BAG_GIFT = "room/bag_gift";
    private static final String METHOD_GET_FEATHER = "feather/amass";
    private static final String METHOD_SEND_FEATHER = "feather/send";
    private static final String METHOD_SEND_FORTUNE = "fortune/send_fortune";

    private static final String KEY_COUNT = "count";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_MARQUEE = "marquee";

    /**
     * 礼物列表
     *
     * @return 礼物列表请求实例
     */
    public static Request<GiftListResult> giftList() {
        return new GetMethodRequest<GiftListResult>(GiftListResult.class, Config.getAPIHost(), METHOD_GIFT_LIST);
    }

    /**
     * 背包礼物
     *
     * @param accessToken 访问标志-表示已经被授权
     * @return 背包礼物请求实例
     */
    public static Request<BagGiftResult> bagGifts(String accessToken) {
        return new GetMethodRequest<BagGiftResult>(BagGiftResult.class, Config.getAPIHost(), METHOD_GET_BAG_GIFT)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }


    /**
     * 异步赠送礼物接口
     *
     * @param accessToken     访问标志-表示已经被授权
     * @param roomId          房间id
     * @param giftId          礼物id
     * @param dstUserId       目标用户id
     * @param count           赠送礼物数量
     * @param notShowSendGift 不上跑道
     * @return 礼物列表请求实例
     */
    public static Request<BaseResult> sendGift(String accessToken, long roomId, long giftId, long dstUserId, int count, boolean notShowSendGift) {
        if (notShowSendGift) {
            return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SEND_GIFT)
                    .addUrlArgument(accessToken)
                    .addUrlArgument(roomId)
                    .addUrlArgument(giftId)
                    .addArgument(KEY_COUNT, count)
                    .addArgument(KEY_USER_ID, dstUserId)
                    .addArgument(KEY_MARQUEE, "no")
                    .addArgument("via", "android")
                    .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
        } else {
            return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SEND_GIFT)
                    .addUrlArgument(accessToken)
                    .addUrlArgument(roomId)
                    .addUrlArgument(giftId)
                    .addArgument(KEY_COUNT, count)
                    .addArgument(KEY_USER_ID, dstUserId)
                    .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
        }
    }

    /**
     * 异步赠送背包礼物接口
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param roomId      房间id
     * @param giftId      礼物id
     * @param dstUserId   目标用户id
     * @param count       赠送礼物数量
     * @return 背包礼物列表请求实例
     */
    public static Request<BaseResult> sendBagGift(String accessToken, long roomId, long giftId, long dstUserId, int count) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SEND_BAG_GIFT)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addUrlArgument(giftId)
                .addArgument("via", "android")
                .addArgument(KEY_USER_ID, dstUserId)
                .addArgument(KEY_COUNT, count)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步生成羽毛
     *
     * @param accessToken 访问标志-表示已经被授权
     * @return 羽毛请求实例
     */
    public static Request<BaseResult> getFeather(String accessToken) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_GET_FEATHER)
                .addUrlArgument(accessToken)
                .addArgument("via", "android")
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"))
                .addArguments(EnvironmentUtils.GeneralParameters.parameters());
    }

    /**
     * 异步赠送羽毛
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param roomId      房间id
     * @return 羽毛赠送请求实例
     */
    public static Request<BaseResult> sendFeather(String accessToken, long roomId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SEND_FEATHER)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addArgument("via", "android")
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 送财神
     * @param accessToken 访问标志-表示已经被授权
     * @param roomId      房间id
     * @param count count
     * @return Request
     */
    public static Request<BaseResult> sendFortune(String accessToken, long roomId, int count) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SEND_FORTUNE)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addArgument("via", "android")
                .addArgument(KEY_COUNT, count)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }
}
