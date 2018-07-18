package zhulei.com.stone.others;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.hawk.BuildConfig;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by zhulei on 16/5/26.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(getApplicationContext());

        Fragmentation.builder()
                .debug(BuildConfig.DEBUG)
                .stackViewMode(Fragmentation.BUBBLE)
                .install();

    }
}
