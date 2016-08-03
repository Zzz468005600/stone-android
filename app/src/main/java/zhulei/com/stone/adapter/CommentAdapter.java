package zhulei.com.stone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhulei.com.stone.R;
import zhulei.com.stone.data.entity.Comment;
import zhulei.com.stone.data.entity.User;

/**
 * Created by zhulei on 16/6/19.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private ArrayList<Comment> mComments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.mContext = context;
        this.mComments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        User user = comment.getUser();
        if (user.getHeader() != null) {
            Picasso.with(mContext)
                    .load(user.getHeader())
                    .resize(mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small),
                            mContext.getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small))
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.loading_fail)
                    .centerCrop()
                    .into(holder.mUserHeader);
        }
        if (user.getUsername() != null) {
            holder.mUserName.setText(user.getUsername());
        }
        if (comment.getCreatedAt() != null) {
            holder.mCreateTime.setText(comment.getCreatedAt());
        }
        if (comment.getContent() != null) {
            holder.mCommentContent.setText(comment.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_header)
        ImageView mUserHeader;
        @BindView(R.id.create_time)
        TextView mCreateTime;
        @BindView(R.id.comment_content)
        TextView mCommentContent;
        @BindView(R.id.user_name)
        TextView mUserName;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
