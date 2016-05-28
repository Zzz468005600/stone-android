package zhulei.com.stone.fragment;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.OnClick;
import zhulei.com.stone.R;
import zhulei.com.stone.base.BaseFragment;

/**
 * Created by zhulei on 16/5/27.
 *
 * 登陆主界面
 */
public class LoginFragment extends BaseFragment {

    @BindView(R.id.check_visible)
    CheckBox mCheckVisible;
    @BindView(R.id.user_name)
    TextInputEditText mUserName;
    @BindView(R.id.user_password)
    TextInputEditText mUserPsw;

    @OnClick(R.id.login_btn)
    public void onLoginBtnClicked(){

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
