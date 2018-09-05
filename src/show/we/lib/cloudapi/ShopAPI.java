package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.config.Enums.VipMonth;
import show.we.lib.model.MountListResult;
import show.we.lib.model.MyMountResult;
import show.we.lib.model.UnipayOrderNumberResult;

/**
 * @author ll
 * @version 1.0.0
 *          Date: 13-6-17
 *          Time: 下午2:48
 */
public final class ShopAPI {

    private static final String METHOD_MOUNT_MALL = "show/cars_list";
    private static final String METHOD_BUY_MOUNT = "shop/buy_car";
    private static final String METHOD_DEL_MOUNT = "shop/del_car";
    private static final String METHOD_MY_MOUNT = "user/car_info";
    private static final String METHOD_SET_DEFAULT_MOUNT = "shop/set_curr_car";
    private static final String METHOD_UNSET_MOUNT = "shop/unset_curr_car";
    private static final String METHOD_PROPS_MALL = "shop/buy_horn";
    private static final String METHOD_BUY_VIP = "shop";
    private static final String METHOD_UNIPAY_ORDER_ID = "unipay/order_id";

    private static final String KEY_ID = "_id";
    private static final String KEY_ROOM_ID = "room_id";
    private static final String KEY_FEE_NAME = "feename";
    private static final String KEY_PAY_FEE = "payfee";
    private static final String KEY_APP_DEVELOPER = "appdeveloper";
    private static final String KEY_GAME_ACCOUNT = "gameaccount";
    private static final String KEY_MAC_ADDRESS = "macaddress";
    private static final String KEY_APP_ID = "appid";
    private static final String KEY_IP_ADDRESS = "ipaddress";
    private static final String KEY_SERVICE_ID = "serviceid";
    private static final String KEY_CHANNEL_ID = "channelid";
    private static final String KEY_IMEI = "imei";
    private static final String KEY_APP_VERSION = "appversion";
    private static final String ROOM_ID = "room_id";

    /**
     * 获取座驾商城数据
     *
     * @return 座驾商城请求实例
     */
    public static Request<MountListResult> mountMall() {
        return new GetMethodRequest<MountListResult>(MountListResult.class, Config.getAPIHost(), METHOD_MOUNT_MALL);
    }

    /**
     * 购买座驾
     *
     * @param accessToken 用户的Token
     * @param mountId     座驾的Id
     * @return 购买请求
     */
    public static Request<BaseResult> buyMount(String accessToken, long mountId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_BUY_MOUNT)
                .addUrlArgument(accessToken)
                .addUrlArgument(mountId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求我的座驾信息
     *
     * @param accessToken 用户的Token
     * @return 请求
     */
    public static Request<MyMountResult> myMount(String accessToken) {
        return new GetMethodRequest<MyMountResult>(MyMountResult.class, Config.getAPIHost(), METHOD_MY_MOUNT)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 设置默认座驾
     *
     * @param accessToken 用户Token
     * @param mountId     座驾Id
     * @return 请求
     */
    public static Request<BaseResult> setDefaultMount(String accessToken, long mountId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SET_DEFAULT_MOUNT)
                .addUrlArgument(accessToken)
                .addUrlArgument(mountId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 删除座骑
     *
     * @param accessToken accessToken
     * @param mountId     mountId
     * @return 请求
     */
    public static Request<BaseResult> delMount(String accessToken, long mountId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_DEL_MOUNT)
                .addUrlArgument(accessToken)
                .addUrlArgument(mountId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 停用座驾
     *
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<BaseResult> unSetMount(String accessToken) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_UNSET_MOUNT)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 购买喇叭
     *
     * @param accessToken accessToken
     * @param type        套餐类型
     * @return Request
     */
    public static Request<BaseResult> buyHorn(String accessToken, int type) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_PROPS_MALL)
                .addUrlArgument(accessToken)
                .addUrlArgument(type)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求购买普通vip
     *
     * @param accessToken accessToken
     * @param vipMonth    购买vip月数
     * @param roomId      来自主播房间号
     * @return Http请求实例
     */
    public static Request<BaseResult> buyNormalVip(String accessToken,  String[] BuyType, int type) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_BUY_VIP)
        		.addUrlArgument(BuyType)
        		.addUrlArgument(accessToken)
                .addUrlArgument(type)
                .addArgument("via", "android")
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求购买至尊vip
     *
     * @param accessToken accessToken
     * @param vipMonth    购买vip月数
     * @param roomId      来自主播房间号
     * @return Http请求实例
     */
//    public static Request<BaseResult> buyExtremeVip(String accessToken, VipMonth vipMonth, long roomId) {
//        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_BUY_VIP2)
//                .addUrlArgument(accessToken)
//                .addUrlArgument(vipMonth.getValue())
//                .addArgument(ROOM_ID, roomId)
//                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
//    }

    /**
     * 请求续费至尊vip
     *
     * @param accessToken accessToken
     * @param vipMonth    vip月数
     * @param roomId      房间id
     * @return Http请求实例
     */
//    public static Request<BaseResult> renewExtremeVip(String accessToken, VipMonth vipMonth, long roomId) {
//        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_RENEW_VIP2)
//                .addUrlArgument(accessToken)
//                .addUrlArgument(vipMonth.getValue())
//                .addArgument(ROOM_ID, roomId)
//                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
//    }

    /**
     * 请求续费vip
     *
     * @param accessToken accessToken
     * @param vipMonth    vip月数
     * @param roomId      房间id
     * @return Http请求实例
     */
//    public static Request<BaseResult> renewNormalVip(String accessToken, VipMonth vipMonth, long roomId) {
//        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_RENEW_VIP)
//                .addUrlArgument(accessToken)
//                .addUrlArgument(vipMonth.getValue())
//                .addArgument(ROOM_ID, roomId)
//                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
//    }

    /**
     * 异步请求订单号(UniPay,订单号24位，不能有特殊字符)
     * @param userId 用户id
     * @param productName 商品名称
     * @param money 金额
     * @param companyName companyName
     * @param officialCode officialCode
     * @param channelId 渠道号，在assets\premessalbe.txt
     * @return Http请求实例
     */
    public static  Request<UnipayOrderNumberResult> orderId(long userId, String productName, int money
            , String companyName, String officialCode, String channelId) {
        return new GetMethodRequest<UnipayOrderNumberResult>(UnipayOrderNumberResult.class, Config.getAPIHost(), METHOD_UNIPAY_ORDER_ID)
                .addArgument(KEY_ID, userId)
                .addArgument(KEY_FEE_NAME, productName)
                .addArgument(KEY_PAY_FEE, money)
                .addArgument(KEY_APP_DEVELOPER, companyName)
                .addArgument(KEY_GAME_ACCOUNT, userId + "")
                .addArgument(KEY_MAC_ADDRESS, EnvironmentUtils.Network.wifiMac())
                .addArgument(KEY_IP_ADDRESS, EnvironmentUtils.Network.wifiMac())
                .addArgument(KEY_SERVICE_ID, officialCode)
                .addArgument(KEY_CHANNEL_ID, channelId)
                .addArgument(KEY_IMEI, EnvironmentUtils.Network.imei())
                .addArgument(KEY_APP_VERSION, EnvironmentUtils.Config.getAppVersion());
    }
}
