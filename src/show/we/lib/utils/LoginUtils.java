package show.we.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;
import show.we.sdk.usersystem.Authorize;
import show.we.sdk.usersystem.UserResult;
import show.we.sdk.request.BaseResult;
import show.we.sdk.util.EnvironmentUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.cloudapi.ErrorCode;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.model.UserInfoResult;
import show.we.lib.union.PostUnion;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理的业务过多，所以抽离
 * Created by CG on 13-10-24.
 *
 * @author ll
 * @version 3.0.0
 */
public class LoginUtils {

    /** QQ登录所需要的AppID **/
//    public static final String QQ_UNION_LOGIN_APPID = "100240447";
//	  public static final String QQ_UNION_LOGIN_APPID = "1103180423";
	  public static final String QQ_UNION_LOGIN_APPID = "101155929";

    /**
     * 检查昵称名是否有效
     *
     * @param nickName    昵称名
     * @param context     上下文对象
     * @param isShowToast 是否显示
     * @return true - 有效
     */
    public static boolean checkNickNameValid(String nickName, Context context, boolean isShowToast) {
        final int nickNameMaxLength = 30;
        if (StringUtils.isEmpty(nickName)) {
            if (isShowToast) {
                PromptUtils.showToast(R.string.nickname_must_not_be_null, Toast.LENGTH_SHORT);
            }
        } else if (nickName.length() > nickNameMaxLength) {
            if (isShowToast) {
                PromptUtils.showToast(R.string.nickname_must_less_than_30, Toast.LENGTH_SHORT);
            }
        } else {
            String[] mKeyWords = (String[]) CacheUtils.getObjectCache().get(CacheObjectKey.KEY_WORD);
            String[] mSensitiveWords = (String[]) CacheUtils.getObjectCache().get(CacheObjectKey.SENSITIVE_WORD);
            if (mKeyWords != null && mSensitiveWords != null) {
                for (String str : mKeyWords) {
                    if (nickName.contains(str)) {
                        if (isShowToast) {
                            PromptUtils.showToast(R.string.nickname_invalid, Toast.LENGTH_SHORT);
                        }
                        return false;
                    }
                }
                for (String str : mSensitiveWords) {
                    if (nickName.contains(str)) {
                        if (isShowToast) {
                            PromptUtils.showToast(R.string.nickname_invalid, Toast.LENGTH_SHORT);
                        }
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 检查email是否有效(给已注册例如ktv@.com用户使用)
     *
     * @param userEmail   email地址
     * @param isShowToast 是否吐司
     * @return true - 有效
     */
    public static boolean checkEmailValidForOldUser(String userEmail, boolean isShowToast) {
        Pattern userPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w*([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = userPattern.matcher(userEmail);
        if (matcher.matches()) {
//            if (isShowToast) {
//                PromptUtils.showToast(R.string.username_must_be_email, Toast.LENGTH_SHORT);
//            }
//            return false;
        }
        return false;
    }

    /**
     * 检查email是否有效
     *
     * @param userEmail   email地址
     * @param isShowToast 是否吐司
     * @return true - 有效
     */
    public static boolean checkEmailValid(String userEmail, boolean isShowToast) {
//        Pattern userPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    	  Pattern userPattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\_\\-]*");
        Matcher matcher = userPattern.matcher(userEmail);
        if (matcher.matches() == false) {
        	if (isShowToast) {
                PromptUtils.showToast(R.string.username_must_be_email_donot, Toast.LENGTH_SHORT);
            }
        	return false;
        }
        	  if (userEmail.length() < 6 ) {
                  if (isShowToast) {
                      PromptUtils.showToast(R.string.username_must_be_email_byte, Toast.LENGTH_SHORT);
                  }
                  return false;
        	  }
                  if (userEmail.length() > 18 ) {
                      if (isShowToast) {
                          PromptUtils.showToast(R.string.username_must_be_email_long, Toast.LENGTH_SHORT);
                      }
                      return false;
        }
        return true;
}

	/**
	 * 检查手机号码是否有效
	 * 
	 * @param phoneNum
	 *            手机号码
	 * @param isShowToast
	 *            是否吐司
	 * @return true - 有效
	 */
	public static boolean checkPhoneValid(String phoneNum, boolean isShowToast) {
		Pattern userPattern = Pattern
				.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = userPattern.matcher(phoneNum);
		
		
		if (phoneNum.trim().length() == 0) {
			if (isShowToast) {
				PromptUtils.showToast(R.string.phone_must_be,
						Toast.LENGTH_SHORT);
			}
			return false;
		}
		if (phoneNum.length() < 11) {
			if (isShowToast) {
				PromptUtils.showToast(R.string.phone_must_be_long,
						Toast.LENGTH_SHORT);
			}
			return false;
		}

//		if (matcher.matches() == false) {
//			if (isShowToast) {
//				PromptUtils.showToast(R.string.phone_must_be_donot,
//						Toast.LENGTH_SHORT);
//			}
//			return false;
//		}
		return true;
	}

    /**
     * 检查密码
     *
     * @param password    密码
     * @param isShowToast 是否吐司
     * @return true - 有效
     */
    public static boolean checkPasswordValid(String password, boolean isShowToast) {
        if (password.length() < 6 ) {
            if (isShowToast) {
                PromptUtils.showToast(R.string.password_len_more_than_6, Toast.LENGTH_SHORT);
            }
            
            return false;
        }
        if (password.length() > 18 ) {
            if (isShowToast) {
                PromptUtils.showToast(R.string.password_len_more_than_18, Toast.LENGTH_SHORT);
            }
            
            return false;
        }
        return true;
    }

    /**
     * 检查修改密码
     *
     * @param oldPassword       旧密码
     * @param newPassword       新密码
     * @param repeatNewPassword 重复新密码
     * @return true - 有效
     */
    public static boolean checkSetPasswordValid(String oldPassword, String newPassword, String repeatNewPassword) {
        if (StringUtils.isEmpty(oldPassword)) {
            PromptUtils.showToast(R.string.old_password_null, Toast.LENGTH_SHORT);
            return false;
        } else if (StringUtils.isEmpty(newPassword)) {
            PromptUtils.showToast(R.string.new_password_null, Toast.LENGTH_SHORT);
            return false;
        } else if (checkPasswordValid(newPassword, true)) {
            if (StringUtils.isEmpty(repeatNewPassword)) {
                PromptUtils.showToast(R.string.repeat_new_password_null, Toast.LENGTH_SHORT);
                return false;
            } else if (!StringUtils.equal(repeatNewPassword, newPassword)) {
                PromptUtils.showToast(R.string.new_password_error, Toast.LENGTH_SHORT);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 处理登录完成
     *
     * @param result         返回结果
     * @param activity       发生登录的activity
     * @param finishActivity 是否结束Activity
     * @return 是否登录成功
     */
    public static boolean processLoginCompleted(BaseResult result, Activity activity, Boolean finishActivity) {
        PromptUtils.dismissProgressDialog();
        if (result != null) {
            if (result instanceof UserResult) {
                int resultCode = result.getCode();
                if (resultCode == ErrorCode.ERROR_USERNAME_OR_PASSWORD
                        || resultCode == ErrorCode.ERROR_AUTHENTICATION) {
                    PromptUtils.showToast(activity.getString(R.string.username_password_error), Toast.LENGTH_SHORT);
                } else if (resultCode == ErrorCode.ERROR_USERNAME_EXIST) {
                    PromptUtils.showToast(activity.getString(R.string.username_already_existed), Toast.LENGTH_SHORT);
                }
            } else if (result instanceof UserInfoResult) {
                if (result.isSuccess()) {
                        InputMethodUtils.hideSoftInput(activity.getCurrentFocus());
                        SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy/MM/dd    HH:mm:ss");       
               		 String date = sDateFormat.format(new    java.util.Date());
               		 SharedPreferences mySharedPreferences =activity.getSharedPreferences("timeforlogin",
               				 Activity.MODE_PRIVATE); 
               		 Editor edit = mySharedPreferences.edit();
               		 edit.putString("logintime", date);
               		 edit.commit();
                        PostUnion postunion = new PostUnion();
                    	UserInfoResult mUserInfoResult = UserInfoUtils.getUserInfo();
                    	RequestUtils.requestMission(activity);
                    	try {
        					postunion.unionRequest(activity, Integer.parseInt(String.
        							valueOf(mUserInfoResult.getData().getId())), "dau","");
        				} catch (NumberFormatException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				} catch (SocketException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
                    if (finishActivity) {
                        activity.finish();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * processThirdPartyAuthorizeCompleted
     *
     * @param data    data
     * @param context context
     * @return true - 授权成功
     */
    public static boolean processThirdPartyAuthorizeCompleted(Intent data, Context context) {
        if (data != null && data.getExtras() != null) {
            UserResult result = data.getExtras().getParcelable(Authorize.AUTHORIZE_RESULT_KEY);
            RequestUtils.doPostThirdPartyAuthorize(result);
            PromptUtils.showProgressDialog(context, context.getString(R.string.doing_login));
            return true;
        }
        return false;
    }

    /**
     * 处理授权失败
     */
    public static void processAuthorizeFailure() {
        PromptUtils.dismissProgressDialog();
        PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
    }

    /**
     * 退出登陆
     */
    public static void logout() {
        CacheUtils.getObjectCache().delete(CacheObjectKey.USER_INFO_KEY);
        CacheUtils.getObjectCache().delete(CacheObjectKey.FAV_STAR_LIST_KEY);
        CacheUtils.getObjectCache().delete(CacheObjectKey.ACCESS_TOKEN);
        CacheUtils.getObjectCache().delete(CacheObjectKey.BAG_GIFT_LIST_KEY);
        CacheUtils.getObjectCache().delete(CacheObjectKey.MAIL_CONVERSATION_MAP);
        CacheUtils.getObjectCache().delete(CacheObjectKey.MISSION_LIST_KEY);
        CacheUtils.getObjectCache().delete(CacheObjectKey.COST_LOG);
        CacheUtils.getObjectCache().delete(CacheObjectKey.RECEIVE_GIFT_LOG);
        CacheUtils.getObjectCache().delete(CacheObjectKey.NOTICE_LIST);
        CacheUtils.getObjectCache().delete(CacheObjectKey.REMIND_LIST);
        CacheUtils.getObjectCache().delete(CacheObjectKey.MY_FAMILY);
        CacheUtils.getObjectCache().delete(CacheObjectKey.DAY_SIGNED_LIST_KEY);
        StorageUtils.getSharedPreferences().edit().remove(SharedPreferenceKey.DAY_TIME_MILLS_KEY).commit();
        StorageUtils.getSharedPreferences().edit().remove(SharedPreferenceKey.AUTHORIZE_INFO).commit();
        StorageUtils.getSharedPreferences().edit().remove(SharedPreferenceKey.ACCESS_TOKEN_KEY).commit();
        EnvironmentUtils.GeneralParameters.setUserId(0);
    }

    /**
     * @return 是否已经登录
     */
    public static boolean isAlreadyLogin() {
        return CacheUtils.getObjectCache().contain(CacheObjectKey.USER_INFO_KEY);
    }

}
