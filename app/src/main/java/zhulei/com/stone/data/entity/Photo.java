package zhulei.com.stone.data.entity;

import android.text.TextUtils;

/**
 * Created by zhulei on 16/6/3.
 */
public class Photo {

    public String localPath;
    public String remotePath;

    public Photo(String localPath, String remotePath){
        this.localPath = localPath;
        this.remotePath = remotePath;
    }

    public boolean isEmpty(){
        return TextUtils.isEmpty(localPath) && TextUtils.isEmpty(remotePath);
    }

}
