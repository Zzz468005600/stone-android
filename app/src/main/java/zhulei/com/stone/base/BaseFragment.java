package zhulei.com.stone.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhulei.com.stone.R;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class BaseFragment extends AppFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    protected MaterialDialog mLoadingDialog;

    protected void initToolBar(Toolbar toolbar){
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        ButterKnife.bind(this, view);
        initToolBar(mToolBar);
    }

    protected void showProgress(String content){
        if (getActivity() != null && isVisible()) {
            mLoadingDialog = new MaterialDialog.Builder(getActivity())
                    .content(content)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .show();
        }
    }

    protected void showProgress() {
        showProgress(getString(R.string.loading));
    }

    protected void hideProgress(){
        if (getActivity() != null && isVisible()){
            if (mLoadingDialog != null && mLoadingDialog.isShowing()){
                mLoadingDialog.dismiss();
            }
        }
    }

}
