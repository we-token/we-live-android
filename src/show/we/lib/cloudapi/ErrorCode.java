package show.we.lib.cloudapi;

/**
 * Created by Administrator on 13-7-24.
 *
 * @author ll
 * @version 1.0.0
 */
public class ErrorCode {

    /**
     * result返回Code，没有足够的金币
     */
    public static final int NOT_ENOUGH_MONEY = 30412;

    /**
     * 注册太频繁
     */
    public static final int REGISTER_TOO_FREQUENT = 30423;

    /**
     * 验证码错误
     */
    public static final int AUTH_CODE_ERROR = 30419;

    /**
     * 用户被冻结
     */
    public static final int FREEZE_USER = 30418;

    /**
     * 恶意访问，ip，设备被禁
     */
    public static final int FREEZE_DEVICE = 30421;

    /**
     * token 失效
     */
    public static final int ACCESS_TOKEN_EXPIRED = 30405;

    /**
     * 需要验证邮箱
     */
    public static final int ERROR_NEED_VARIFY_EMAIL = 30444;

    /**
     * 需要VIP会员要求
     */
    public static final int ERROR_NEED_VIP = 30422;

    /**
     * 权限不足
     */
    public static final int PERMISSION_DENIED = 30413;

    /**
     * 已提交申请，审核中
     */
    public static final int VERIFYING = 30443;

    /**
     * 已经创建了一个
     */
    public static final int ALREADY_CREATED = 30442;

    /**
     * 授权失败
     */
    public static final int ERROR_AUTHENTICATION = 30301;
    /**
     * 用户名或密码错误
     */
    public static final int ERROR_USERNAME_OR_PASSWORD = 30302;
    /**
     * 用户名不存在
     */
    public static final int ERROR_USERNAME_EXIST = 30308;

    /**
     * ERROR_VALUE_EXIST
     */
    public static final int ERROR_VALUE_EXIST = 30307;
}
