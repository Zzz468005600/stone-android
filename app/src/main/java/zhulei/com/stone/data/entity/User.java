package zhulei.com.stone.data.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by zhulei on 16/5/30.
 */
public class User extends BmobUser {

    private String header;

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
