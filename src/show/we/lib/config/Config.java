package show.we.lib.config;

import android.content.Context;
import android.widget.Toast;

import show.we.sdk.util.ConstantUtils;
import show.we.sdk.util.EnvironmentUtils;
import show.we.sdk.util.SecurityUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.utils.DisplayUtils;

import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * 通用的设置信息
 *
 * @author ll
 * @version 1.0.0
 */
public class Config {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "show.db";
    /**
     * 版本 1.2
     */
    public static final int DB_VERSION_V_1_2 = 1;
    /**
     * 版本 1.3
     */
    public static final int DB_VERSION_V_1_3 = 16777216; //TaskInfo.DB_VERSION;
    /**
     * 版本 1.4
     */
    public static final int DB_VERSION_V_1_4 = DB_VERSION_V_1_3 + 1;
    /**
     * 版本 1.6
     */
    public static final int DB_VERSION_V_1_6 = DB_VERSION_V_1_4 + 1;


    /**
     * 测试服务器 WS
     */
    public static final int TEST_WS = 0;
    /**
     * 正式服务器 WS
     */
    public static final int FORMAL_WS = 1;

    private static int sConnectServerType = FORMAL_WS;

    /**
     * 清除七天前的图片缓存数据
     */
    public static final long CLEAR_CACHE_INTERVAL = 7 * ConstantUtils.MILLS_PER_DAY; //一周

    /**
     * 图片缓存占用这个APP的内存比例
     */
    public static final float IMAGE_CACHE_MEM_PERCENT = 0.05f;

    /**
     * 对象缓存占用这个APP的内存比例
     */
    public static final float OBJECT_CACHE_MEM_PERCENT = 0.05f;

    /**
     * 退出倒计时
     */
    public static final long EXIT_COUNT_DOWN = 5 * ConstantUtils.THOUSAND;

    /**
     * 最大累积羽毛数量
     */
    public static final int MAX_FEATHER_COUNT = 100;

    /**
     * 图标宽度
     */
    public static final int COMMON_ICON_WIDTH_DP = 48;
    /**
     * 图标高度
     */
    public static final int COMMON_ICON_HEIGHT_DP = 48;

    /**
     * 宽屏最低dip值
     */
    public static final float LARGE_SCREEN_DIPS = 480;

    private static String sCacheFolderPath;

    private static boolean sIsLargeScreen = false;

    private static boolean sSendGiftMarquee = true;

    private static String sWebSocketIP = "";

    private static final String IMAGE_CACHE_FOLDER_NAME = "image";
    private static final String GIF_CACHE_FOLDER_NAME = "gif";
    private static final String MARKET_APP_CACHE_FOLDER_NAME = "marketApp";
    private static final String PAY_APP_FOLDER_NAME = "payApp";
    private static final String TENPAY_APP_NAME = "tenpay.apk";
    private static final String RING_CACHE_FOLDER_NAME = "ring";
    private static final String OBJECT_CACHE_FOLDER_NAME = "object";
    private static final String RECORDER_CACHE_FOLDER_NAME = "recorder";
    /**
     * 图片后缀名 *
     */
    public static final String FILE_EXTENSION_JPG = ".jpg";

    private static final String TEST_API_HOST = "http://172.16.2.25:8080"; //http://test.api.ttxiu.com";
    private static final String FORMAL_API_HOST = "http://api.51weibo.com";//"http://api.ttxiu.com";

    private static final String FORMAL_MARKET_API_HOST = "http://api.busdh.com/market-api/beauty";
    private static final String TEST_MARKET_API_HOST = "http://api.busdh.com/market-api/beauty";//"http://192.168.1.72:9020/market-api/beauty";

    // SocketIOProxy
    private static final String TEST_WEB_SOCKET_WS_URL = "http://ws.51weibo.tv" ; //http://test.ws.51weibo.com:7000";
    //聊天地址
    private static final String FORMAL_WEB_SOCKET_WS_URL = "http://ws.51weibo.com:80";//"http://ws.show.dongting.com:80";

    private static final String TEST_VIDEO_STREAM_URL = "http://api.51weibo.tv";//"rtmp://rtmp.51weibo.com/show/";//"rtmp://show.dl.ttxiu.com/ktv/";
    private static final String QQ_UNION_LOGIN_URL = "http://ttus.51weibo.com/thirdlogin/qqForAndroid"; //http://ttus.51weibo.com/third/login/qq

