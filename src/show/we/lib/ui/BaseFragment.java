package show.we.lib.ui;

import android.support.v4.app.Fragment;
import android.widget.Toast;
import show.we.lib.R;
import show.we.lib.cloudapi.ErrorCode;
import show.we.lib.service.MainService;
import show.we.lib.utils.PromptUtils;
import show.we.lib.utils.RequestUtils;
import show.we.lib.widget.dialog.StandardDialog;

/**
 * @author ll
 * @version 3.0.0
 */
public class BaseFragment extends Fragment implements OnAuthCodeConfirmListener
        , OnDrawBonusListener {

    /**
     * 当前领取任务的任务id
     */
//    protected String mCurMissionId;
    protected int mCurMissionId;

    /**
     * 获取主服务
     *
     * @return 主服务
     */
    public final MainService getMainService() {
        return (getActivity() instanceof BaseActivity ? ((BaseActivity) getActivity()).getMainService() : null);
    }

    /**
     * 是否已经连接到服务
     *
     * @return true - 已经连接到服务
     */
    public final boolean isConnected() {
        return (getActivity() instanceof BaseActivity ? ((BaseActivity) getActivity()).getMainService() != null : false);
    }

    @Override
    public void onAuthCodeConfirmed(String authCode) {
        if (isConnected()) {
        	//任务id变更
            RequestUtils.requestDrawBonus(mCurMissionId, /*authCode,*/ getActivity());
        }
        PromptUtils.showProgressDialog(getActivity(), getString(R.string.drawing));
    }

    @Override
    public void onDrawBonusError(int code) {
        if (code == ErrorCode.ERROR_NEED_VARIFY_EMAIL) {
            StandardDialog exitDialog = new StandardDialog(getActivity());
            exitDialog.setPositiveButtonText(R.string.please_varify_email);
            exitDialog.show();
        } else {
            PromptUtils.showToast(code == ErrorCode.AUTH_CODE_ERROR ? R.string.auth_code_error
                    : R.string.draw_bonus_fail, Toast.LENGTH_SHORT);
        }
        PromptUtils.dismissProgressDialog();
    }

    @Override
    public void onDrawBonusSuccess() {
        PromptUtils.dismissProgressDialog();
        PromptUtils.showToast(R.string.get_reward_succeed, Toast.LENGTH_SHORT);
    }
}
