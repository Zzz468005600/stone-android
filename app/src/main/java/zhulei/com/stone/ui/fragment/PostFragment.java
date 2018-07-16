package zhulei.com.stone.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelector;
import zhulei.com.stone.R;
import zhulei.com.stone.data.model.entity.Photo;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * Created by zhulei on 16/5/29.
 */
public class PostFragment extends BaseFragment {

    public static final int REQUEST_PHOTO = 1;
    public static final int REQUEST_READ_STORAGE = 2;

    @BindView(R.id.et_text)
    EditText mEtText;
    @BindView(R.id.grid_image)
    GridView mGridImage;

    private ArrayList<Photo> mPhotos;
    private PhotoAdapter mPhotoAdapter;
    private boolean posting;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post;
    }

    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        initGridImage();
    }

    private void initGridImage() {
        mPhotos = new ArrayList<>();
        mPhotos.add(new Photo(null, null));
        mPhotoAdapter = new PhotoAdapter(mPhotos, this);
        mPhotoAdapter.setOnItemClickListener(listener);
        mGridImage.setAdapter(mPhotoAdapter);
    }

    private PhotoAdapter.OnItemClickListener listener = new PhotoAdapter.OnItemClickListener() {
        @Override
        public void onAddBtnClicked(int position) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
            } else {
                openImageSelector();
            }
        }

        @Override
        public void onCloseBtnClicked(int position) {
            mPhotos.remove(position);
            if (mPhotos.size() < 9 && !mPhotos.get(0).isEmpty()) {
                mPhotos.add(0, new Photo(null, null));
            }
            mPhotoAdapter.notifyDataSetChanged();
        }
    };

    private void openImageSelector() {
        MultiImageSelector.create(getContext())
                .showCamera(true)
                .multi()
                .count(9 - mPhotos.size() + 1)
                .start(this, REQUEST_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImageSelector();
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                List<String> images = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (images != null && images.size() > 0) {
                    for (int i = 0; i < images.size(); i++) {
                        mPhotos.add(new Photo(images.get(i), null));
                    }
                    if (mPhotos.size() == 10 && mPhotos.get(0).isEmpty()) {
                        mPhotos.remove(0);
                    }
                    mPhotoAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu_post, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_post) {
            if (!posting) {
                postMessage();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void postMessage() {

    }

    public static class PhotoAdapter extends BaseAdapter {

        private ArrayList<Photo> mData;
        private Context mContext;

        private OnItemClickListener listener;

        public PhotoAdapter(ArrayList<Photo> photos, Fragment fragment) {
            mData = photos;
            mContext = fragment.getContext();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_image, null);
                PhotoViewHolder mHolder = new PhotoViewHolder(itemView);
                itemView.setTag(mHolder);
            }
            ImageView ivPhoto = ((PhotoViewHolder) itemView.getTag()).getmIvPhoto();
            ImageView btnAdd = ((PhotoViewHolder) itemView.getTag()).getmBtnAdd();
            ImageView btnClose = ((PhotoViewHolder) itemView.getTag()).getmBtnClose();
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onCloseBtnClicked(position);
                    }
                }
            });
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAddBtnClicked(position);
                    }
                }
            });
            if (mData.get(position).isEmpty()) {
                ivPhoto.setVisibility(View.GONE);
                btnClose.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);
            } else {
                ivPhoto.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.GONE);
                Picasso.with(mContext)
                        .load(new File(mData.get(position).localPath))
                        .resize(mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small),
                                mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small))
                        .config(Bitmap.Config.ALPHA_8)
                        .centerCrop()
                        .into(ivPhoto);
            }
            return itemView;
        }

        public interface OnItemClickListener {
            void onAddBtnClicked(int position);

            void onCloseBtnClicked(int position);
        }

        public class PhotoViewHolder {

            public View mItemView;
            private ImageView mIvPhoto;
            private ImageView mBtnClose;
            private ImageView mBtnAdd;

            public PhotoViewHolder(View itemView) {
                mItemView = itemView;
            }

            public ImageView getmIvPhoto() {
                if (mIvPhoto == null) {
                    mIvPhoto = (ImageView) mItemView.findViewById(R.id.image);
                }
                return mIvPhoto;
            }

            public ImageView getmBtnAdd() {
                if (mBtnAdd == null) {
                    mBtnAdd = (ImageView) mItemView.findViewById(R.id.btn_add);
                }
                return mBtnAdd;
            }

            public ImageView getmBtnClose() {
                if (mBtnClose == null) {
                    mBtnClose = (ImageView) mItemView.findViewById(R.id.btn_close);
                }
                return mBtnClose;
            }
        }
    }
}
