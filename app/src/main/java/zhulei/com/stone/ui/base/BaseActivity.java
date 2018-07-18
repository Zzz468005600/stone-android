package zhulei.com.stone.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import me.yokeyword.fragmentation.SupportActivity;
import zhulei.com.stone.R;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class BaseActivity extends SupportActivity {

    //由于有些跳转无需参数,所以这里无需抽象方法
    protected void handleIntent(Intent intent){
    }
    protected abstract BaseFragment getFirstFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //写死竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //处理Intent(主要用来获取其中携带的参数)
        if (getIntent() != null){
            handleIntent(getIntent());
        }

        //设置contentView
        setContentView(getContentViewLayout());

        //添加栈底的第一个fragment
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            if (getFirstFragment() != null){
                loadRootFragment(getFragmentContainerId(), getFirstFragment());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onSupportNavigateUp();
    }

    protected int getContentViewLayout() {
        return R.layout.activity_base;
    }

    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }
}
