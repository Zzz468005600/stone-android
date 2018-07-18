package zhulei.com.stone.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import zhulei.com.stone.R;
import zhulei.com.stone.presenter.base.IBasePresenter;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class BaseFragment<P extends IBasePresenter> extends SupportFragment {

    protected abstract int getLayoutId();

    @BindView(R.id.toolbar)
    protected Toolbar mToolBar;
    @BindView(R.id.center_title)
    protected TextView mCenterTitle;

    private Unbinder mUnBinder;

    protected void initToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        setHasOptionsMenu(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(_mActivity);
                pop();
            }
        });
    }

    protected void initView(View view, Bundle savedInstanceState) {
        initToolBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseView();
    }

    protected void releaseView() {
        if (mUnBinder != null)
            mUnBinder.unbind();
    }

}
