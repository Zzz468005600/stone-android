package zhulei.com.stone.activity;

import android.content.Intent;

import java.util.ArrayList;

import zhulei.com.stone.base.BaseActivity;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.fragment.ImageFragment;

/**
 * Created by zhulei on 16/6/10.
 */
public class ImageActivity extends BaseActivity {

    public ArrayList<String> mImages;
    public static final String ARG_IMAGES = "images";

    @Override
    protected BaseFragment getFirstFragment() {
        return ImageFragment.newInstance(mImages);
    }

    @Override
    protected void handleIntent(Intent intent) {
        mImages = intent.getStringArrayListExtra(ARG_IMAGES);
    }
}
