package show.we.lib.cloudapi;

import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.model.*;

/**
 * 支付相关api
 *
 * @author ll
 * @version 1.0.0
 */
public final class PayAPI {

    private static final String METHOD_TENPAY_ORDER = "tenpay/order_mobile";
    private static final String METHOD_ORDER_NUMBER_ALI = "payformobile/ali_mobile_sign";
    private static final String METHOD_UNIPAY_ORDER_ID = "unipay/orderid";
    private static final String METHOD_UNIONPAY_ORDER_ID = "unionpayformobile/mobile_upmporder";
    
    private static final String TTUS_HOST_UNIPAY = "http://172.16.2.247:8080";//联调测试
    
    private static final String METHOD_YEEPAY = "pay/yeepay_wap";
    private static final String METHOD_YEEPAY_STATUS = "pay/trade_query";
    private static final String METHOD_SMS_ORDER_ID = "sms/order_id";
    private static final String METHOD_FIRST_RECHARGE = "user/first_charge";
    
    private static final String KEY_COIN = "coin";
    private static final String KEY_ID = "payUserId";
//    private static final String KEY_ID = "o7s9nqentt4o3y4klyq517fscap23e52";
    private static final String KEY_AMOUNT = "coin";
    private static final String KEY_CUSTOM = "custom";
    private static final String KEY_MONEY = "cny";
    private static final String KEY_ORIGIN = "origin";
    private static final String KEY_MP = "pa_MP";
    private static final String KEY_FRPID = "pd_FrpId";
    private static final String KEY_AMT = "p3_Amt";
    private static final String KEY_CARDAMT = "pa7_cardAmt";
    private static final String KEY_CARDNO = "pa8_cardNo";
    private static final String KEY_CARDPWD = "pa9_cardPwd";
    private static final String KEY_MZ = "mz";

    private static final String KEY_ORDERID = "order_id";


    /**
     * 获取财付通订单号
     *
     * @param userId   用户id
     * @param amount   金额(单位：分)
     * @param brokerId 代理人id
     * @return Request
     */
    public static Request<TenpayOrderResult> tenpayOrder(long userId, int amount, long brokerId) {
        return new GetMethodRequest<TenpayOrderResult>(TenpayOrderResult.class, Config.getAPIHost(), METHOD_TENPAY_ORDER)
                .addArgument(KEY_ID, userId)
                .addArgument(KEY_AMOUNT, amount)
                .addArgument(KEY_CUSTOM, userId + "_" + brokerId);
    }

    /**
     * 获取财付通订单号
     *
     * @param userId 用户id
     * @param amount 金额(单位：分)
     * @return Request
     */
    public static Request<TenpayOrderResult> tenpayOrder(long userId, int amount) {
        return new GetMethodRequest<TenpayOrderResult>(TenpayOrderResult.class, Config.getAPIHost(), METHOD_TENPAY_ORDER)
                .addArgument(KEY_ID, userId)
                .addArgument(KEY_AMOUNT, amount);
    }

    /**
     * 异步请求订单号(AliPay 支付宝)
     *
     * @param userId  用户id
     * @param money   用户选择的人民币
     * @param proxyId 代理的id，没有代理人填写-1
     * @return Http请求实例
     */
    public static Request<AlipayOrderNumberResult> orderNumberForAliPay(long userId, long money, long proxyId) {
        return new GetMethodRequest<AlipayOrderNumberResult>(AlipayOrderNumberResult.class, Config.getAPIHost(), METHOD_ORDER_NUMBER_ALI)
        	    .addArgument("payUserId", userId)
        	    .addArgument(KEY_MONEY, money)
//                .addArgument(KEY_ORIGIN, proxyId == -1 ? userId + "" : userId + "_" + proxyId)
                .addArgument("toUserId", userId)
                .addArgument("via", "Android")
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"))
                .addArgument("merId" , "2088801770882180")
                .addArgument("payQd", "ali");
    }

