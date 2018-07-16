package zhulei.com.stone.ui.fragment;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * Created by zhulei on 16/5/27.
 *
 * 登陆主界面
 */
public class LoginFragment extends BaseFragment {

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

    }
    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked(){
        pushFragment(VerifyFragment.newInstance(VerifyFragment.REGISTER));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setTitle(R.string.login);
    }
}
