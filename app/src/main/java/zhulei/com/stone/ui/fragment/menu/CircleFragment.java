package zhulei.com.stone.ui.fragment.menu;

import android.graphics.Color;

import butterknife.BindView;
import zhulei.com.stone.R;
import zhulei.com.stone.data.entity.custom.Circle;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.widget.CircleView;

/**
 * Created by zhulei on 16/6/14.
 */
public class CircleFragment extends BaseFragment {

    @BindView(R.id.nav_circle)
    CircleView mCircleView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    public static CircleFragment newInstance(){
        CircleFragment fragment = new CircleFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCircleView.startAnim(new Circle(20, Color.RED), new Circle(500, Color.YELLOW));
    }

    @Override
    public void onStop() {
        super.onStop();
        mCircleView.cancleAnim();
    }
}
