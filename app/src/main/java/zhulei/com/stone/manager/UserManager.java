package zhulei.com.stone.manager;

import com.orhanobut.hawk.Hawk;

import cn.bmob.v3.BmobUser;

/**
 * Created by zhulei on 16/5/28.
 */
public class UserManager {

    private static UserManager sInstance;

    public static final String OBJECT_ID = "objectId";
    public static final String USER_NAME = "userName";
    public static final String PHONE_NUMBER = "phoneNumber";

    private String objectId;
    private String userName;
    private String phoneNumber;

    private UserManager() {
        objectId = Hawk.get(OBJECT_ID);
        userName = Hawk.get(USER_NAME);
        phoneNumber = Hawk.get(PHONE_NUMBER);
    }

    public static UserManager instance() {
        if (sInstance == null) {
            sInstance = new UserManager();
        }
        return sInstance;
    }

    public void saveUser(BmobUser user){
        if (user == null){
            return;
        }
        objectId = user.getObjectId();
        if (objectId != null){
            Hawk.put(OBJECT_ID, objectId);
        }else {
            Hawk.remove(OBJECT_ID);
        }
        userName = user.getUsername();
        if (userName != null){
            Hawk.put(USER_NAME, userName);
        }else {
            Hawk.remove(USER_NAME);
        }
        phoneNumber = user.getMobilePhoneNumber();
        if (phoneNumber != null){
            Hawk.put(PHONE_NUMBER, phoneNumber);
        }else {
            Hawk.remove(PHONE_NUMBER);
        }
    }

    public void reset(){
        objectId = null;
        userName = null;
        phoneNumber = null;
        Hawk.remove(OBJECT_ID, USER_NAME, PHONE_NUMBER);
    }

    public boolean hasLogin(){
        return objectId != null;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
