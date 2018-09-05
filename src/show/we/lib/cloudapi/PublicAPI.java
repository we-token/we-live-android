package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.PostMethodRequestV2;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.config.Enums;
import show.we.lib.config.Enums.BannerType;
import show.we.lib.model.*;

/**
 * 一些公用的API杂项
 *
 * @author ll
 * @version 1.0.0
 */
public final class PublicAPI {

    /**
     * 默认请求的page大小 *
     */
    public static final int ROOM_LIST_PAGE_SIZE = 50;

    /**
     * 默认请求的page大小 *
     */
    public static final int PHOTO_PAGE_SIZE = 60;
    /**
     * 默认加载更多请求的page大小 *
     */
    public static final int LOAD_MORE_PHOTO_SIZE = 30;

    private static final int BEAN_COUNT_RED_STAR_END = 106000;
    private static final int BEAN_COUNT_BRIGHT_STAR_END = 561000;
    private static final int BEAN_COUNT_GIANT_STAR_END = 1616000;

    private static final String METHOD_BROKER_LIST = "public/broker_list";

    private static final String METHOD_ROOM_LIST = "public/room_list";
    private static final String METHOD_ROOM_FOUND_LATEST = "public/room_found_latest";
    private static final String METHOD_ROOM_LIVE_LATEST = "public/room_live_latest";

    private static final String METHOD_TIME_STAMP = "public/t_hex";
    private static final String METHOD_SEND_BROADCAST = "room/broadcast";
    private static final String METHOD_ROOM_TOKEN = "room/voice_token";
    private static final String METHOD_DAY_LOGIN = "user/day_login";
    private static final String METHOD_GET_BANNER = "public/poster";
    private static final String METHOD_VISITOR_COUNT = "public/visiter_count";
    private static final String METHOD_FEEDBACK = "public/feedback";
    private static final String METHOD_STAR_PHOTO = "zone/photo_list";
    private static final String METHOD_SOFA_LIST = "public/room_sofa";
//    private static final String METHOD_VIDEO_STREAM_URL = "public/http_pull_url";
    private static final String METHOD_VIDEO_STREAM_URL = "roommanage/streamUrl";
    private static final String METHOD_RECOMMEND_APP_URL = "recommend";
    private static final String METHOD_INCR_DOWN_COUNT = "incr_down_count";
    private static final String METHOD_ROOM_TAG = "public/room_tag";
    private static final String METHOD_SENSITIVE_WORD = "public/blackword_list/1";
    private static final String METHOD_KEY_WORD = "public/blackword_list/0";
    private static final String METHOD_PUBLIC_INFORM = "public/inform?type=2";
    private static final String METHOD_STAR_STATUS = "public/room_by_ids";
    private static final String METHOD_ID_NAME = "public/id_name";
    private static final String METHOD_PROPERTIES_LIST = "properties/list";


    private static final String KEY_SORT = "sort";
    private static final String KEY_PAGE = "page";
    private static final String KEY_SIZE = "size";
    private static final String KEY_SBEAN = "sbean";
    private static final String KEY_EBEAN = "ebean";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_HORN = "horn";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_PHOTO_TYPE = "type";
    private static final String KEY_APPID = "id";
    private static final String KEY_NICK_NAME = "nick_name";
    private static final String KEY_ROOM_ID = "room_id";
    private static final String KEY_ROOMS_ID = "ids";
    
