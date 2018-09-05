package show.we.lib.cloudapi;

import show.we.lib.config.Config;
import show.we.lib.config.Enums;
import show.we.lib.model.ExchangeRecordListResult;
import show.we.lib.model.FavStarListResult;
import show.we.lib.model.HornResult;
import show.we.lib.model.LatestContactListResult;
import show.we.lib.model.MailConversationResult;
import show.we.lib.model.MissionListResult;
import show.we.lib.model.MissionNumResult;
import show.we.lib.model.NoticeListResult;
import show.we.lib.model.ReceiveSendGiftRecordResult;
import show.we.lib.model.RechargeRecordListResult;
import show.we.lib.model.RemindListResult;
import show.we.lib.model.RemindNoticeUnReadCountResult;
import show.we.lib.model.SignListResult;
import show.we.lib.model.TaskListResult;
import show.we.lib.model.UnLoginTaskListResult;
import show.we.lib.model.UploadTokenResult;
import show.we.lib.model.UserArchiveResult;
import show.we.lib.model.UserBadgeResult;
import show.we.lib.model.UserInfoResult;
import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.PostMethodRequest;
import show.we.sdk.request.PostMethodRequestV2;
import show.we.sdk.request.Request;
import show.we.sdk.usersystem.UserResult;
import show.we.sdk.util.ConstantUtils;
import show.we.sdk.util.DateUtils;
import show.we.sdk.util.EnvironmentUtils;
import show.we.sdk.util.StringUtils;


/**
 * 用户相关API
 *
 * @author ll
 * @version 1.0.0
 */
public final class UserSystemAPI {

    /**
     * 每页消费记录默认项数
     */
    public static final int COST_LOG_SIZE = 20;
    private static final String COST_TYPE_SEND_GIFT = "send_gift";
    private static final String TTUS_HOST = "http://ttus.51weibo.com/";
    private static final String METHOD_SHOW_USER_ARCHIVE = "zone/user_info";
    private static final String METHOD_SHOW_USER_INFO = "user/info";
    private static final String METHOD_EDIT_USER_INFO = "user/edit";
    private static final String METHOD_ROUTINET_LIST = "routinetesks/routinet_list";
    private static final String METHOD_ROUTINET_LIST_UNLOGIN = "taskcommonality/getTasksContent";
    private static final String METHOD_ROUTINET_GET = "routinetesks/routinet_get";
    private static final String METHOD_FAVORITE_STAR = "user/following_list";
    private static final String METHOD_RECHARGE_RECORD = "user/pay_log";
    private static final String METHOD_EXCHANGE_RECORD = "user/exchange_log";
    private static final String METHOD_EXCHANGE = "user/exchange";
    private static final String METHOD_ADD_FOLLOWING = "user/add_following";
    private static final String METHOD_DEL_FOLLOWING = "user/del_following";
    private static final String METHOD_HORN_INFO = "/user/horn_info";
    private static final String METHOD_PRAISE_PHOTO = "/photo/praise";
    private static final String METHOD_GRAB_SOFA = "live/grab_sofa";
    private static final String METHOD_MAIL_LATEST_CONTACE = "mail/latest_contact";
    private static final String METHOD_MAIL_INBOX_FROM = "mail/inbox_from";
    private static final String METHOD_MAIL_SEND = "mail/send";
    private static final String METHOD_MAIL_MARK_READ = "mail/mark_read";
    private static final String METHOD_MAIL_MARK_READ_FROM = "mail/mark_read_from";
    private static final String METHOD_MAIL_DEL = "mail/del";
    private static final String METHOD_MAIL_DEL_FROM = "mail/del_from";
    private static final String METHOD_VIP_HIDING = "user/vip_hiding";
    private static final String METHOD_SIGN_LIST = "signin/sign_list";
    private static final String METHOD_SIGN_AWARD = "signin/sign_get";
    private static final String METHOD_MISSION_NUM = "zone/mission_num";
    private static final String METHOD_ADD_WHISPER = "whisper/add";
    private static final String METHOD_LOAD_SHARE_STATISTIC = "user/m_share";
    private static final String METHOD_HIT_EGG = "egg/open";
    private static final String METHOD_ALTER_PWD = "user/changePassword";
    private static final String METHOD_FIND_PWD = "find_pwd";
    private static final String METHOD_CHECK_EMAIL = "checkname";
    private static final String METHOD_LOGIN = "login";
    private static final String METHOD_COST_LOG = "user/cost_log";
    private static final String METHOD_RECEIVE_GIFT_LOG = "user/gift_rec";
    private static final String METHOD_MSG_LIST = "msg/list";
    private static final String METHOD_UNREAD_COUNT = "msg/unread_count";
    private static final String METHOD_MSG_DEL = "msg/del";
    private static final String METHOD_MSG_MARK_READ = "msg/mark_read";
    private static final String METHOD_REMIND_LIST = "remind/list";
    private static final String METHOD_REMIND_UNREAD_COUNT = "remind/unread_count";
    private static final String METHOD_REMIND_MARK_READ = "remind/mark_read";
    private static final String METHOD_REMIND_DEL = "remind/del";
    private static final String METHOD_REMIND_SET = "remind/set";
    private static final String METHOD_REQUEST_BADGE = "zone/user_medal";
    private static final String METHOD_ACCUSE_PHOTO_TOKEN = "photo/accuse_token";
    private static final String METHOD_REJISTER_FOR_MOBILE = "ttus/registerForMobile";

