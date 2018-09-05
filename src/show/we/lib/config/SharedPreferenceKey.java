package show.we.lib.config;

/**
 * 需要保存信息的配置文件
 *
 * @author ll
 * @version 1.0.0
 */
public class SharedPreferenceKey {
    /**
     * 主题
     */
    public static final String THEME = "theme";
    /**
     * 透明主题
     */
    public static final String THEME_TRANSPARENT = "theme_transparent";
    /**
     * 左滑关闭主题
     */
    public static final String THEME_SLIDEBACK = "theme_slideback";
    /**
     * 搜索页面主题
     */
    public static final String THEME_WINDOW_OVERLAY = "theme_window_overlay";
    /**
     * token内容
     */
    public static final String ACCESS_TOKEN_OLD_KEY = "access_token";

    /**
     * token内容
     */
    public static final String ACCESS_TOKEN_KEY = "encrypted_access_token";

    /**
     * 用户名
     */
    public static final String USER_EMAIL_KEY = "user_name";

    /**
     * 收藏的主播上线提醒
     */
    public static final String FAVORITE_STAR_ONLINE_HINT_KEY = "favorite_star_online_hint";

    /**
     * 上次检查更新的时间
     */
    public static final String PREVIOUS_CHECK_UPDATE_TIME_KEY = "previous_check_update_time";

    /**
     * 是否需要更新
     */
    public static final String IS_NEED_UPDATE = "is_need_update";

    /**
     * 上次链接服务器类型
     */
    public static final String LAST_CONNECT_SERVER_TYPE_KEY = "last_connect_server_type_key";

    /**
     * 上次登录的日期
     */
    public static final String LATEST_LOGIN_REQUEST_DATE = "latest_login_request_time";

    /**
     * 一天内登录的用户信息
     */
    public static final String LOGIN_USERS_INFO = "login_user_info";

    /**
     * 创建快捷图标
     */
    public static final String HAS_SHORTCUT = "has_shortcut";

    /**
     * 非Wi-Fi模式下 是否已经做了提示
     */
    public static final String WIFI_TIP_DONE = "wifi_tip_done";

    /**
     * 授权信息
     */
    public static final String AUTHORIZE_INFO = "authorize_info";

    /**
     * 显示主页面新手引导 *
     */
    public static final String SHOW_MAIN_PAGE_NEW_GUIDE = "show_main_page_new_guide";

    /**
     * 显示直播页新手引导 *
     */
    public static final String SHOW_LIVE_PAGE_NEW_GUIDE = "show_live_page_new_guide";

    /**
     * 家族列表的数量
     */
    public static final String FAMILY_LIST_SIZE = "family_list_size";

    /**
     * 广播跑道
     */
    public static final String SHOW_BROADCAST_MARQUEE = "show_broadcast_marquee";

    /**
     * 礼物跑道
     */
    public static final String SHOW_GIFT_MARQUEE = "show_gift_marquee";

    /**
     * 进场信息
     */
    public static final String SHOW_ENTER_MESSAGE = "show_enter_message";

    /**
     * 送礼是否上跑道
     */
    public static final String SEND_GIFT_MARQUEE = "send_gift_marquee";

    /** 当前一天的时间数值 **/
    public static final String DAY_TIME_MILLS_KEY = "day_time_mills_key";
}
