package zhulei.com.stone.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.nereo.multi_image_selector.MultiImageSelector;
import zhulei.com.stone.R;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.entity.User;
import zhulei.com.stone.event.Envents;
import zhulei.com.stone.manager.UserManager;

/**
 * Created by zhulei on 16/5/30.
 */
public class ProfileFragment extends BaseFragment {

    public static final int REQUEST_IMAGE = 1;
    public static final int REQUEST_READ_STORAGE = 2;

    @BindView(R.id.tv_name)
    TextView mUserName;
    @BindView(R.id.tv_phone)
    TextView mUserMobile;
    @BindView(R.id.iv_header)
    ImageView mUserHeader;

    @OnClick(R.id.iv_header)
    public void onUserHeaderClicked(){
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
        }else {
            openImageSelector();
        }
    }

    private void openImageSelector(){
        MultiImageSelector.create(getContext())
                .showCamera(true)
                .single()
                .start(this, REQUEST_IMAGE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        refreshHeader();
    }

    private void refreshHeader(){
        if (UserManager.instance().getUserHeader() != null){
            Picasso.with(getContext())
                    .load(UserManager.instance().getUserHeader())
                    .resize(getContext().getResources().getDimensionPixelOffset(R.dimen.header_with),
                            getContext().getResources().getDimensionPixelOffset(R.dimen.header_height))
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.loading_fail)
                    .into(mUserHeader);
        }else {
            Picasso.with(getContext())
                    .load(R.drawable.user_header)
                    .resize(getContext().getResources().getDimensionPixelOffset(R.dimen.header_with),
                            getContext().getResources().getDimensionPixelOffset(R.dimen.header_height))
                    .centerCrop()
                    .into(mUserHeader);
        }
        mUserName.setText(UserManager.instance().getUserName());
        mUserMobile.setText(UserManager.instance().getPhoneNumber());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE){
            if (resultCode == Activity.RESULT_OK){
                List<String> images = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (images != null && images.size() > 0){
                    final BmobFile bmobFile = new BmobFile(new File(images.get(0)));
                    showProgress("正在上传头像...");
                    bmobFile.uploadblock(getContext(), new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            if (getActivity() != null && isVisible()){
                                updateUser(bmobFile);
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            hideProgress();
                            if (getActivity() != null && isVisible()){
                                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    }

    private void updateUser(BmobFile bmobFile){
        final User newUser = new User();
        newUser.setHeader(bmobFile.getFileUrl(getContext()));
        BmobUser user = User.getCurrentUser(getContext());
        newUser.update(getContext(), user.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                hideProgress();
                if (getActivity() != null && isVisible()){
                    UserManager.instance().updateUserHeader(newUser.getHeader());
                    refreshHeader();
                    EventBus.getDefault().post(new Envents.UpdateUserHeader());
                }
            }

            @Override
            public void onFailure(int i, String s) {
                hideProgress();
                if (getActivity() != null && isVisible()){
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
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

}
