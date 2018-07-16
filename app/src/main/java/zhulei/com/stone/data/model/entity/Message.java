package zhulei.com.stone.data.model.entity;

import java.io.Serializable;

/**
 * Created by zhulei on 16/6/6.
 */
public class Message implements Serializable {

    private User user;
    private String text;
    private String images;

    public void setImages(String images) {
        this.images = images;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public String getImages() {
        return images;
    }

    public User getUser() {
        return user;
    }
}
