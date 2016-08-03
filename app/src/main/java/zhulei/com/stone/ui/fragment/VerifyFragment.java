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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.util.CheckUtil;

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
        if (TextUtils.isEmpty(mUserPsw.getText())) {
            mUserPsw.setError(getString(R.string.error_psw));
            return;
        }
        if (mAction == REGISTER) {
            if (CheckUtil.isValidMobile(mMobile.getText())) {
                if (TextUtils.isEmpty(mUserName.getText().toString().trim())) {
                    mUserName.setError(getString(R.string.error_user_name));
                    return;
                }
                final BmobUser user = new BmobUser();
                user.setUsername(mUserName.getText() + "");
                user.setMobilePhoneNumber(mMobile.getText() + "");
                user.setPassword(mUserPsw.getText() + "");
                showProgress();
                user.signUp(getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        hideProgress();
                        if (getActivity() != null && isVisible()) {
                            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                            popFragment();
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        hideProgress();
                        if (getActivity() != null && isVisible()) {
                            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                mMobile.setError(getString(R.string.error_mobile));
            }
        } else if (mAction == RESET) {
            if (!TextUtils.isEmpty(mOldPsw.getText())) {
                BmobUser user = BmobUser.getCurrentUser(getContext());
                if (user != null) {
                    showProgress();
                    user.updateCurrentUserPassword(getContext(), mOldPsw.getText().toString(),
                            mUserPsw.getText().toString(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    hideProgress();
                                    if (getActivity() != null && isVisible()) {
                                        Toast.makeText(getContext(), getString(R.string.reset_success), Toast.LENGTH_SHORT).show();
                                        popFragment();
                                    }
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    hideProgress();
                                    if (getActivity() != null && isVisible()) {
                                        if (s.contains("sessionToken")){
                                            Toast.makeText(getContext(), getString(R.string.first_login), Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getContext(), getString(R.string.never_login), Toast.LENGTH_SHORT).show();
                }
            } else {
                mOldPsw.setError(getString(R.string.error_psw));
            }
        }
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
