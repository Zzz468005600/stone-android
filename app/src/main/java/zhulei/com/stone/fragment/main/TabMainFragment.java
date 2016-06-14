package zhulei.com.stone.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import zhulei.com.stone.R;
import zhulei.com.stone.adapter.TabMainAdapter;
import zhulei.com.stone.entity.Message;
import zhulei.com.stone.event.Envents;
import zhulei.com.stone.manager.UserManager;

/**
 * Created by zhulei on 16/6/6.
 */
public class TabMainFragment extends Fragment{

    @BindView(R.id.list_container)
    SwipeRefreshLayout mListContainer;
    @BindView(R.id.list_content)
    RecyclerView mListContent;
    @BindView(R.id.empty_content)
    TextView mEmpty;

    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar mProgressBar;

    private ArrayList<Message> mListData = new ArrayList<>();
    private TabMainAdapter mTabMainAdapter;

    private boolean isLoading = true;
    private int mPreviousTotal = 0;

    public static final int THRESHOLD = 2;
    public static final int SIZE = 10;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
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
        mListContent.setAdapter(mTabMainAdapter);
//        mListContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE){
//                    Picasso.with(getContext()).resumeTag(TabMainAdapter.TAG);
//                }else {
//                    Picasso.with(getContext()).pauseTag(TabMainAdapter.TAG);
//                }
//            }
//        });
        if(!UserManager.instance().hasLogin()){
            mProgressBar.setVisibility(View.GONE);
        }else {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.show();
        }
        getListData(0 ,10);
    }

    public static TabMainFragment newInstance(){
        TabMainFragment instance = new TabMainFragment();
        return instance;
    }

    private void getListData(final int skip, int limit){
        isLoading = true;
        if (!UserManager.instance().hasLogin()){
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            if (mListData.size() == 0){
                mListContent.setVisibility(View.GONE);
                mEmpty.setVisibility(View.VISIBLE);
            }
            if (mListContainer.isRefreshing()){
                mListContainer.setRefreshing(false);
            }
            return;
        }
        BmobQuery<Message> query = new BmobQuery<>();
        query.include("user");
        query.setLimit(limit);
        query.setSkip(skip);
        query.order("-createdAt");
        query.findObjects(getContext(), new FindListener<Message>() {
            @Override
            public void onSuccess(List<Message> list) {
                isLoading = false;
                if (getActivity() != null && isVisible()){
                    if (mListContainer.isRefreshing()){
                        mListContainer.setRefreshing(false);
                    }
                    if (mProgressBar.getVisibility() == View.VISIBLE){
                        mProgressBar.hide();
                        mProgressBar.setVisibility(View.GONE);
                    }
                    if (skip == 0 && list.isEmpty()){
                        mListContent.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }else {
                        mListContent.setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);
                        if (skip == 0){
                            mListData.clear();
                        }
                        mListData.addAll(list);
                        mTabMainAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                isLoading = false;
                if (getActivity() != null && isVisible()){
                    if (mListContainer.isRefreshing()){
                        mListContainer.setRefreshing(false);
                    }
                    if (mProgressBar.getVisibility() == View.VISIBLE){
                        mProgressBar.hide();
                        mProgressBar.setVisibility(View.GONE);
                    }
                    if (mListData.isEmpty()){
                        mListContent.setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Subscribe
    public void onEvent(Envents.LoginEvent event){
        getListData(0, 10);
    }
}
