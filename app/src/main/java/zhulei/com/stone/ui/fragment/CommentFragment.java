package zhulei.com.stone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import zhulei.com.stone.R;
import zhulei.com.stone.adapter.CommentAdapter;
import zhulei.com.stone.data.entity.Comment;
import zhulei.com.stone.data.entity.Message;
import zhulei.com.stone.data.entity.User;
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
    public void onBtnSendClicked(){
        String content = mCommentContent.getText().toString();
        if (TextUtils.isEmpty(content)){
            Toast.makeText(getContext(), "请输入评论内容", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = User.getCurrentUser(getContext(), User.class);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setMessage(mCurMessage);
        comment.setUser(user);
        showProgress();
        comment.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                if (getActivity() != null && isVisible()){
                    hideProgress();
                    Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                    mListContainer.setRefreshing(true);
                    getListData(0, LIMIT);
                    mCommentContent.setText("");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (getActivity() != null && isVisible()){
                    hideProgress();
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setTitle(R.string.comment_list);
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

        mListContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListData(0, LIMIT);
            }
        });
        mListContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mListContent.setItemAnimator(new DefaultItemAnimator());
        mListContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mListContent.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isLoading) {
                    if (totalItemCount > mPreviousTotal) {
                        isLoading = false;
                        mPreviousTotal = totalItemCount;
                    }
                }
                if (!isLoading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + THRESHOLD)) {
                    getListData(totalItemCount, LIMIT);
                    isLoading = true;
                }
            }
        });
        mComments = new ArrayList<>();
        mCommentAdapter = new CommentAdapter(getContext(), mComments);
        mListContent.setAdapter(mCommentAdapter);

        mContentProgress.show();
        mListContainer.setVisibility(View.GONE);
        mEmptyContent.setVisibility(View.GONE);
        getListData(0, LIMIT);
    }

    private void refreshContent(){
        if (mComments.isEmpty()){
            mEmptyContent.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.GONE);
        }else {
            mEmptyContent.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        }
    }

    private void getListData(final int skip, int limit){
        isLoading = true;
        if (skip == 0){
            mPreviousTotal = 0;
        }
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.addWhereEqualTo("message", mCurMessage);
        query.include("user");
        query.setSkip(skip);
        query.setLimit(limit);
        query.order("-createdAt");
        query.findObjects(getContext(), new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                if (getActivity() != null && isVisible()){
                    if (skip == 0){
                        mComments.clear();
                    }
                    mComments.addAll(list);
                    if (mContentProgress.isShown()){
                        mContentProgress.hide();
                        mContentProgress.setVisibility(View.GONE);
                    }
                    if (mListContainer.isRefreshing()){
                        mListContainer.setRefreshing(false);
                    }
                    mCommentAdapter.notifyDataSetChanged();
                    refreshContent();
                }
            }

            @Override
            public void onError(int i, String s) {
                if (getActivity() != null && isVisible()){
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    if (mContentProgress.isShown()){
                        mContentProgress.hide();
                        mContentProgress.setVisibility(View.GONE);
                    }
                    if (mListContainer.isRefreshing()){
                        mListContainer.setRefreshing(false);
                    }
                    refreshContent();
                }
            }
        });
    }
}
