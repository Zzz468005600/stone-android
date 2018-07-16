package zhulei.com.stone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * Created by zhulei on 16/5/27.
 * <p>
 * 注册.修改密码界面
 */
public class VerifyFragment extends BaseFragment {

    public static final String ARG_VERIFY = "verify";

    public static final int REGISTER = 0;
    public static final int RESET = 1;

    private int mAction;

    @BindView(R.id.verify_name_layout)
    View mVerifyNameLayout;
    @BindView(R.id.old_psw_layout)
    View mOldPswLayout;
    @BindView(R.id.mobile_layout)
    View mMobileLayout;
    @BindView(R.id.verify_name)
    TextInputEditText mUserName;
    @BindView(R.id.verify_mobile)
    TextInputEditText mMobile;
    @BindView(R.id.account_password)
    TextInputEditText mUserPsw;
    @BindView(R.id.account_old_password)
    TextInputEditText mOldPsw;

    @OnCheckedChanged(R.id.check_visible)
    public void onCheckClicked(boolean checked) {
        if (checked) {
            mUserPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mUserPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mUserPsw.postInvalidate();
        CharSequence text = mUserPsw.getText();
        if (TextUtils.isEmpty(text)) {
            Selection.setSelection((Spannable) text, text.length());
        }
    }

    @OnClick(R.id.btn_commit)
    public void onCommitBtnClicked() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify;
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        if (mAction == RESET) {
            toolbar.setTitle(R.string.reset_psw);
            mVerifyNameLayout.setVisibility(View.GONE);
            mOldPswLayout.setVisibility(View.VISIBLE);
            mMobileLayout.setVisibility(View.GONE);
        } else {
            toolbar.setTitle(R.string.register);
            mVerifyNameLayout.setVisibility(View.VISIBLE);
            mOldPswLayout.setVisibility(View.GONE);
            mMobileLayout.setVisibility(View.VISIBLE);
        }
    }

    public static VerifyFragment newInstance(int action) {
        VerifyFragment fragment = new VerifyFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_VERIFY, action);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAction = getArguments().getInt(ARG_VERIFY);
        }
    }

}
