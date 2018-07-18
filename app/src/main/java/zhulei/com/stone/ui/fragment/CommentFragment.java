package zhulei.com.stone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import zhulei.com.stone.R;
import zhulei.com.stone.adapter.CommentAdapter;
import zhulei.com.stone.data.model.entity.Comment;
import zhulei.com.stone.data.model.entity.Message;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * Created by zhulei on 16/6/19.
 */
public class CommentFragment extends BaseFragment {

    private static final int LIMIT = 10;
    public static final int THRESHOLD = 2;
    public static final String ARG_MESSAGE = "arg_message";

    @BindView(R.id.content_progress)
    ContentLoadingProgressBar mContentProgress;
    @BindView(R.id.list_container)
    SwipeRefreshLayout mListContainer;
    @BindView(R.id.list_content)
    RecyclerView mListContent;
    @BindView(R.id.empty_content)
    TextView mEmptyContent;

    @BindView(R.id.comment_content)
    EditText mCommentContent;

    @OnClick(R.id.btn_send)
    public void onBtnSendClicked() {

    }

    private ArrayList<Comment> mComments;
    private CommentAdapter mCommentAdapter;
    private boolean isLoading = true;
    private int mPreviousTotal = 0;

    private Message mCurMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        mCenterTitle.setText(R.string.comment_list);
    }

    public static CommentFragment newInstance(Message message) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MESSAGE, message);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurMessage = (Message) getArguments().getSerializable(ARG_MESSAGE);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

    }

    private void refreshContent() {
        if (mComments.isEmpty()) {
            mEmptyContent.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.GONE);
        } else {
            mEmptyContent.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        }
    }

}
