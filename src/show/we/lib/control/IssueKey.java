package show.we.lib.control;

/**
 * Created by CG on 14-1-3.
 * 关注的主题
 * @author ll
 * @version 3.4.0
 */
public enum IssueKey {

    /**
     * live_activity_destroy
     */
    ON_LIVE_ACTIVITY_DESTROY,

    /**
     * 下载完成的通知
     */
    DOWNLOAD_COMPLETED,

    /**
     * notify_live_activity_finish
     */
    NOTIFY_LIVE_ACTIVITY_FINISH,

    /**
     * 发送礼物
     */
    SELECT_SEND_GIFT,

    /**
     * 发送公聊消息
     */
    PUBLIC_MESSAGE,

    /**
     * 发送私聊消息
     */
    PRIVATE_MESSAGE,

    /**
     * select_gift
     */
    SELECT_GIFT,

    /**
     * send_gift_completed
     */
    SEND_GIFT_COMPLETED,

    /**
     * issue_gift_list
     */
    ISSUE_GIFT_LIST_DIALOG_NOTIFY,

    /**
     * request_star_info_success
     */
    REQUEST_LIVE_STAR_INFO_SUCCESS,

    /**
     * shut_up
     */
    SHUT_UP,

    /**
     * del_favorite_star
     */
    DEL_FAVORITE_STAR,

    /**
     * request_fav_star_success
     */
    REQUEST_FAV_STAR_SUCCESS,

    /**
     * request_fav_star_failed
     */
    REQUEST_FAV_STAR_FAILED,

    /**
     * user_info_update
     */
    USER_INFO_UPDATE,

    /** please_login */
    PLEASE_LOGIN,

    /**
     * issue mission_change
     */
    MISSION_CHANGE,

    /**
     * request mission failed
     */
    REQUEST_MISSION_FAIL,

    /**
     * 每日签到数据改变
     */
    DAILY_SIGN_LIST_RESULT,

    /**
     * add_following_success
     */
    ADD_FOLLOWING_SUCCESS,

    /**
     * add_following_fail
     */
    ADD_FOLLOWING_FAIL,

    /**
     * cancel_focus_star_success
     */
    CANCEL_FOCUS_STAR_SUCCESS,

    /**
     * cancel_focus_star_fail
     */
    CANCEL_FOCUS_STAR_FAIL,

    /**
     * upload_user_info_success
     */
    UPLOAD_USER_INFO_SUCCESS,

    /**
     * upload_user_info_fail
     */
    UPLOAD_USER_INFO_FAIL,

    /**
     * song_order_request
     */
    SONG_ORDER_REQUEST,

    /**
     * close_live_menu
     */
    CLOSE_LIVE_MENU,

    /**
     * feather_notify
     */
    MESSAGE_PARSE_FEATHER_NOTIFY,

    /**
     * gift_animation_notify
     */
    MESSAGE_PARSE_GIFT_ANIMATION_NOTIFY,

    /**
     * open_room_live
     */
    MESSAGE_PARSE_OPEN_ROOM_LIVE,

    /**
     * close_room_live
     */
    MESSAGE_PARSE_CLOSE_ROOM_LIVE,

    /**
     * marquee_notify
     */
    MESSAGE_PARSE_MARQUEE_NOTIFY,

    /**
     * puzzle_begin
     */
    MESSAGE_PARSE_PUZZLE_BEGIN,

    /**
     * puzzle_win
     */
    MESSAGE_PARSE_PUZZLE_WIN,

    /**
     * live_broadcast_notify
     */
    MESSAGE_PARSE_LIVE_BROADCAST_NOTIFY,

    /**
     * live_sofa_notify
     */
    MESSAGE_PARSE_LIVE_SOFA_NOTIFY,

    /**
     * receive_new_private_message
     */
    MESSAGE_PARSE_RECEIVE_NEW_PRIVATE_MESSAGE,

    /**
     * receive_public_message
     */
    MESSAGE_PARSE_RECEIVE_PUBLIC_MESSAGE,

    /**
     * message_parse_god_of_wealth_message
     */
    MESSAGE_PARSE_GOD_OF_WEALTH_MESSAGE,

    /**
     * chat_window_integrated_message
     */
    CHAT_WINDOW_INTEGRATED_MESSAGE,

    /**
     * audience_count_change
     */
    AUDIENCE_COUNT_CHANGE,

    /**
     * web_socket_reconnect
     */
    WEB_SOCKET_RECONNECT,

    /**
     * user_upgrade
     */
    USER_UPGRADE,

    /**
     * rank_id_changed
     */
    RANK_ID_CHANGED,

    /**
     * FAMILY_PAGE_TYPE_CHANGE
     */
    FAMILY_TYPE_CHANGE,

    /**
     * my family data rquested
     */
    MY_FAMILY_DATA_CHANGED,

    /**
     * FAMILY_DETAILS_TYPE_CHANGE
     */
    FAMILY_DETAILS_TYPE_CHANGE,

    /**
     * load_image
     */
    LOAD_IMAGE,

    /**
     * clear_image
     */
    CLEAR_IMAGE,

    /**
     * start_record
     */
    START_RECORD,

    /**
     * end_record
     */
    END_RECORD,

    /**
     * get_feather_success
     */
    GET_FEATHER_SUCCESS,

    /**
     * send_feather_failure
     */
    SEND_FEATHER_FAILURE,

    /**
     * gift_shape_animation_notify
     */
    GIFT_SHAPE_ANIMATION_NOTIFY,

    /**
     * 关闭软键盘
     */
    INPUT_METHOD_CLOSED,

    /**
     * 打开软键盘
     */
    INPUT_METHOD_OPENED,

    /**
     * 聊天区域点击
     */
    LIVE_CENTER_CLICK,

    /**
     * 主播升级
     */
    STAR_UPGRADE,

    /**
     * 恢复禁言
     */
    RECOVER_SHUT_UP,

    /**
     * bell_clock_start
     */
    BELL_CLOCK_START,

    /**
     * bell_clock_stop
     */
    BELL_CLOCK_STOP,

    /**
     * 在直播间Activity里切换主播
     */
    SWITCH_STAR_IN_LIVE,

	/** 点击切换粉丝页面 */
	CLICK_TO_SWITCH_FANS,

	/** 粉丝页面切换 */
	FANS_PAGE_CHANGE,

	/** 点击切换聊天页面 */
	CHAT_PAGE_CHANGE,

    /** 直播间Activity执行OnResume **/
    LIVE_ACTIVITY_RESUME,

    /** 直播间Activity执行OnPause **/
    LIVE_ACTIVITY_PAUSE,

    /** 视频方向比例 */
    VIDEO_ASPECT_RATIO,

    /** 视频流地址请求完成 **/
    VIDEO_STREAM_REQUEST_FINISH,

    /**
     * 提醒菜单
     */
    REMIND_MENU,

    /** 话题菜单 */
    TOPIC_MENU,

    /** 本房间摇奖中奖消息 */
    SLOT_WIN_ROOM,

    /**
     * 家族话题有新回复
     */
    FAMILY_NEW_COMMENT_REPLY,

    /**
     * 最近看过的主播排序完成
     */
    SORT_RECENTLY_STAR,
    /**
     * 送礼消息推送
     */
    MESSAGE_PARSE_GIFT_NOTIFY,
    /**
     * 宝藏中奖信息
     */
    MESSAGE_PARSE_TREASURE_ROOM,
    /**
     * 请求公告文字列表成功
     */
    REQUEST_NOTICE_LIST_SUCCESS
}
