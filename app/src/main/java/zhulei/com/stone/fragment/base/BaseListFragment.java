package zhulei.com.stone.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhulei.com.stone.R;
import zhulei.com.stone.activity.CommentActivity;
import zhulei.com.stone.adapter.TabMainAdapter;
import zhulei.com.stone.entity.Message;
import zhulei.com.stone.manager.UserManager;

/**
 * Created by zhulei on 16/6/26.
 */
public abstract class BaseListFragment extends Fragment {

    @BindView(R.id.list_container)
    protected SwipeRefreshLayout mListContainer;
    @BindView(R.id.list_content)
    protected RecyclerView mListContent;
    @BindView(R.id.empty_content)
    protected TextView mEmpty;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.progress_bar)
    protected ContentLoadingProgressBar mProgressBar;

    protected ArrayList<Message> mListData = new ArrayList<>();
    protected TabMainAdapter mTabMainAdapter;

    protected boolean isLoading = true;
    protected int mPreviousTotal = 0;

    public static final int THRESHOLD = 2;
    public static final int SIZE = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected void initToolBar(Toolbar toolbar){
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    };

    private void initView() {
        initToolBar(mToolBar);
        mListContainer.setColorSchemeColors(R.color.colorPrimaryDark);
        mListContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListData(0, SIZE);
            }
        });

        mListContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
                    getListData(totalItemCount, SIZE);
                    isLoading = true;
                }
            }
        });
        mTabMainAdapter = new TabMainAdapter(getContext(), mListData);
        mTabMainAdapter.setListener(mListener);
        mListContent.setAdapter(mTabMainAdapter);
        if(!UserManager.instance().hasLogin()){
            mProgressBar.setVisibility(View.GONE);
        }else {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.show();
        }
        getListData(0 ,10);
    }

    private TabMainAdapter.Listener mListener = new TabMainAdapter.Listener() {
        @Override
        public void onCommentClicked(Message message) {
            Intent intent = new Intent(getContext(), CommentActivity.class);
            intent.putExtra(CommentActivity.ARG_MESSAGE, message);
            startActivity(intent);
        }
    };

    public abstract void getListData(final int skip, int limit);

}
