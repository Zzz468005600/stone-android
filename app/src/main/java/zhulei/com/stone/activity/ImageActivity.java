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

    private ArrayList<String> mImages;
    private int mPosition;
    public static final String ARG_IMAGES = "images";
    public static final String ARG_POSITION = "position";

    @Override
    protected BaseFragment getFirstFragment() {
        return ImageFragment.newInstance(mImages, mPosition);
    }

    @Override
    protected void handleIntent(Intent intent) {
        mImages = intent.getStringArrayListExtra(ARG_IMAGES);
        mPosition = intent.getIntExtra(ARG_POSITION, 0);
    }
}
