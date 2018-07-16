package zhulei.com.stone.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import zhulei.com.stone.others.event.Envents;
import zhulei.com.stone.ui.fragment.base.BaseListFragment;

/**
 * Created by zhulei on 16/6/6.
 */
public class TabMainFragment extends BaseListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static TabMainFragment newInstance() {
        TabMainFragment instance = new TabMainFragment();
        return instance;
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void getListData(final int skip, int limit) {
    }

    @Subscribe
    public void onEvent(Envents.LoginEvent event) {
        getListData(0, 10);
    }
}
