package show.we.lib.widget.chat.spannable_string;

import android.content.Context;
import android.text.SpannableStringBuilder;
import show.we.lib.R;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.UserInfoUtils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class CommonData {

    /**
     * SPACE_SEPARATION
     */
    protected static final String SPACE_SEPARATION = "  ";

    /**
     * 悄悄的对
     */
    protected static String mPrivateToSeparation;
    /**
     * 说:
     */
    protected static String mSaidSeparation;
    /**
     * 购买了
     */
    protected static String mBuySeparation;
    /**
     * 获得了
     */
    protected static String mWinSeparation;
    /**
     * 被
     */
    protected static String mBySeparation;
    /**
     * %d倍
     */
    protected static String mXTime;
    /**
     * 倍返奖
     */
    protected static String mGrandPrice;
    /**
     * 进入房间
     */
    protected static String mInRoom;
    /**
     * 徒步
     */
    protected static String mWalk;
    /**
     * 骑着
     */
    protected static String mRide;
    /**
     * 踢出房间
     */
    protected static String mKick;
    /**
     * 禁言
     */
    protected static String mShutUp;
    /**
     * 分钟
     */
    protected static String mMinute;
    /**
     * 主播
     */
    protected static String mStar;
    /**
     * 获得主播同意
     */
    protected static String mStarAgreed;
    /**
     * 拒绝了 您 点播的歌曲:
     */
    protected static String mStarRefused;
    /**
     * 点播的歌曲:
     */
    protected static String mOrderedSong;
    /**
     * [官方公告]
     */
    protected static String mktv;
    /**
     * 官方网址
     */
    protected static String mktvNetAddress;
    /**
     * 注册
     */
    protected static String mRegister;
    /**
     * 登录
     */
    protected static String mLogin;
    /**
     * 充值
     */
    protected static String mRecharge;
    /**
     * 送给
     */
    protected static String mPresent;
    /**
     * 个
     */
    protected static String mUnit;
    /**
     * 对
     */
    protected static String mToSeparation;

	/**
	 * 进场信息、所有辅助文字
	 */
	protected static int mGrayColor;

	/**
	 * 发言内容、公告内容
	 */
	protected static int mBlackColor;

	/**
	 * 发言用户昵称、所有链接
	 */
	protected static int mBlueColor;

	/**
	 * 送礼信息、中奖信息
	 */
	protected static int mYellowColor;


    /**
     * 为
     */
    protected static String mFor;

    /**
     * 大家
     */
    protected static String mAllUsers;

	/**
	 * 至尊VIP用户昵称
	 */
	protected static int mPurpleColor;

	/**
	 * 公告中ktv
	 */
	protected static int mRedColor;

    /**
     * 您
     */
    protected static String mYou;

    /**
     * 玩幸运大摇杆喜中
     */
    protected static String mSlotWin;

    /**
     * 一根羽毛
     */
    protected static String mOneFeather;

	/** 用户id */
	protected static long mMyId;

    private static boolean mIsInit;

    /**
     * Context
     */
    protected Context mContext;
    /**
     * mBuilder
     */
    protected SpannableStringBuilder mBuilder;

    /**
     * CommonData
     *
     * @param context context
     */
    public CommonData(Context context) {
        mContext = context;
        if (!mIsInit) {
            mIsInit = true;

            mToSeparation = context.getString(R.string.to_separation);
            mPrivateToSeparation = context.getString(R.string.private_to_separation);
            mSaidSeparation = context.getString(R.string.said_separation);
            mBuySeparation = context.getString(R.string.buy_separation);
            mWinSeparation = context.getString(R.string.win_separation);
            mXTime = context.getString(R.string.x_time);
            mGrandPrice = context.getString(R.string.grand_price);
            mInRoom = context.getString(R.string.in_room);
            mRide = context.getString(R.string.ride);
            mShutUp = context.getString(R.string.shut_up);
            mBySeparation = context.getString(R.string.by_separation);
            mKick = context.getString(R.string.kick);
            mMinute = context.getString(R.string.minute);
            mStar = context.getString(R.string.star);
            mStarAgreed = context.getString(R.string.star_agreed);
            mStarRefused = context.getString(R.string.star_refuse_your_order_song);
            mOrderedSong = context.getString(R.string.ordered_song);
            mktv = context.getString(R.string.ktv);
            mktvNetAddress = context.getString(R.string.ktv_net_address);
            mRegister = context.getString(R.string.register);
            mLogin = context.getString(R.string.login);
            mRecharge = context.getString(R.string.recharge);
            mPresent = context.getString(R.string.present);
            mUnit = context.getString(R.string.unit_with_space);
            mYou = context.getString(R.string.you);
            mWalk = context.getString(R.string.walk);
            mFor = context.getString(R.string.forfor);
            mAllUsers = context.getString(R.string.all_users);
            mSlotWin = context.getString(R.string.slot_win);
            mOneFeather = context.getString(R.string.one_feather);
            if (LoginUtils.isAlreadyLogin()) {
				mMyId = UserInfoUtils.getUserInfo().getData().getId();
            }

			mGrayColor = context.getResources().getColor(R.color.chat_enter_room);
			mBlackColor = context.getResources().getColor(R.color.chat_announcement);
            mBlueColor = context.getResources().getColor(R.color.chat_nickname_linked);
            mYellowColor = context.getResources().getColor(R.color.chat_gift_message);
            mPurpleColor = context.getResources().getColor(R.color.chat_super_user_name);
            mRedColor = context.getResources().getColor(R.color.chat_official);
        }
    }

    /**
     * getSpan
     *
     * @return SpannableStringBuilder
     */
    public SpannableStringBuilder getSpan() {
        return mBuilder;
    }
}
