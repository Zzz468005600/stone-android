package zhulei.com.stone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import zhulei.com.stone.fragment.main.TabMainFragment;

/**
 * Created by zhulei on 16/6/6.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public static final int PAGE_COUNT = 4;
    public static final String[] TITLES = new String[]{"首页", "新闻", "豆瓣", "博客"};

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return TabMainFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