    private static final String TEST_VIDEO_RTMP_URL = "http://api.51weibo.com/live/id";//"rtmp://live.ws.51weibo.com/";
    private static final String TEST_VIDEO_RTMP_SECRET_KEY = "f4_d0s3gp_zfir5jr3qwxv19";


    private static final String FORMAL_AUTH_CODE_PIC_URL = "http://api.51weibo.com/user/authcode/";

    private static final String TEST_AUTH_CODE_PIC_URL = "http://api.51weibo.tv/user/authcode/";
    private static final String TEST_RECEIVE_URL = "http://union.51weibo.com/api/receive";
    
  private static final String TTUS_URL = "http://ttus.51weibo.com"; 
//    private static final String TTUS_URL = "http://172.16.2.25:8086";
//    private static final String TTUS_URL = "http://172.16.2.223:8082";
  
    /**
     * 初始化
     *
     * @param context context
     */
    public static void init(Context context) {
        sCacheFolderPath = EnvironmentUtils.Storage.getCachePath(context);
        if (sConnectServerType == FORMAL_WS) {
            new ResolveSocketDNSThread().start();
        }
    }

    /**
     * 设置是否连接测试服务器
     *
     * @param connectServerType 链接服务器的类型
     */
    public static void setConnectToTestServer(int connectServerType) {
        sConnectServerType = connectServerType;
    }

    /**
     * 是否连接测试服务器
     *
     * @return true表示连接测试服务器
     */
    public static int getConnectToServerType() {
        return sConnectServerType;
    }

    /**
     * 获取缓存文件夹路径
     *
     * @return 路径名
     */
    public static String getCacheFolderPath() {
        return sCacheFolderPath;
    }

    /**
     * 获取图片缓存路径
     *
     * @return 图片缓存路径
     */
    public static String getImageCacheFolderPath() {
        return sCacheFolderPath + File.separator + IMAGE_CACHE_FOLDER_NAME;
    }

    /**
     * 获取对象缓存的路径
     *
     * @return 对象缓存路径
     */
    public static String getObjectCacheFolderPath() {
        return sCacheFolderPath + File.separator + OBJECT_CACHE_FOLDER_NAME;
    }

    /**
     * 获取gif缓存路径
     *
     * @return gif缓存路径
     */
    public static String getGifCacheFolderPath() {
        return sCacheFolderPath + File.separator + GIF_CACHE_FOLDER_NAME;
    }
    
    /**
     * 获取市场app缓存路径
     *
     * @return app缓存路径
     */
    public static String getMarketAppCacheFolderPath() {
        return sCacheFolderPath + File.separator + MARKET_APP_CACHE_FOLDER_NAME;
    }

    /**
     * 获取支付app缓存路径
     *
     * @return app缓存路径
     */
    public static String getPayAppCacheFolderPath() {
        return sCacheFolderPath + File.separator + PAY_APP_FOLDER_NAME;
    }

    /**
     * 获取财付通apk文件名
     *
     * @return 财付通apk文件名
     */
    public static String getTenpayApkName() {
        return TENPAY_APP_NAME;
    }

    /**
     * 获取铃声的缓存路径
     *
     * @return 铃声的缓存路径
     */
    public static String getRingCacheFolderPath() {
        return sCacheFolderPath + File.separator + RING_CACHE_FOLDER_NAME;
    }

    /**
     * 图片存储路径
     *
     * @return String
     */
    public static String getPictureFolderPath() {
        return sCacheFolderPath + File.separator + IMAGE_CACHE_FOLDER_NAME;
    }

    /**
     * 获取API HOST
     *
     * @return API HOST
     */
    public static String getAPIHost() {
        return (sConnectServerType == FORMAL_WS) ? FORMAL_API_HOST : TEST_API_HOST;
    }

    /**
     * 获取推荐App API HOST
     *
     * @return API HOST
     */
    public static String getMarketAPIHost() {
        return (sConnectServerType == FORMAL_WS) ? FORMAL_MARKET_API_HOST : TEST_MARKET_API_HOST;
    }

    /**
     * 获取QQ互联登陆URL
     *
     * @return API HOST
     */
    public static String getQQLoginAPIHost() {
        return QQ_UNION_LOGIN_URL;
    }