    private static final String KEY_USER_ID = "_id";
    private static final String KEY_NICK_NAME = "nick_name";
    private static final String KEY_SEX = "sex";
    private static final String KEY_PIC = "pic";
    private static final String KEY_CONSTELLATION = "constellation";
    private static final String KEY_STATURE = "stature";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_PROVINCE = "Province";
    private static final String KEY_CITY = "city";
    private static final String KEY_COUNTY = "county";
    private static final String KEY_REAL_NAME = "real_name";
    private static final String KEY_TELEPHONE = "telephone";
    private static final String KEY_BANK_ACCOUNT = "bank_account";
    private static final String KEY_BANK_NAME = "bank_name";
    private static final String KEY_BANK_NO = "bank_no";
    private static final String KEY_MISSION_ID = "mission_id"; 
    private static final String KEY_USER_INFO = "user_info";
    
    private static final String KEY_OPEN_ID = "openId";
    private static final String KEY_CLIENT_ID = "clientId";
    private static final String KEY_APP_ID = "appId";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_VIA = "via";
    private static final String KEY_PAGE = "page";
    private static final String KEY_SIZE = "size";
    private static final String KEY_PHOTO_PATH = "path";
    private static final String KEY_MSG = "msg";
    private static final String KEY_TO_ID = "to_id";
    private static final String KEY_ROOM_ID = "room_id";
    private static final String KEY_FROM = "from";
    private static final String KEY_LOGITUDE = "coordinate.x";
    private static final String KEY_LATITUDE = "coordinate.y";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_OLD_PASSWORD = "old_password";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_START_TIME = "stime";
    private static final String KEY_END_TIME = "etime";
    private static final String KEY_COST_TYPE = "type";
    private static final String KEY_LEND = "lend";
    private static final String KEY_ID1 = "id1";
    private static final String KEY_ID2 = "id2";
    private static final String KEY_LBEGIN = "lbegin";
    private static final String KEY_AUTH_CODE = "auth_code";
    private static final String KEY_AUTH_KEY = "auth_key";
    
   
    /**
     * 查询绑定状态
     */
    private static final String KEY_CHECKSMS = "checkSmsmsgActivateInfo";
    /**
     * 获取验证码
     */
    private static final String KEY_GETSMS = "getSmsActivateInfo";
    /**
     * 绑定手机或者重置密码验证码校验
     */
    private static final String KEY_CONFIRMSMS = "confirmSmsgActivateInfo";
    /**
     * 验证是否可以重新绑定手机
     */
    private static final String KEY_CHECKSMSGACTIVATE = "checkSmsgActivateexprietime";
    /**
     * 重置密码
     */
    private static final String SMSUPDATEPASSWORD = "smsUpdatePassword";
    
//    private static final String TTUS = "http://172.16.2.173:8086/thirdlogin/qqForAndroid";
   
    
    private static final String TTUS = "http://172.16.2.173:8086";   