    /**
     * 异步请求订单号(UniPay,订单号24位，不能有特殊字符) - 联通卡
     *
     * @param userId 用户id
     * @return Http请求实例
     */
    public static Request<UnipayOrderNumberResult> unipayOrder(long userId) {
        return new GetMethodRequest<UnipayOrderNumberResult>(UnipayOrderNumberResult.class, Config.getAPIHost(), METHOD_UNIPAY_ORDER_ID)
                .addArgument(KEY_ID, userId);
    }

    /**
     * 获取财付通订单号 - 银联
     *
     * @param userId   用户id
     * @param extraCommonParam  金币
     * @param selectMoney 金额
     * @return Request
     */
    public static Request<UnionpayOrderNumberResult> unionpayOrder(long userId, /*long extraCommonParam,*/ long selectMoney) {
        return new GetMethodRequest<UnionpayOrderNumberResult>(UnionpayOrderNumberResult.class, Config.getAPIHost(), METHOD_UNIONPAY_ORDER_ID)
//                .addArgument(KEY_ID, userId)
//                .addArgument(KEY_AMOUNT, extraCommonParam)
//              .addArgument("merId" , "880000000002311")   //测试账号
                .addArgument("merId" , "802500048990555")//正式账号
                .addArgument("payUserId", userId)
                .addArgument(KEY_MONEY, selectMoney < 0 ? 0 : selectMoney)
                .addArgument("toUserId", userId)
                .addArgument("via", "Android")
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"))
                .addArgument("payQd", "chinapay");
    }
    

    /**
     * 异步请求订单号(UniPay,订单号24位，不能有特殊字符)-联通手机支付
     *
     * @param userId 用户id
     * @return Http请求实例
     */
    public static Request<UnicomOrderNumberResult> unicomOrderId(long userId) {
        return new GetMethodRequest<UnicomOrderNumberResult>(UnicomOrderNumberResult.class, Config.getAPIHost(), METHOD_UNIPAY_ORDER_ID)
                .addArgument(KEY_ID, userId);
    }

    /**
     * @param userId  用户ID
     * @param mMP     经纪人ID
     * @param mFrpId  银行编号 大写
     * @param mAmt    金额支付金额,必填.单位:元，精确到分
     * @param cardAmt 卡组总金额
     * @param cardNo  卡号组
     * @param cardPwd 卡密组
     * @return Request
     */
    public static Request<YeepayResult> yeepayNonBankcard(long userId, long mMP, String mFrpId, float mAmt, float cardAmt, String cardNo, String cardPwd) {
    	return new GetMethodRequest<YeepayResult>(YeepayResult.class, Config.getAPIHost(), METHOD_YEEPAY)
                .addArgument(KEY_ID, userId)
                .addArgument(KEY_MP, mMP == -1 ? userId + "" : userId + "_" + mMP)
                .addArgument(KEY_FRPID, mFrpId)
                .addArgument(KEY_AMT, mAmt)
                .addArgument(KEY_CARDAMT, cardAmt)
                .addArgument(KEY_CARDNO, cardNo)
                .addArgument(KEY_CARDPWD, cardPwd);
    }

    /**
     * @param mOrderId 生成的定单号
     * @return Request
     */
    public static Request<YeepayStatusResult> yeepayStatusNonBankcard(String mOrderId) {
        return new GetMethodRequest<YeepayStatusResult>(YeepayStatusResult.class, Config.getAPIHost(), METHOD_YEEPAY_STATUS)
                .addArgument(KEY_ORDERID, mOrderId);

    }

    /**
     * 登录支付订单
     * @param userId userId
     * @param money money
     * @return Request
     */
    public static Request<SmsOrderIdResult> smsOrderId(long userId, int money) {
        return new GetMethodRequest<SmsOrderIdResult>(SmsOrderIdResult.class, Config.getAPIHost(), METHOD_SMS_ORDER_ID)
                .addArgument(KEY_ID, userId)
                .addArgument(KEY_MZ, money)
                .addArgument(KEY_CUSTOM, userId);
    }

    /**
     * 请求首充优惠
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<FirstRechargeAwardResult> requestFirstRechargeAward(String accessToken) {
        return new GetMethodRequest<FirstRechargeAwardResult>(FirstRechargeAwardResult.class, Config.getAPIHost(), METHOD_FIRST_RECHARGE)
                .addUrlArgument(accessToken);
    }
}
