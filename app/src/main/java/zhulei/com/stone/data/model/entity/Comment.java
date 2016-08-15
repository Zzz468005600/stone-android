package zhulei.com.stone.data.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhulei on 16/6/19.
 */
public class Comment extends BmobObject {

    private User user;
    private Message message;
    private String content;

    public User getUser() {
        return user;
    }

    public Message getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
