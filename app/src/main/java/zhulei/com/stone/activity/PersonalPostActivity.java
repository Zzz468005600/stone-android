package zhulei.com.stone.activity;

import android.support.v4.app.Fragment;

import zhulei.com.stone.base.BaseActivity;
import zhulei.com.stone.fragment.menu.PersonalPostFragment;

/**
 * Created by zhulei on 16/6/26.
 */
public class PersonalPostActivity extends BaseActivity {
    @Override
    protected Fragment getFirstFragment() {
        return PersonalPostFragment.newInstance();
    }
}
