package zhulei.com.stone.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import zhulei.com.stone.data.model.entity.Message;
import zhulei.com.stone.data.manager.UserManager;
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

    public static TabMainFragment newInstance(){
        TabMainFragment instance = new TabMainFragment();
        return instance;
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void getListData(final int skip, int limit){
        isLoading = true;
        if (skip == 0){
            mPreviousTotal = 0;
        }
        if (!UserManager.instance().hasLogin()){
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            if (mListData.size() == 0){
                mListContent.setVisibility(View.GONE);
                mEmpty.setVisibility(View.VISIBLE);
            }
            if (mListContainer.isRefreshing()){
                mListContainer.setRefreshing(false);
            }
            return;
        }
        BmobQuery<Message> query = new BmobQuery<>();
        query.include("user");
        query.setLimit(limit);
        query.setSkip(skip);
        query.order("-createdAt");
        query.findObjects(getContext(), new FindListener<Message>() {
            @Override
            public void onSuccess(List<Message> list) {
                if (getActivity() != null && isVisible()){
                    if (mListContainer.isRefreshing()){
                        mListContainer.setRefreshing(false);
                    }
                    if (mProgressBar.getVisibility() == View.VISIBLE){
                        mProgressBar.hide();
                        mProgressBar.setVisibility(View.GONE);
                    }
                    if (skip == 0 && list.isEmpty()){
                        mListContent.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }else {
                        mListContent.setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);
                        if (skip == 0){
                            mListData.clear();
                            mTabMainAdapter.onRefresh();
                        }
                        mListData.addAll(list);
                        mTabMainAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                if (getActivity() != null && isVisible()){
                    if (mListContainer.isRefreshing()){
                        mListContainer.setRefreshing(false);
                    }
                    if (mProgressBar.getVisibility() == View.VISIBLE){
                        mProgressBar.hide();
                        mProgressBar.setVisibility(View.GONE);
                    }
                    if (mListData.isEmpty()){
                        mListContent.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Subscribe
    public void onEvent(Envents.LoginEvent event){
        getListData(0, 10);
    }
}
