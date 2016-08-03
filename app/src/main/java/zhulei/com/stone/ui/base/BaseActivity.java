package zhulei.com.stone.ui.base;

import zhulei.com.stone.R;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class BaseActivity extends AppActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_base;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }
}
