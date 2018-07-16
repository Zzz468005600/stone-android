package zhulei.com.stone.ui.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import zhulei.com.stone.R;
import zhulei.com.stone.data.manager.UserManager;
import zhulei.com.stone.ui.base.BaseFragment;

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
    public void onUserHeaderClicked() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
        } else {
            openImageSelector();
        }
    }

    private void openImageSelector() {
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

    private void refreshHeader() {
        if (UserManager.instance().getUserHeader() != null) {
            Picasso.with(getContext())
                    .load(UserManager.instance().getUserHeader())
                    .resize(getContext().getResources().getDimensionPixelOffset(R.dimen.header_with),
                            getContext().getResources().getDimensionPixelOffset(R.dimen.header_height))
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.loading_fail)
                    .into(mUserHeader);
        } else {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImageSelector();
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
