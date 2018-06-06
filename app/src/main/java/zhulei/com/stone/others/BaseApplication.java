package zhulei.com.stone.others;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import cn.bmob.v3.Bmob;

/**
 * Created by zhulei on 16/5/26.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Hawk.init(getApplicationContext())
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .build();

        Bmob.initialize(this, "e4d8d7779e054f3fd8b228f060ce1943");

    }
}
