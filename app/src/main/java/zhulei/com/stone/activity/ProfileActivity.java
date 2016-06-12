package zhulei.com.stone.activity;

import android.Manifest;
import android.app.Activity;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.nereo.multi_image_selector.MultiImageSelector;
import zhulei.com.stone.R;
import zhulei.com.stone.entity.User;
import zhulei.com.stone.event.Envents;
import zhulei.com.stone.manager.UserManager;

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

    private MaterialDialog mLoadingDialog;

    @OnClick(R.id.action_personal_set)
    public void onPersonalSetClicked(){

    }
    @OnClick(R.id.header_set)
    public void onHeaderSetClicked(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
            }
        }else {
            openImageSelector();
        }
    }

    private void openImageSelector(){
        MultiImageSelector.create(this)
                .showCamera(true)
                .single()
                .start(this, REQUEST_IMAGE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE){
            if (resultCode == Activity.RESULT_OK){
                List<String> images = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (images != null && images.size() > 0){
                    final BmobFile bmobFile = new BmobFile(new File(images.get(0)));
                    showProgress("正在上传头像...");
                    bmobFile.uploadblock(this, new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            updateUser(bmobFile);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            hideProgress();
                            Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

    private void hideProgress() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }

    private void showProgress(String s) {
        mLoadingDialog = new MaterialDialog.Builder(this)
                .content(s)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();
    }

    private void updateUser(BmobFile bmobFile){
        final User newUser = new User();
        newUser.setHeader(bmobFile.getFileUrl(this));
        BmobUser user = User.getCurrentUser(this);
        newUser.update(this, user.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                hideProgress();
                UserManager.instance().updateUserHeader(newUser.getHeader());
                refreshHeader();
                EventBus.getDefault().post(new Envents.UpdateUserHeader());
            }

            @Override
            public void onFailure(int i, String s) {
                hideProgress();
                Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        refreshHeader();
    }

    private void refreshHeader() {
        Picasso.with(this)
                .load(UserManager.instance().getUserHeader())
                .placeholder(R.drawable.ic_loading)
                .resize(getResources().getDisplayMetrics().widthPixels,
                        getResources().getDimensionPixelSize(R.dimen.size_thumbnail))
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
