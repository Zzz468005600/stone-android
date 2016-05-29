package zhulei.com.stone.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.ButterKnife;
import zhulei.com.stone.R;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class BaseFragment extends AppFragment {

    protected MaterialDialog mLoadingDialog;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initActionBar(ActionBar actionBar) {
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    protected void showProgress() {
        if (getActivity() != null && isVisible()) {
            mLoadingDialog = new MaterialDialog.Builder(getActivity())
                    .content(R.string.loading)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .show();
        }
    }

    protected void hideProgress(){
        if (getActivity() != null && isVisible()){
            if (mLoadingDialog != null && mLoadingDialog.isShowing()){
                mLoadingDialog.dismiss();
            }
        }
    }

}
