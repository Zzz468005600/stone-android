package zhulei.com.stone.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import zhulei.com.stone.R;
import zhulei.com.stone.data.manager.UserManager;
import zhulei.com.stone.ui.widget.WaterView;

/**
 * Created by zhulei on 16/5/30.
 */
public class ProfileActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE = 1;
    public static final int REQUEST_READ_STORAGE = 2;

    @BindView(R.id.user_header)
    ImageView mUserHeader;
    @BindView(R.id.user_header_small)
    ImageView mUserHeaderSmall;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.water_view)
    WaterView mWaterView;

    private MaterialDialog mLoadingDialog;

    @OnClick(R.id.action_personal_set)
    public void onPersonalSetClicked() {
        Intent intent = new Intent(this, BirthDaySettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.header_set)
    public void onHeaderSetClicked() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
            }
        } else {
            openImageSelector();
        }
    }

    private void openImageSelector() {
        MultiImageSelector.create(this)
                .showCamera(true)
                .single()
                .start(this, REQUEST_IMAGE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private void hideProgress() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWaterView.startAnimator();
        refreshHeader();
    }

    private void refreshHeader() {
        Picasso.with(this)
                .load(UserManager.instance().getUserHeader())
                .placeholder(R.drawable.ic_loading)
                .resize(getResources().getDisplayMetrics().widthPixels,
                        getResources().getDimensionPixelSize(R.dimen.size_thumbnail_middle))
                .centerCrop()
                .error(R.drawable.loading_fail)
                .into(mUserHeader);
        Picasso.with(this)
                .load(UserManager.instance().getUserHeader())
                .placeholder(R.drawable.ic_loading)
                .resize(getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small),
                        getResources().getDimensionPixelSize(R.dimen.size_thumbnail_small))
                .centerCrop()
                .error(R.drawable.loading_fail)
                .into(mUserHeaderSmall);
    }
}
