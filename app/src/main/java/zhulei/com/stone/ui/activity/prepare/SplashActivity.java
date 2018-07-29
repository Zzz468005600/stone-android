package zhulei.com.stone.ui.activity.prepare;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * author: zhulei
 * date: 2018/7/26
 * blog: http://www.zhuleiblog.com/
 */
public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_splash;
    }

    private static class SplashHandler extends Handler {

        private WeakReference<SplashActivity> mActivity;

        SplashHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mActivity.get();
            if (activity != null) {
                activity.requestPermissionsSuccess();
            }
        }
    }

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int RC_REQUEST_CODE_ALL_PERMISSIONS = 0X3001;
    private static final String[] ALL_PERMISSIONS = {Manifest.permission.INTERNET};

    private SplashHandler mHandler = new SplashHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult -> " + requestCode);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.somePermissionDenied(this, ALL_PERMISSIONS)) {
                finish();
            } else {
                requestPermissionsSuccess();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        for (String perm : perms) {
            Log.d(TAG, "onPermissionsGranted -> " + perm);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        for (String perm : perms) {
            Log.d(TAG, "onPermissionsDenied -> " + perm);
        }
        if (EasyPermissions.somePermissionDenied(this, perms.toArray(new String[perms.size()]))) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限申请")
                    .setRationale("在设置-应用-stone中开启权限，以正常使用")
                    .setNegativeButton("取消")
                    .setPositiveButton("去设置")
                    .build().show();
        }
    }

    @AfterPermissionGranted(RC_REQUEST_CODE_ALL_PERMISSIONS)
    private void afterPermissionGranted() {
        if (EasyPermissions.hasPermissions(this, ALL_PERMISSIONS)) {
            Log.d(TAG, "afterPermissionGranted -> " + "yes");
            requestPermissionsSuccess();
        } else {
            Log.d(TAG, "afterPermissionGranted -> " + "no");
        }
    }

    private void setupPermissions() {
        if (EasyPermissions.hasPermissions(this, ALL_PERMISSIONS)) {
            EasyPermissions.requestPermissions(this, "我们需要获取您的相关权限，否则将无法使用", RC_REQUEST_CODE_ALL_PERMISSIONS, ALL_PERMISSIONS);
        } else {
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    private void requestPermissionsSuccess() {
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }
}
