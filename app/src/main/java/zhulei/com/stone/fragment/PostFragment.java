package zhulei.com.stone.fragment;

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
import android.text.TextUtils;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import me.nereo.multi_image_selector.MultiImageSelector;
import zhulei.com.stone.R;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.entity.Message;
import zhulei.com.stone.entity.Photo;
import zhulei.com.stone.entity.User;
import zhulei.com.stone.manager.UserManager;
import zhulei.com.stone.util.ImageUtil;

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
        setHasOptionsMenu(true);
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
            }else {
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

    private void openImageSelector(){
        MultiImageSelector.create(getContext())
                .showCamera(true)
                .multi()
                .count(9 - mPhotos.size() + 1)
                .start(this, REQUEST_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
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
            postMessage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void postMessage() {
        if (!UserManager.instance().hasLogin()) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mEtText.getText().toString().trim())) {
            Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        final String[] images;
        if (mPhotos.size() > 1) {
            if (mPhotos.get(0).isEmpty()) {
                images = new String[mPhotos.size() - 1];
            } else {
                images = new String[mPhotos.size()];
            }
            for (int i = 0; i < mPhotos.size(); i++) {
                Photo photo = mPhotos.get(i);
                if (images.length == mPhotos.size()) {
                    images[i] = photo.localPath;
                } else {
                    if (i > 0) {
                        images[i - 1] = photo.localPath;
                    }
                }
            }
            showProgress("正在发表...");
            BmobFile.uploadBatch(getContext(), images, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> urls) {
                    if (urls.size() == images.length) {
                        sendMessage(urls);
                    }
                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {
                }

                @Override
                public void onError(int i, String s) {
                    if (getActivity() != null && isVisible()) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            showProgress("正在发表...");
            sendMessage(null);
        }
    }

    private void sendMessage(List<String> images) {
        Message message = new Message();
        User user = BmobUser.getCurrentUser(getContext(), User.class);
        message.setUser(user);
        if (images != null) {
            message.setImages(ImageUtil.transImages(images));
            message.setText(mEtText.getText() + "");
        } else {
            message.setText(mEtText.getText() + "");
            message.setImages("");
        }
        message.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                if (getActivity() != null && isVisible()) {
                    hideProgress();
                    Toast.makeText(getContext(), "发表成功", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (getActivity() != null && isVisible()) {
                    hideProgress();
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class PhotoAdapter extends BaseAdapter {

        private ArrayList<Photo> mData;
        private Context mContext;

        private OnItemClickListener listener;

        public PhotoAdapter(ArrayList<Photo> photos, Fragment fragment) {
            mData = photos;
            mContext = fragment.getContext();
        }

        public void setOnItemClickListener(OnItemClickListener listener){
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
                    if (listener != null){
                        listener.onCloseBtnClicked(position);
                    }
                }
            });
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
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

        public interface OnItemClickListener{
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
