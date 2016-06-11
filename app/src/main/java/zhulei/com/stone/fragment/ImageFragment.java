package zhulei.com.stone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import zhulei.com.stone.R;
import zhulei.com.stone.base.BaseFragment;

/**
 * Created by zhulei on 16/6/10.
 */
public class ImageFragment extends BaseFragment {

    public static final String ARG_IMAGES = "arg_images";
    private ArrayList<String> mImages;
    private ArrayList<View> mViews;

    @BindView(R.id.vp_image)
    ViewPager mVpImage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mViews = new ArrayList<>();
        for (int i = 0; i < mImages.size(); i++){
            mViews.add(LayoutInflater.from(getContext()).inflate(R.layout.vp_image, null));
        }
        mVpImage.setAdapter(new ImageAdapter(getContext(), mViews, mImages));
    }

    public static ImageFragment newInstance(ArrayList<String> images) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_IMAGES, images);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = getArguments().getStringArrayList(ARG_IMAGES);
    }

    public static class ImageAdapter extends PagerAdapter {

        private Context mContext;
        private ArrayList<View> mViews;
        private ArrayList<String> mData;

        public ImageAdapter(Context context, ArrayList<View> views, ArrayList<String> data){
            this.mContext = context;
            this.mViews =views;
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViews.get(position);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Picasso.with(mContext)
                    .load(mData.get(position))
                    .resize(mContext.getResources().getDisplayMetrics().widthPixels,
                            mContext.getResources().getDisplayMetrics().widthPixels)
                    .centerInside()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.loading_fail)
                    .into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }
    }

}