    /**
     * 根据ID获取用户昵称
     *
     * @param accessToken accessToken
     * @return Http请求实例
     */
    public static Request<BaseResult> idName(String accessToken) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_ID_NAME)
                .addUrlArgument(accessToken);
    }
                
    /**
     * 全部主播(和pc端相同)
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> allStarRoomList(int page, int size, int sbean, int ebean) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_SORT, 0)
                .addArgument(KEY_PAGE, page == 0 ? 0 : page)
                .addArgument(KEY_SIZE, size == 0 ? 0 : size)
		        .addArgument(KEY_SBEAN, sbean == 0 ? 0 : sbean)
		        .addArgument(KEY_EBEAN, ebean == 0 ? 0 : ebean);
    }

    /**
     * 人气主播
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> recommendRoomList(int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 新来的主播
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> newRoomList( int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_FOUND_LATEST)
//                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 最近开播
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> latestRoomList(int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIVE_LATEST)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 按等级排序
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> allRankStarRoomList(int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_SORT, 3);
    }

    /**
     * 超星房间列表
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> superStarRoomList(int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_SBEAN, BEAN_COUNT_GIANT_STAR_END)
                .addArgument(KEY_EBEAN, Integer.MAX_VALUE);
    }

    /**
     * 巨星房间列表
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> giantStarRoomList(int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_SBEAN, BEAN_COUNT_BRIGHT_STAR_END)
                .addArgument(KEY_EBEAN, BEAN_COUNT_GIANT_STAR_END);
    }

    /**
     * 明星房间列表
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> brightStarRoomList(int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_SBEAN, BEAN_COUNT_RED_STAR_END)
                .addArgument(KEY_EBEAN, BEAN_COUNT_BRIGHT_STAR_END);
    }

    /**
     * 红星房间列表
     *
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> redStarRoomList(int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_SBEAN, 0)
                .addArgument(KEY_EBEAN, BEAN_COUNT_RED_STAR_END);
    }

    /**
     * 请求代理人的列表
     *
     * @return Http请求实例
     */
    public static Request<BrokerListResult> brokerList() {
        return new PostMethodRequestV2<BrokerListResult>(BrokerListResult.class, Config.getAPIHost(), METHOD_BROKER_LIST);
    }

    /**
     * 请求一个时间戳
     *
     * @return Http请求实例
     */
    public static Request<TimeStampResult> timeStamp() {
        return new GetMethodRequest<TimeStampResult>(TimeStampResult.class, Config.getAPIHost(), METHOD_TIME_STAMP);
    }

    /**
     * 请求发送广播
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param content     广播内容
     * @return Http请求实例
     */
    public static Request<BaseResult> sendBroadcast(String accessToken, long roomId, String content) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SEND_BROADCAST)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addArgument(KEY_CONTENT, content)
                .addArgument(KEY_HORN, 1)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }


    /**
     * 每日登陆请求
     *
     * @param accessToken accessToken
     * @return Http请求实例
     */
    public static Request<BaseResult> loginStatistics(String accessToken) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_DAY_LOGIN)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"))
                .addArguments(EnvironmentUtils.GeneralParameters.parameters());
    }

    /**
     * 请求获取首页banner信息
     *
     * @param bannerType MAIN_BANNER_TYPE | RECHARGE_BANNER_TYPE
     * @return banner实例
     */
    public static Request<BannerResult> getBanner() {
        return new GetMethodRequest<BannerResult>(BannerResult.class, Config.getAPIHost(), METHOD_GET_BANNER);
                
    }

    /**
     * 获取服务器签名
     *
     * @param accessToken accessToken
     * @param fileLength  文件长度
     * @param duration    录音时长
     * @return secretKey
     */
    public static Request<SecretKeyResult> secretKey(String accessToken, long fileLength, int duration) {
        return new GetMethodRequest<SecretKeyResult>(SecretKeyResult.class, Config.getAPIHost(), METHOD_ROOM_TOKEN)
                .addUrlArgument(accessToken)
                .addUrlArgument(fileLength)
                .addUrlArgument(duration)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 获取请求在线数量的接口
     *
     * @return 请求在线数量的接口
     */
    public static Request<OnlineVisitorCountResult> requestOnlineVisitorCount() {
        return new GetMethodRequest<OnlineVisitorCountResult>(OnlineVisitorCountResult.class, Config.getAPIHost(), METHOD_VISITOR_COUNT);
    }

    /**
     * 获取用户反馈接口
     *
     * @param contact 联系方式
     * @param content 反馈内容
     * @return 用户反馈接口
     */
    public static Request<BaseResult> userAdvice(String contact, String content) {
        return new PostMethodRequestV2<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FEEDBACK)
                .addArgument(KEY_CONTACT, contact)
                .addArgument(KEY_CONTENT, content)
                .addArguments(EnvironmentUtils.GeneralParameters.parameters());
    }

    /**
     * 获取所有主播照片列表
     *
     * @param starId starId
     * @param page   page
     * @param size   size
     * @return Request
     */
    public static Request<StarPhotoListResult> allStarPhoto(long starId, int page, int size) {
        return new GetMethodRequest<StarPhotoListResult>(StarPhotoListResult.class, Config.getAPIHost(), METHOD_STAR_PHOTO)
                .addUrlArgument(starId)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 获取主播照片列表
     *
     * @param starId        主播id
     * @param starPhotoType 照片类型:STAR_PHOTO_TYPE.LIVE / STAR_PHOTO_TYPE.LIFE
     * @param page          页数
     * @param size          大小
     * @return Request
     */
    public static Request<StarPhotoListResult> starPhotoList(long starId, Enums.StarPhotoType starPhotoType, int page, int size) {
        return new GetMethodRequest<StarPhotoListResult>(StarPhotoListResult.class, Config.getAPIHost(), METHOD_STAR_PHOTO)
                .addUrlArgument(starId)
                .addArgument(KEY_PHOTO_TYPE, starPhotoType.getValue())
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 获取某个房间的沙发列表
     *
     * @param roomId 房间id
     * @return Request
     */
    public static Request<SofaListResult> sofaList(long roomId) {
        return new GetMethodRequest<SofaListResult>(SofaListResult.class, Config.getAPIHost(), METHOD_SOFA_LIST)
                .addUrlArgument(roomId);
    }

    /**
     * 获取房间视频流地址
     *
     * @param roomId 房间id
     * @return Request
     */
    public static Request<VideoStreamUrlResult> videoStreamUrl(long roomId) {
        return new GetMethodRequest<VideoStreamUrlResult>(VideoStreamUrlResult.class, Config.getAPIHost(), METHOD_VIDEO_STREAM_URL)
        		.addArgument("roomId",roomId);
    }

    /**
     * 增加一次该应用下载次数
     *
     * @param appId 应用id
     * @return BaseResult
     */
    public static Request<BaseResult> addDownloadCount(int appId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getMarketAPIHost(), METHOD_INCR_DOWN_COUNT)
                .addArgument(KEY_APPID, appId);
    }

    /**
     * 请求搜索结果by关键词
     *
     * @param key  关键词
     * @param page 当前请求页码
     * @param size 每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> searchResultListByKey(String key, int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_NICK_NAME, key)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 请求搜索结果by直播间ID
     *
     * @param roomId 房间id
     * @param page   当前请求页码
     * @param size   每页大小
     * @return Http请求实例
     */
    public static Request<RoomListResult> searchResultListByRoomId(long roomId, int page, int size) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_LIST)
                .addArgument(KEY_ROOM_ID, roomId)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 请求搜索结果by印象标签ID
     *
     * @param tagId 标签id
     * @return Http请求实例
     */
    public static Request<RoomListResult> searchResultListByTagID(int tagId) {
        return new GetMethodRequest<RoomListResult>(RoomListResult.class, Config.getAPIHost(), METHOD_ROOM_TAG)
                .addUrlArgument(tagId);
    }

    /**
     * 请求敏感词
     * @return Http请求实例
     */
    public static Request<SensitiveWordResult> requestSensitiveWord() {
        return new GetMethodRequest<SensitiveWordResult>(SensitiveWordResult.class, Config.getAPIHost(), METHOD_SENSITIVE_WORD);
    }

    /**
     * 请求违禁词
     * @return Http请求实例
     */
    public static Request<KeyWordResult> requestKeyWord() {
        return new GetMethodRequest<KeyWordResult>(KeyWordResult.class, Config.getAPIHost(), METHOD_KEY_WORD);
    }

    /**
     * 请求公告内容
     * @return Http请求实例
     */
    public static Request<PublicInformResult> requestPublicInform() {
        return new GetMethodRequest<PublicInformResult>(PublicInformResult.class, Config.getAPIHost(), METHOD_PUBLIC_INFORM);
    }

    /**
     * 请求最近看过的主播状态
     * @param roomsId roomsId
     * @return Http请求实例
     */
    public static Request<UsersStatusResult> requestUsersResult(String roomsId) {
        return new GetMethodRequest<UsersStatusResult>(UsersStatusResult.class, Config.getAPIHost(), METHOD_STAR_STATUS)
                .addArgument(KEY_ROOMS_ID, roomsId);
    }

    /**
     * 请求一个接口列表，包括公告、投诉等内容
     * @return Http请求实例
     */
    public static Request<PropertiesListResult> requestPropertiesList() {
        return new GetMethodRequest<PropertiesListResult>(PropertiesListResult.class, Config.getAPIHost(), METHOD_PROPERTIES_LIST);
    }
}
