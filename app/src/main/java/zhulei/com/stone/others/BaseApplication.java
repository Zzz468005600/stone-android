package zhulei.com.stone.others;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

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

    }
}
