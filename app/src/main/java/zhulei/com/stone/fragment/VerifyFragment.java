package zhulei.com.stone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import zhulei.com.stone.R;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.event.Envents;
import zhulei.com.stone.manager.UserManager;
import zhulei.com.stone.util.CheckUtil;

/**
 * Created by zhulei on 16/5/27.
 *
 * 注册.修改密码界面
 */
public class VerifyFragment extends BaseFragment {

    public static final String ARG_VERIFY = "verify";

    public static final int REGISTER = 0;
    public static final int RESET = 1;

    private int mAction;

    @BindView(R.id.verify_name)
    TextInputEditText mUserName;
    @BindView(R.id.verify_mobile)
    TextInputEditText mMobile;
    @BindView(R.id.account_password)
    TextInputEditText mUserPsw;

    @OnClick(R.id.btn_commit)
    public void onCommitBtnClicked(){
        if (mAction == REGISTER){
            if (CheckUtil.isValidMobile(mMobile.getText())){
                if (TextUtils.isEmpty(mUserName.getText().toString().trim())){
                    mUserName.setError("用户名不可为空");
                    return;
                }
                if (TextUtils.isEmpty(mUserPsw.getText().toString())){
                    mUserPsw.setError("密码不可为空");
                    return;
                }
                final BmobUser user = new BmobUser();
                user.setUsername(mUserName.getText()+"");
                user.setMobilePhoneNumber(mMobile.getText()+"");
                user.setPassword(mUserPsw.getText()+"");
                user.signUp(getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        if (getActivity() != null && isVisible()){
                            UserManager.instance().saveUser(user);
                            EventBus.getDefault().post(new Envents.LoginEvent());
                            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        if (getActivity() != null && isVisible()){
                            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                mMobile.setError("手机号不合法");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify;
    }

    @Override
    protected void initActionBar(ActionBar actionBar) {
        super.initActionBar(actionBar);
        if (mAction == RESET){
            actionBar.setTitle(R.string.reset_psw);
        }else {
            actionBar.setTitle(R.string.register);
        }
    }

    public static VerifyFragment newInstance(int action){
        VerifyFragment fragment = new VerifyFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_VERIFY, action);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mAction = getArguments().getInt(ARG_VERIFY);
        }
    }

}