    /**
     * 
     * 1.查询绑定状态
     *
     * @param userName 用户名
     * @return Request
     */
    public static Request<BaseResult> checkSmsMsgActivateInfo(String userName) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getTtusHost(), KEY_CHECKSMS)
        		.addArgument("userName",userName);
    }
    
    
    /**
     * 
     * 2．获取验证码
     *
     * @param userName 用户名
     * @param type 操作类型
     * @param phone 用户手机号
     * @return Request
     */
    public static Request<BaseResult> getSmsActivateInfo(String userName,String type,String phone) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getTtusHost(), KEY_GETSMS)
                .addArgument("userName",userName)
                .addArgument("phone",phone)
                .addArgument("type",type);
                
    }
    

    /**
     * 
     * 3．绑定手机或者重置密码验证码校验
     *
     * @param userName 用户名
     * @param code 手机验证码
     * @param type 操作类型
     * @param pwd 用户密码
     * @return Request
     */
    public static Request<BaseResult> confirmSmsgActivateInfo(String userName,String code,String type,String pwd) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getTtusHost(), KEY_CONFIRMSMS)
                .addArgument("userName",userName)
                .addArgument("code",code)
                .addArgument("type",type)
                .addArgument("pwd",pwd);
    }
    
    /**
     * 
     * 3．重置密码验证码校验
     *
     * @param userName 用户名
     * @param code 手机验证码
     * @param type 操作类型
     * @param pwd 用户密码
     * @return Request
     */
    public static Request<BaseResult> confirmSmsgPwdInfo(String userName,String code,String type) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getTtusHost(), KEY_CONFIRMSMS)
                .addArgument("userName",userName)
                .addArgument("code",code)
                .addArgument("type",type);                
    }
    
    /**
     * 
     * 3．重置密码验证码校验
     *
     * @param userName 用户名
     * @param code 手机验证码
     * @param type 操作类型
     * @param pwd 用户密码
     * @return Request
     */
    public static Request<BaseResult> confirmRegister(String userName,String code,String type,String np,String vp,String phoneNum) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getTtusHost(), KEY_CONFIRMSMS)
                .addArgument("userName",userName)
                .addArgument("code",code)
                .addArgument("type",type)                
                .addArgument("np",np)                
				.addArgument("vp",vp)
				.addArgument("phone",phoneNum);
    }
    
    /**
     * 
     * 4．验证是否可以重新绑定手机
     *
     * @param userName 用户名
     * @return Request
     */
    public static Request<BaseResult> checkSmsgActivateexprietime(String userName) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getTtusHost(), KEY_CHECKSMSGACTIVATE)
                .addUrlArgument(userName);
    }
    
    /**
     * 
     * 5．重置密码
     *
     * @param userName 用户名
     * @param np 新密码
     * @param vp 重复密码
     * @return Request
     */
    public static Request<BaseResult> smsUpdatePassword(String userName,String np,String vp) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getTtusHost(), SMSUPDATEPASSWORD)
                .addArgument("userName",userName)
                .addArgument("np",np)
                .addArgument("vp",vp);
    }
    
    /**
     * 记录悄悄话
     *
     * @param accessToken accessToken
     * @param toUserId    toUserId
     * @param roomId      roomId
     * @param msg         msg
     * @return Request
     */
    public static Request<BaseResult> addWhisper(String accessToken, long toUserId, long roomId, String msg) {
        return new PostMethodRequestV2<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_ADD_WHISPER)
                .addArgument(KEY_ACCESS_TOKEN, accessToken)
                .addArgument(KEY_TO_ID, toUserId)
                .addArgument(KEY_ROOM_ID, roomId)
                .addArgument(KEY_FROM, EnvironmentUtils.GeneralParameters.getFromId())
                .addArgument(KEY_MSG, msg)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 获取非当前登录用户的信息
     * @param userId  userId
     * @return Request
     */
     public static Request<UserArchiveResult> userArchiveInfo(long userId) {
         return new GetMethodRequest<UserArchiveResult>(UserArchiveResult.class, Config.getAPIHost(), METHOD_SHOW_USER_ARCHIVE)
                 .addUrlArgument(userId);
     }



    /**
     * 获取用户个人资料信息
     *
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<UserInfoResult> userInfo(String accessToken) {
        return new GetMethodRequest<UserInfoResult>(UserInfoResult.class, Config.getAPIHost(), METHOD_SHOW_USER_INFO)
                .addUrlArgument(accessToken);
//                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"))
//                .addArguments(EnvironmentUtils.GeneralParameters.parameters());
    }

    /**
     * 异步请求修改用户信息
     *
     * @param accessToken    访问标志-表示已经被授权
     * @param userInfoResult 新的用户数据
     * @return Request
     */
    public static Request<BaseResult> uploadUserInfo(String accessToken, UserInfoResult userInfoResult) {
        return new PostMethodRequestV2<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_EDIT_USER_INFO)
                .addArgument(KEY_ACCESS_TOKEN, accessToken)
