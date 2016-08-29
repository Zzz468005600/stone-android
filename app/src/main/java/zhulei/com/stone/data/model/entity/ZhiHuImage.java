package zhulei.com.stone.data.model.entity;

import java.util.Map;

/**
 * Created by zhulei on 16/8/29.
 */
public class ZhiHuImage {

    private String text;
    private String img;

    public static ZhiHuImage parse(Map map){
        if (map == null){
            return null;
        }
        ZhiHuImage image = new ZhiHuImage();
        image.text = (String) map.get("text");
        image.img = (String) map.get("img");
        return image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
