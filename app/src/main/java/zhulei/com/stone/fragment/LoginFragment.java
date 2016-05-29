package zhulei.com.stone.fragment;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import zhulei.com.stone.R;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.event.Envents;
import zhulei.com.stone.manager.UserManager;
import zhulei.com.stone.util.CheckUtil;

/**
 * Created by zhulei on 16/5/27.
 *
 * 登陆主界面
 */
public class LoginFragment extends BaseFragment {

    public static final String SQL_MOBILE = "mobilePhoneNumber";
    public static final String SQL_PSW = "password";

    @BindView(R.id.user_mobile)
    TextInputEditText mUserMobile;
    @BindView(R.id.user_password)
    TextInputEditText mUserPsw;

    @OnCheckedChanged(R.id.check_visible)
    public void onChecked(boolean checked){
        if (checked){
            mUserPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            mUserPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mUserPsw.postInvalidate();
        CharSequence text = mUserPsw.getText();
        if (!TextUtils.isEmpty(text)){
            Selection.setSelection((Spannable) text, text.length());
        }
    }
    @OnClick(R.id.login_btn)
    public void onLoginBtnClicked(){
        if (CheckUtil.isValidMobile(mUserMobile.getText())){
            if (!TextUtils.isEmpty(mUserPsw.getText())){
                BmobUser.loginByAccount(getContext(), mUserMobile.getText() + "", mUserPsw.getText() + "",
                        new LogInListener<BmobUser>() {
                            @Override
                            public void done(BmobUser user, BmobException e) {
                                if (getActivity() != null && isVisible()){
                                    if (user != null){
                                        UserManager.instance().saveUser(user);
                                        Toast.makeText(getContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                                        EventBus.getDefault().post(new Envents.LoginEvent());
                                        getActivity().finish();
                                    }else {
                                        Toast.makeText(getContext(), getString(R.string.error_login), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }else {
                mUserPsw.setError(getString(R.string.error_psw));
            }
        }else {
            mUserMobile.setError(getString(R.string.error_mobile));
        }
    }
    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked(){
        pushFragment(VerifyFragment.newInstance(VerifyFragment.REGISTER));
    }
    @OnClick(R.id.forget_button)
    public void onForgetBtnClicked(){
        pushFragment(VerifyFragment.newInstance(VerifyFragment.RESET));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initActionBar(ActionBar actionBar) {
        super.initActionBar(actionBar);
        actionBar.setTitle(R.string.login);
    }

}
