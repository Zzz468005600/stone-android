package zhulei.com.stone.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import zhulei.com.stone.R;
import zhulei.com.stone.base.BaseFragment;

/**
 * Created by zhulei on 16/6/16.
 */
public class BirthDaySettingFragment extends BaseFragment {

    @BindView(R.id.tv_title1)
    TextView mTitle1;
    @BindView(R.id.tv_title2)
    TextView mTitle2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_birthday_setting;
    }

    public static BirthDaySettingFragment newInstance() {
        BirthDaySettingFragment fragment = new BirthDaySettingFragment();
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mTitle1.setText("告诉我们你的信息");
        mTitle2.setText("让你的个人信息更完整");
    }
}