//        		.addUrlArgument(accessToken)
                .addArgument(KEY_NICK_NAME, userInfoResult.getData().getNickName())
                .addArgument(KEY_SEX, userInfoResult.getData().getSex())
                .addArgument(KEY_PIC, userInfoResult.getData().getPicUrl())
                .addArgument(KEY_CONSTELLATION, userInfoResult.getData().getConstellation())
                .addArgument(KEY_STATURE, userInfoResult.getData().getStature())
                .addArgument(KEY_LOCATION, userInfoResult.getData().getLocation())
                .addArgument(KEY_PROVINCE, userInfoResult.getData().getProvince())
                .addArgument(KEY_CITY, userInfoResult.getData().getCity())
                .addArgument(KEY_PROVINCE, userInfoResult.getData().getCounty())
                .addArgument(KEY_REAL_NAME, userInfoResult.getData().getRealName())
                .addArgument(KEY_TELEPHONE, userInfoResult.getData().getTelephone())
                .addArgument(KEY_BANK_ACCOUNT, userInfoResult.getData().getBankAccount())
                .addArgument(KEY_BANK_NAME, userInfoResult.getData().getBankName())
                .addArgument(KEY_BANK_NO, userInfoResult.getData().getBankNo())
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求修改用户经纬度
     *
     * @param accessToken    访问标志-表示已经被授权
     * @param longitude 经度
     * @param latitude 纬度
     * @param address 地址
     * @return Request
     */
    public static Request<BaseResult> uploadUserLocation(String accessToken, double longitude, double latitude, String address) {
        Request<BaseResult> request = new PostMethodRequestV2<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_EDIT_USER_INFO)
                .addArgument(KEY_ACCESS_TOKEN, accessToken)
                .addArgument(KEY_LOGITUDE, longitude)
                .addArgument(KEY_LATITUDE, latitude)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
        if (!StringUtils.isEmpty(address)) {
            request.addArgument(KEY_ADDRESS, address);
        }
        return request;
    }

    /**
     * 未登录异步请求任务列表接口
     *
     * @param accessToken 访问标志-表示已经被授权
     * @return Request
     */
    public static Request<TaskListResult> unLoginMission(int via) {
    	return new GetMethodRequest<TaskListResult>(TaskListResult.class,  Config.getAPIHost(), METHOD_ROUTINET_LIST_UNLOGIN)
    			.addArgument("via", 1)
    			.addArgument("tasktype", 0);
    }
    
    /**
     * 异步请求任务列表接口
     *
     * @param accessToken 访问标志-表示已经被授权
     * @return Request
     */
    public static Request<TaskListResult> mission(String accessToken) {
    	return new GetMethodRequest<TaskListResult>(TaskListResult.class,  Config.getAPIHost(), METHOD_ROUTINET_LIST)
    			.addArgument("access_token", accessToken)
    			.addArgument("tasktype", 0)
    			.addArgument("via", 1);
    }
    /**
     * 领取奖励
     *
     * @param accessToken accessToken
     * @param missionId   missionId
     * @param authCode    验证码
     * @return Request
     */
    public static Request<BaseResult> drawBonus(String accessToken, int missionId/*, String authCode*/) {
    	return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_ROUTINET_GET)
    			.addArgument("access_token", accessToken)
    			.addArgument("taskid", missionId);
