package zhulei.com.stone.data.manager;

import com.orhanobut.hawk.Hawk;

import zhulei.com.stone.data.model.entity.User;

/**
 * Created by zhulei on 16/5/28.
 */
public class UserManager {

    private static UserManager sInstance;

    public static final String OBJECT_ID = "objectId";
    public static final String USER_NAME = "userName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String USER_HEADER = "userHeader";

    private String objectId;
    private String userName;
    private String phoneNumber;
    private String userHeader;

    private UserManager() {
        objectId = Hawk.get(OBJECT_ID);
        userName = Hawk.get(USER_NAME, "");
        phoneNumber = Hawk.get(PHONE_NUMBER, "");
        userHeader = Hawk.get(USER_HEADER);
    }

    public static UserManager instance() {
        if (sInstance == null) {
            sInstance = new UserManager();
        }
        return sInstance;
    }

    public void saveUser(User user){

    }

    public void updateUserHeader(String header){
        userHeader = header;
        if (userHeader == null){
            Hawk.remove(USER_HEADER);
        }else {
            Hawk.put(USER_HEADER, userHeader);
        }
    }

    public void reset(){
        objectId = null;
        userName = null;
        phoneNumber = null;
        userHeader = null;
        Hawk.remove(OBJECT_ID, USER_NAME, PHONE_NUMBER, USER_HEADER);
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

    public String getUserHeader() {
        return userHeader;
    }

    public void logOut(){
        reset();
    }
}
