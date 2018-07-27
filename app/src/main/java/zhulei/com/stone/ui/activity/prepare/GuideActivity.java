package zhulei.com.stone.ui.activity.prepare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zhulei.com.stone.R;
import zhulei.com.stone.ui.activity.user.LoginActivity;
import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * author: zhulei
 * date: 2018/7/26
 * blog: http://www.zhuleiblog.com/
 */
public class GuideActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnBinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null){
            mUnBinder.unbind();
        }
    }

    private Unbinder mUnBinder;

    @OnClick(R.id.login)
    void onLoginClicked(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