    /**
     * 获取TTUS HOST
     *
     * @return TTUS HOST
     */
    public static String getTtusHost() {
        return TTUS_URL;
    }
    public static String getSignListHost() {
        return TEST_API_HOST;
    }
    /**
     * 获取WebSocket地址
     *
     * @return WebSocketURL
     */
    public static String getWebSocketUrl() {
//        if (sConnectServerType == TEST_WS) {
//            return TEST_WEB_SOCKET_WS_URL;
//        }
        String tempIP = FORMAL_WEB_SOCKET_WS_URL;
//        synchronized (sWebSocketIP) {
//            tempIP = sWebSocketIP;
//        }
//        if (StringUtils.isEmpty(tempIP)) {
//            new ResolveSocketDNSThread().start();
//            return FORMAL_WEB_SOCKET_WS_URL;
//        }
        return tempIP;
    }

    /**
     * 获取视频播放地址
     *
     * @return 视频URL
     */
    public static String getTestVideoStreamUrl() {
        return TEST_VIDEO_STREAM_URL;
    }

    /**
     * 获取视频播放地址
     *
     * @param timeHex 服务器十六进制时间
     * @param roomId  房间id
     * @return 视频URL
     */
    public static String getTestVideoRtmpStreamUrl(String timeHex, long roomId) {
        StringBuilder sb = new StringBuilder();
        String md5 = SecurityUtils.MD5.get32MD5String(TEST_VIDEO_RTMP_SECRET_KEY + "/ktv/" + roomId + timeHex);
        sb.append(TEST_VIDEO_RTMP_URL).append(roomId).append("?k=").append(md5).append("&t=").append(timeHex);
        return sb.toString();
    }

    /**
     * 获取录音缓存路径
     *
     * @return 录音缓存路径
     */
    public static String getRecorderCacheFolderPath() {
        return sCacheFolderPath + File.separator + RECORDER_CACHE_FOLDER_NAME;
    }

    /**
     * 获取验证码图片url
     *
     * @return 验证码图片url
     */
    public static String getAuthCodePicUrl() {
        return (sConnectServerType == FORMAL_WS) ? FORMAL_AUTH_CODE_PIC_URL : TEST_AUTH_CODE_PIC_URL;
    }

    /**
     * 封面图片宽度
     *
     * @return 封面图片宽度
     */
    public static int getRoomCoverWidth() {
        return DisplayUtils.getWidthPixels() / (isLargeScreen() ? 3 : 2);
    }

    /**
     * @return 封面图片高度
     */
    public static int getRoomCoverHeight() {
        final float ratio = 1.6f;
        return (int) (getRoomCoverWidth() / ratio);
    }

    /**
     * 是否是大屏手机
     *
     * @return true - 是大屏手机
     */
    public static boolean isLargeScreen() {
        return sIsLargeScreen;
    }

    /**
     * 设置是否是大屏手机
     *
     * @param isLargeScreen 是大屏手机
     */
    public static void setIsLargeScreen(boolean isLargeScreen) {
        Config.sIsLargeScreen = isLargeScreen;
    }

    /**
     * 送礼是否上跑道
     * @return 送礼是否上跑道
     */
    public static boolean isSendGiftMarquee() {
        return sSendGiftMarquee;
    }

    /**
     * 设置送礼是否上跑道
     * @param isSendGiftMarquee isSendGiftMarquee
     */
    public static void setIsSendGiftMarquee(boolean isSendGiftMarquee) {
        sSendGiftMarquee = isSendGiftMarquee;
    }

    /**
     * 获取WebSocket Ip from dns
     *
     * @return WebSocketURL
     */
    public static class ResolveSocketDNSThread extends Thread {

        /**
         *
         */
        public ResolveSocketDNSThread() {
            super("DNSInitThread");
        }

        @Override
        public void run() {
            try {
                URL url = new URL(FORMAL_WEB_SOCKET_WS_URL);
                InetAddress address = InetAddress.getByName(url.getHost());
                if (address.getHostAddress() != null) {
                    synchronized (sWebSocketIP) {
                        sWebSocketIP = new StringBuilder(url.getProtocol())
                                .append("://")
                                .append(address.getHostAddress())
                                .append(":")
                                .append(url.getPort())
                                .toString();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }

    }
}
