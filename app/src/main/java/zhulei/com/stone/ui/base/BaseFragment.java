package zhulei.com.stone.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhulei.com.stone.R;
import zhulei.com.stone.presenter.base.IBasePresenter;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class BaseFragment<P extends IBasePresenter> extends AppFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    protected MaterialDialog mLoadingDialog;
    protected P mPresenter;
    private Unbinder mUnBinder;

    protected void initToolBar(Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popFragment();
            }
        });
        setHasOptionsMenu(true);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mUnBinder =  ButterKnife.bind(this, view);
        initToolBar(mToolBar);
    }

    protected P initPresenter(){
        return null;
    }

    protected void showProgress(String content) {
        if (getActivity() != null && isVisible()) {
            hideProgress();
            if (mLoadingDialog == null) {
                mLoadingDialog = new MaterialDialog.Builder(getActivity())
                        .content(content)
                        .progress(true, 0)
                        .canceledOnTouchOutside(false)
                        .build();
            }
            mLoadingDialog.show();
        }
    }

    protected void showProgress() {
        showProgress(getString(R.string.loading));
    }

    protected void hideProgress() {
        if (getActivity() != null && isVisible()) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
    }

    public void showLoading(String message) {
        showProgress(message);
    }

    public void hideLoading() {
        hideProgress();
    }
}
