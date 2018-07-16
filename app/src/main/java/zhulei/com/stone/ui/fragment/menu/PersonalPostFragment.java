package zhulei.com.stone.ui.fragment.menu;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import zhulei.com.stone.R;
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

    }
}
