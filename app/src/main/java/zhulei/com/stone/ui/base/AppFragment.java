package zhulei.com.stone.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhulei on 16/5/27.
 */
public abstract class AppFragment extends SupportFragment {

    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);
    protected void releaseView(){

    };

    protected BaseActivity getHoldingActivity(){
        if (getActivity() instanceof BaseActivity){
            return (BaseActivity)getActivity();
        }else {
            throw new ClassCastException("activity must extends BaseActivity");
        }
    }

    protected void pushFragment(BaseFragment fragment){
        getHoldingActivity().pushFragment(fragment);
    }

    protected void popFragment(){
        getHoldingActivity().popFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseView();
    }

}
