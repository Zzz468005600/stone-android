package zhulei.com.stone.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * Created by zhulei on 16/6/10.
 */
public class ImageFragment extends BaseFragment {

    public static final String ARG_IMAGES = "arg_images";
    public static final String ARG_POSITION = "arg_position";
    private ArrayList<String> mImages;
    private int mPosition;
    private ArrayList<View> mViews;

    @BindView(R.id.vp_image)
    ViewPager mVpImage;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar mProgressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mViews = new ArrayList<>();
        for (int i = 0; i < mImages.size(); i++){
            mViews.add(LayoutInflater.from(getContext()).inflate(R.layout.vp_image, null));
        }
        mVpImage.setAdapter(new ImageAdapter(getContext(), mViews, mImages, listener));
        mVpImage.setCurrentItem(mPosition);
        mProgressBar.show();
        mVpImage.setVisibility(View.INVISIBLE);
    }

    private ImageAdapter.Listener listener = new ImageAdapter.Listener() {
        @Override
        public void hasLoaded() {
            if (mProgressBar.getVisibility() == View.VISIBLE){
                mProgressBar.hide();
                mProgressBar.setVisibility(View.GONE);
                mVpImage.setVisibility(View.VISIBLE);
            }
        }
    };

    public static ImageFragment newInstance(ArrayList<String> images, int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_IMAGES, images);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = getArguments().getStringArrayList(ARG_IMAGES);
        mPosition = getArguments().getInt(ARG_POSITION);
    }

    public static class ImageAdapter extends PagerAdapter {

        private Context mContext;
        private ArrayList<View> mViews;
        private ArrayList<String> mData;
        private Listener mListener;

        public ImageAdapter(Context context, ArrayList<View> views, ArrayList<String> data, Listener listener){
            this.mContext = context;
            this.mViews =views;
            this.mData = data;
            this.mListener = listener;
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
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.loading_fail)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mListener != null){
                                mListener.hasLoaded();
                            }
                        }

                        @Override
                        public void onError() {
                            if (mListener != null){
                                mListener.hasLoaded();
                            }
                        }
                    });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        interface Listener{
            void hasLoaded();
        }
    }

}
