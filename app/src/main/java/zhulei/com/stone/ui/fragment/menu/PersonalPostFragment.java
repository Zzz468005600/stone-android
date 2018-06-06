package zhulei.com.stone.ui.fragment.menu;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zhulei.com.stone.R;
import zhulei.com.stone.data.model.entity.Message;
import zhulei.com.stone.data.manager.UserManager;
import zhulei.com.stone.ui.fragment.base.BaseListFragment;

/**
 * Created by zhulei on 16/6/26.
 */
public class PersonalPostFragment extends BaseListFragment {

    public static PersonalPostFragment newInstance() {
        PersonalPostFragment instance = new PersonalPostFragment();
        return instance;
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setTitle(R.string.personal_post);
    }

    @Override
    public void getListData(final int skip, int limit) {
        isLoading = true;
        if (skip == 0) {
            mPreviousTotal = 0;
        }
        if (!UserManager.instance().hasLogin()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            if (mListData.size() == 0) {
                mListContent.setVisibility(View.GONE);
                mEmpty.setVisibility(View.VISIBLE);
            }
            if (mListContainer.isRefreshing()) {
                mListContainer.setRefreshing(false);
            }
            return;
        }
        BmobQuery<Message> query = new BmobQuery<>();
        query.include("user");
        query.addWhereEqualTo("user", BmobUser.getCurrentUser());
        query.setLimit(limit);
        query.setSkip(skip);
        query.order("-createdAt");
        query.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if (getActivity() != null && isVisible()) {
                    if (e == null) {
                        if (mListContainer.isRefreshing()) {
                            mListContainer.setRefreshing(false);
                        }
                        if (mProgressBar.getVisibility() == View.VISIBLE) {
                            mProgressBar.hide();
                            mProgressBar.setVisibility(View.GONE);
                        }
                        if (skip == 0 && list.isEmpty()) {
                            mListContent.setVisibility(View.GONE);
                            mEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mListContent.setVisibility(View.VISIBLE);
                            mEmpty.setVisibility(View.GONE);
                            if (skip == 0) {
                                mListData.clear();
                                mTabMainAdapter.onRefresh();
                            }
                            mListData.addAll(list);
                            mTabMainAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (mListContainer.isRefreshing()) {
                            mListContainer.setRefreshing(false);
                        }
                        if (mProgressBar.getVisibility() == View.VISIBLE) {
                            mProgressBar.hide();
                            mProgressBar.setVisibility(View.GONE);
                        }
                        if (mListData.isEmpty()) {
                            mListContent.setVisibility(View.GONE);
                            mEmpty.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