//    			.addArgument(KEY_AUTH_CODE, authCode)
//    			.addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求关注主播列表
     *
     * @param accessToken 访问标志-表示已经被授权
     * @return Request
     */
    public static Request<FavStarListResult> favoriteStar(String accessToken) {
        return new GetMethodRequest<FavStarListResult>(FavStarListResult.class, Config.getAPIHost(), METHOD_FAVORITE_STAR)
                .addUrlArgument(accessToken)
                .addArguments(EnvironmentUtils.GeneralParameters.parameters())
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 我的充值记录
     * 异步请求充值记录列表
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param currentPage int
     * @param pageSize    int
     * @param stime    	  long
     * @param etime       long
     * @return Request
     */
    public static Request<RechargeRecordListResult> rechargeRecordList(String accessToken, int Page, int Size,
    		long stime, long etime, int id1, int id2) {
    	final int daysMonth = 30;
    	long currentTime = System.currentTimeMillis() + ConstantUtils.MILLS_PER_DAY;
        return new GetMethodRequest<RechargeRecordListResult>(RechargeRecordListResult.class, Config.getAPIHost(), METHOD_RECHARGE_RECORD)
                .addArgument(KEY_ACCESS_TOKEN, accessToken)
                .addArgument(KEY_PAGE, Page)
                .addArgument(KEY_SIZE, Size)
		        .addArgument(KEY_START_TIME, DateUtils.formatDate(stime , DateUtils.FORMAT_TO_DAY, "-"))
                .addArgument(KEY_END_TIME, DateUtils.formatDate(etime, DateUtils.FORMAT_TO_DAY, "-"))
		        .addArgument(KEY_ID1, id1)
		        .addArgument(KEY_ID2, id2)
		        .addArgument("via", "android")
		        .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求兑换记录列表
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param currentPage int
     * @param pageSize    int
     * @return Request
     */
    public static Request<ExchangeRecordListResult> exchangeRecordList(String accessToken, int currentPage, int pageSize) {
        return new GetMethodRequest<ExchangeRecordListResult>(ExchangeRecordListResult.class, Config.getAPIHost(), METHOD_EXCHANGE_RECORD)
                .addArgument(KEY_ACCESS_TOKEN, accessToken)
                .addArgument(KEY_PAGE, currentPage)
                .addArgument(KEY_SIZE, pageSize)
                .addArgument("via", "android")
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 兑换金币
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param num         int
     * @param authCode    String
     * @return Request
     */
    public static Request<BaseResult> exchangeCoin(String accessToken, int num, String authCode) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_EXCHANGE)
                .addUrlArgument(accessToken)
                .addUrlArgument(num)
                .addArgument("via", "android")
                .addArgument(KEY_AUTH_CODE, authCode)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 添加关注
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param starId      starId
     * @return Request
     */
    public static Request<BaseResult> addFollow(String accessToken, long id1) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_ADD_FOLLOWING)
                .addUrlArgument(accessToken)
                .addUrlArgument(id1)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 取消关注
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param starId      主播id
     * @return Request
     */
    public static Request<BaseResult> delFollow(String accessToken, long id1) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_DEL_FOLLOWING)
                .addUrlArgument(accessToken)
                .addUrlArgument(id1)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求用户喇叭数量
     *
     * @param accessToken 访问标志-表示已经被授权
     * @return Request
     */
    public static Request<HornResult> getHornInfo(String accessToken) {
        return new GetMethodRequest<HornResult>(HornResult.class, Config.getAPIHost(), METHOD_HORN_INFO)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求赞照片
     *
     * @param accessToken 访问标志-表示已经被授权
     * @param photoPath   图片路径
     * @return Request
     */
    public static Request<BaseResult> praisePhoto(String accessToken, String photoPath) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_PRAISE_PHOTO)
                .addUrlArgument(accessToken)
                .addArgument(KEY_PHOTO_PATH, photoPath)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求抢沙发
     *
     * @param sofaId      沙发id
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param cost        单位:100金币
     * @return Request
     */
    public static Request<BaseResult> grabSofa(int sofaId, String accessToken, long roomId, long cost) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_GRAB_SOFA + sofaId)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addUrlArgument(cost)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求最新发私信联系人
     *
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<LatestContactListResult> latestMailContacts(String accessToken) {
        return new GetMethodRequest<LatestContactListResult>(LatestContactListResult.class, Config.getAPIHost(), METHOD_MAIL_LATEST_CONTACE)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求私信联系人会话
     *
     * @param accessToken accessToken
     * @param fromUserId  发送用户id
     * @return Request
     */
    public static Request<MailConversationResult> mailConversation(String accessToken, long fromUserId) {
        return new GetMethodRequest<MailConversationResult>(MailConversationResult.class, Config.getAPIHost(), METHOD_MAIL_INBOX_FROM)
                .addUrlArgument(accessToken)
                .addUrlArgument(fromUserId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求发送私信
     *
     * @param accessToken accessToken
     * @param toUserId    发送用户id
     * @param msg         消息内容
     * @return Request
     */
    public static Request<BaseResult> sendMail(String accessToken, long toUserId, String msg) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MAIL_SEND)
                .addUrlArgument(accessToken)
                .addUrlArgument(toUserId)
                .addArgument(KEY_MSG, msg)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求标记某条私信为已读
     *
     * @param accessToken accessToken
     * @param mailId      消息id
     * @return Request
     */
    public static Request<BaseResult> markReadStatusByMsg(String accessToken, String mailId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MAIL_MARK_READ)
                .addUrlArgument(accessToken)
                .addUrlArgument(mailId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求标识某用户私信为已读
     *
     * @param accessToken accessToken
     * @param fromUserId  用户id
     * @return Request
     */
    public static Request<BaseResult> markReadStatusByUser(String accessToken, long fromUserId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MAIL_MARK_READ_FROM)
                .addUrlArgument(accessToken)
                .addUrlArgument(fromUserId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求发送私信
     *
     * @param accessToken accessToken
     * @param mailId      私信id
     * @return Request
     */
    public static Request<BaseResult> deleteMail(String accessToken, String mailId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MAIL_DEL)
                .addUrlArgument(accessToken)
                .addUrlArgument(mailId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 异步请求删除私信
     *
     * @param accessToken accessToken
     * @param fromUserId  用户id
     * @return Request
     */
    public static Request<BaseResult> deleteUserMail(String accessToken, long fromUserId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MAIL_DEL_FROM)
                .addUrlArgument(accessToken)
                .addUrlArgument(fromUserId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 设置Vip隐身状态
     *
     * @param accessToken accessToken
     * @param isHiding    true为隐身，false为不隐身
     * @return Request
     */
    public static Request<BaseResult> setVipHidingState(String accessToken, boolean isHiding) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_VIP_HIDING)
                .addUrlArgument(accessToken)
                .addUrlArgument(isHiding ? 1 : 0)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 根据qq登陆token获取自有用户系统token
     *
     * @param openId      qq登陆返回openId
     * @param accessToken qq登陆返回accessToken
     * @param clientId    注册应用后生成的appid
     * @return via		    客户端类型
     * @return tpf		    登陆渠道
     */
    public static Request<UserResult> postUserAccessToken(String openId,  String access_token, String clientId) {
        return new PostMethodRequestV2<UserResult>(UserResult.class, Config.getQQLoginAPIHost()) 
                .addArgument(KEY_OPEN_ID, openId)
                .addArgument(KEY_CLIENT_ID, clientId)
                .addArgument(KEY_APP_ID, clientId)
                .addArgument(KEY_ACCESS_TOKEN, access_token)
                .addArgument(KEY_VIA, "android")
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"))
                .addArgument("tpf", "qq");
    }
    

    /**
     * 获取用户当月签到列表
     *
     * @param accessToken accessToken
     * @return SignListResult
     */
    public static Request<SignListResult> getSignList(String accessToken) {
        return new GetMethodRequest<SignListResult>(SignListResult.class, Config.getAPIHost(),  METHOD_SIGN_LIST)
                .addArgument("access_token",accessToken)
                .addArgument("via","1")
                .addArgument("qd", "66666000003")
                .addArgument("tasktype", "1");
        
    }
    /**
     * 领取签到奖励
     *
     * @param accessToken accessToken
     * @param taskid        1为1天，2为2天，3为3天，4为4天
     * @param tasknum   
     * @return SignListResult
     */
    public static Request<SignListResult> getDailySignAward(String accessToken, String taskid,String tasknum ) {
        return new GetMethodRequest<SignListResult>(SignListResult.class, Config.getAPIHost(), METHOD_SIGN_AWARD)
        		.addArgument("access_token",accessToken)
                .addArgument("taskid",taskid)
                .addArgument("tasknum",tasknum)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }


    /**
     * 领取每月额外累计奖励
     *
     * @param accessToken accessToken
     * @param type        1为3天，2为7天，3为15天，4为28天
     * @param authCode    验证码
     * @return BaseResult
     */
    public static Request<BaseResult> getSignAward(String accessToken, int type, String authCode) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SIGN_AWARD)
                .addUrlArgument(accessToken)
                .addUrlArgument(type)
                .addArgument(KEY_AUTH_CODE, authCode)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * getMissionNum
     *
     * @return MissionNumResult
     */
    public static Request<MissionNumResult> getMissionNum() {
        return new GetMethodRequest<MissionNumResult>(MissionNumResult.class, Config.getAPIHost(), METHOD_MISSION_NUM);
    }

    /**
     * 上传分享数据给服务器
     *
     * @param accessToken accessToken
     * @param starId      starId
     * @param type        type
     * @return 上传Request
     */
    public static Request<BaseResult> loadShareStatistic(String accessToken, long starId, Enums.ShareType type) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_LOAD_SHARE_STATISTIC)
                .addUrlArgument(accessToken)
                .addUrlArgument(starId)
                .addUrlArgument(type.getValue())
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 使用不同锤子砸金蛋
     * @param accessToken accessToken
     * @param roomId   房间id
     * @param hammerType 锤子类型
     * @return  BaseResult
     */
    public static Request<BaseResult> hitEgg(String accessToken, long roomId, Enums.HammerType hammerType) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_HIT_EGG + hammerType.getCost())
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId);
    }

    //原cloudapi.jar包中的方法
    /**
     * 修改密码
     * @param accessToken accessToken
     * @param oldPassword oldPassword
     * @param newPassword newPassword
     * @return Request
     */
    public static Request<BaseResult> modifyPassword(String accessToken, String op, String np) {
        return new GetMethodRequest(BaseResult.class, Config.getAPIHost(), METHOD_ALTER_PWD)
                .addArgument(KEY_ACCESS_TOKEN, accessToken)
                .addArgument("op", op)
                .addArgument("np", np)
                .addArgument("vp", np);
    }

    /**
     * 找回密码
     * @param userName userName
     * @return Request
     */
    public static Request<BaseResult> findPassword(String userName) {
        return new PostMethodRequest(BaseResult.class, Config.getAPIHost(), METHOD_FIND_PWD)
                .addArgument(KEY_USER_NAME, userName);
    }

    /**
     * 注册
     * @param username username
     * @param password password
     * @param authCode
     * @param authKey
     * @return Request
     */
    public static Request<UserResult> register(String username, String password, String authCode, String authKey) {
        return new GetMethodRequest(UserResult.class, TTUS_HOST, METHOD_REJISTER_FOR_MOBILE)
                .addArgument(KEY_USER_NAME, username)
                .addArgument(KEY_PASSWORD, password)
		        .addArgument(KEY_AUTH_CODE, authCode)
		        .addArgument(KEY_AUTH_KEY, authKey);
    }
    
    /**
     * 登录
     * @param username username
     * @param password password
     * @return Request
     */
    public static Request<UserResult> login(String username, String password) {
        return new PostMethodRequest(UserResult.class, TTUS_HOST, METHOD_LOGIN)
                .addArgument(KEY_USER_NAME, username)
                .addArgument(KEY_PASSWORD, password);
    }

    /**
     * 检查邮箱是否存在
     * @param userName userName
     * @return Request
     */
    public static Request<BaseResult> checkEmail(String userName) {
        return new PostMethodRequest(BaseResult.class, TTUS_HOST, METHOD_CHECK_EMAIL)
                .addArgument(KEY_USER_NAME, userName);
    }
    //原cloudapi.jar包中的方法 end

    /**
     * 送礼记录接口，只查询最近一个月的记录
     *
     * @param accessToken accessToken
     * @param page      page
     * @param size      size
     * @param latestTime 最后一次TimeStamp
     * @return 上传Request
     */
    public static Request<ReceiveSendGiftRecordResult> sendGiftLog(String accessToken, int page, int size, long latestTime) {
        final int daysMonth = 30;
        long currentTime = System.currentTimeMillis() + ConstantUtils.MILLS_PER_DAY;
        return new GetMethodRequest<ReceiveSendGiftRecordResult>(ReceiveSendGiftRecordResult.class, Config.getAPIHost(), METHOD_COST_LOG)
                .addUrlArgument(accessToken)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_START_TIME, DateUtils.formatDate(currentTime - ConstantUtils.MILLS_PER_DAY * daysMonth, DateUtils.FORMAT_TO_DAY, "-"))
                .addArgument(KEY_END_TIME, DateUtils.formatDate(currentTime, DateUtils.FORMAT_TO_DAY, "-"))
                .addArgument(KEY_COST_TYPE, COST_TYPE_SEND_GIFT)
                .addArgument(KEY_LEND, latestTime)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
       
    }

    /**
     * 收礼记录接口
     *
     * @param accessToken accessToken
     * @param page      page
     * @param size      size
     * @return 上传Request
     */
    public static Request<ReceiveSendGiftRecordResult> receiveGiftLog(String accessToken, int page, int size, long stime, long etime) {
        return new GetMethodRequest<ReceiveSendGiftRecordResult>(ReceiveSendGiftRecordResult.class, Config.getAPIHost(), METHOD_RECEIVE_GIFT_LOG)
                .addUrlArgument(accessToken)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_START_TIME, stime)
                .addArgument(KEY_END_TIME, etime)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    //消息API

    /**
     * 我的消息
     * @param accessToken accessToken
     * @param page      page
     * @param size      size
     * @return Request
     */
    public static Request<NoticeListResult> noticeList(String accessToken, int page, int size, int id1, int id2) {
        return new GetMethodRequest<NoticeListResult>(NoticeListResult.class, Config.getAPIHost(), METHOD_MSG_LIST)
                .addUrlArgument(accessToken)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 未读消息数量
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<RemindNoticeUnReadCountResult> unReadNoticeCount(String accessToken) {
        return new GetMethodRequest<RemindNoticeUnReadCountResult>(RemindNoticeUnReadCountResult.class, Config.getAPIHost(), METHOD_UNREAD_COUNT)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 删除消息
     * @param accessToken accessToken
     * @param noticeId noticeId
     * @return Request
     */
    public static Request<BaseResult> delNotice(String accessToken, String noticeId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MSG_DEL)
                .addUrlArgument(accessToken)
                .addUrlArgument(noticeId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 标记消息为已读
     * @param accessToken accessToken
     * @param noticeId noticeId
     * @return Request
     */
    public static Request<BaseResult> markNoticeRead(String accessToken, String noticeId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MSG_MARK_READ)
                .addUrlArgument(accessToken)
                .addUrlArgument(noticeId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 提醒列表
     * @param accessToken accessToken
     * @param page      page
     * @param size      size
     * @return Request
     */
    public static Request<RemindListResult> remindList(String accessToken, int page, int size) {
        return new GetMethodRequest<RemindListResult>(RemindListResult.class, Config.getAPIHost(), METHOD_REMIND_LIST)
                .addUrlArgument(accessToken)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 未读提醒数量
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<RemindNoticeUnReadCountResult> unReadRemindCount(String accessToken) {
        return new GetMethodRequest<RemindNoticeUnReadCountResult>(RemindNoticeUnReadCountResult.class, Config.getAPIHost(), METHOD_REMIND_UNREAD_COUNT)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 标记提醒为已读
     * @param accessToken accessToken
     * @param remindId msgId
     * @return Request
     */
    public static Request<BaseResult> markRemindRead(String accessToken, String remindId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_REMIND_MARK_READ)
                .addUrlArgument(accessToken)
                .addUrlArgument(remindId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 删除提醒
     * @param accessToken accessToken
     * @param remindId remindId
     * @return Request
     */
    public static Request<BaseResult> delRemind(String accessToken, String remindId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_REMIND_DEL)
                .addUrlArgument(accessToken)
                .addUrlArgument(remindId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 设置不再提醒
     * @param accessToken accessToken
     * @param remindId remindId
     * @return Request
     */
    public static Request<BaseResult> noLongerRemind(String accessToken, String remindId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_REMIND_SET)
                .addUrlArgument(accessToken)
                .addUrlArgument(remindId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求用户徽章
     * @param userId userId
     * @return Request
     */
    public static Request<UserBadgeResult> requestBadge(long userId) {
        return new GetMethodRequest<UserBadgeResult>(UserBadgeResult.class, Config.getAPIHost(), METHOD_REQUEST_BADGE)
                .addUrlArgument(userId);
    }

    /**
     * 又拍云上传表单签名
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<UploadTokenResult> accusePhotoToken(String accessToken) {
        return new GetMethodRequest<UploadTokenResult>(UploadTokenResult.class, Config.getAPIHost(), METHOD_ACCUSE_PHOTO_TOKEN)
                .addArgument(KEY_ACCESS_TOKEN, accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }
}
