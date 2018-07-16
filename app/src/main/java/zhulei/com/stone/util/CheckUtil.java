package zhulei.com.stone.util;

import android.text.TextUtils;

/**
 * Created by zhulei on 16/5/28.
 */
public final class CheckUtil {

    public static boolean isValidMobile(CharSequence mobile){
        if (TextUtils.isEmpty(mobile) || 11 != mobile.toString().trim().length() || !mobile.toString().trim().startsWith("1")) {
            return false;
        }
        return true;
    }

}
