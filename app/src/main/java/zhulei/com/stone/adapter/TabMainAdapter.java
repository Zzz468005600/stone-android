package zhulei.com.stone.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhulei.com.stone.R;
import zhulei.com.stone.activity.ImageActivity;
import zhulei.com.stone.entity.Message;
import zhulei.com.stone.entity.User;
import zhulei.com.stone.util.ImageUtil;

/**
 * Created by zhulei on 16/6/6.
 */
public class TabMainAdapter extends RecyclerView.Adapter<TabMainAdapter.TabViewHolder> {

    public static Object TAG = new Object();

    private Context mContext;
    private List<Message> mData;

    public TabMainAdapter(Context context, ArrayList<Message> data){
        this.mContext =context;
        this.mData = data;
    }

    @Override
    public TabMainAdapter.TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TabViewHolder tabVh = new TabViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_tab, parent, false));
        return tabVh;
    }

    @Override
    public void onBindViewHolder(TabMainAdapter.TabViewHolder holder, int position) {
        Message message = mData.get(position);
        User user = message.getUser();
        String userHeader = user.getHeader();
        String userName = user.getUsername();
        String text = message.getText();
        ArrayList<String> images = ImageUtil.getImages(message.getImages());
        String create = message.getCreatedAt();
        if (!TextUtils.isEmpty(userHeader)){
            Picasso.with(mContext)
                    .load(userHeader)
                    .tag(TAG)
                    .config(Bitmap.Config.ALPHA_8)
                    .resize(mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small),
                            mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small))
                    .centerCrop()
                    .error(R.drawable.loading_fail)
                    .placeholder(R.drawable.ic_loading)
                    .into(holder.mUserHeader);
        }else {
            Picasso.with(mContext)
                    .load(R.drawable.user_header)
                    .tag(TAG)
                    .config(Bitmap.Config.ALPHA_8)
                    .resize(mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small),
                            mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small))
                    .centerCrop()
                    .into(holder.mUserHeader);
        }
        if (userName != null){
            holder.mUserName.setText(userName);
        }
        if (text != null){
            holder.mTvContent.setText(text);
        }
        if (create != null){
            holder.mCreate.setText(create);
        }
        if (images != null && !images.isEmpty()){
            holder.mImages.setVisibility(View.VISIBLE);
            if (images.size() <= 4){
                holder.mImages.setNumColumns(2);
            }else {
                holder.mImages.setNumColumns(3);
            }
            holder.mImages.setAdapter(new ImageAdater(images));
        }else {
            holder.mImages.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TabViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_header)
        ImageView mUserHeader;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.gv_images)
        GridView mImages;
        @BindView(R.id.tv_create)
        TextView mCreate;

        public TabViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ImageAdater extends BaseAdapter{

        private ArrayList<String> mData;

        public ImageAdater(ArrayList<String> data){
            this.mData = data;
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
            if (itemView == null){
                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_image_main, parent, false);
                ImageViewHolder holder = new ImageViewHolder(itemView);
                itemView.setTag(holder);
            }
            ImageViewHolder holder = (ImageViewHolder) itemView.getTag();
            holder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putStringArrayListExtra(ImageActivity.ARG_IMAGES, mData);
                    intent.putExtra(ImageActivity.ARG_POSITION, position);
                    mContext.startActivity(intent);
                }
            });
            int with = mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail);
            int height = with;
            ViewGroup.LayoutParams params = holder.mImage.getLayoutParams();
            params.height = mContext.getResources().getDisplayMetrics().widthPixels / 2;
            if (mData.size() >= 5){
                params.height = mContext.getResources().getDisplayMetrics().widthPixels / 3;
            }
            Picasso.with(mContext)
                    .load(mData.get(position))
                    .tag(TAG)
                    .resize(with, height)
                    .config(Bitmap.Config.ALPHA_8)
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.loading_fail)
                    .into(holder.mImage);
            return itemView;
        }

        class ImageViewHolder{

            @BindView(R.id.image)
            ImageView mImage;

            public ImageViewHolder(View itemView){
                ButterKnife.bind(this, itemView);
            }

        }
    }
}
