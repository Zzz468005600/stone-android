package zhulei.com.stone.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class AppActivity extends AppCompatActivity{

    //由于有些跳转无需参数,所以这里无需抽象方法
    protected void handleIntent(Intent intent){
    };
    protected abstract int getContentViewId();
    protected abstract BaseFragment getFirstFragment();
    protected abstract int getFragmentContainerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //写死竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //处理Intent(主要用来获取其中携带的参数)
        if (getIntent() != null){
            handleIntent(getIntent());
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //设置contentView
        setContentView(getContentViewId());
        //添加栈底的第一个fragment
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            if (getFirstFragment() != null){
                pushFragment(getFirstFragment());
            }else {
                throw new NullPointerException("getFirstFragment() cannot be null");
            }
        }
    }

    public void pushFragment(BaseFragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContainerId(), fragment)
                    .addToBackStack(((Object)fragment).getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    public void popFragment(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
        }else {
            finish();
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

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = super.getSupportParentActivityIntent();
        if (intent == null){
            finish();
        }
        return intent;
    }

    //回退键处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode){
            if (getSupportFragmentManager().getBackStackEntryCount() == 1){
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
