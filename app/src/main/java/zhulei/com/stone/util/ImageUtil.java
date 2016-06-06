package zhulei.com.stone.util;

import java.util.List;

/**
 * Created by zhulei on 16/6/6.
 */
public class ImageUtil {

    public static String transImages(List<String> images){
        StringBuilder imagesTrans = new StringBuilder();
        if (images != null && !images.isEmpty()){
            for (int i = 0; i < images.size(); i ++){
                if (i == images.size() - 1){
                    imagesTrans.append(images.get(i));
                }else {
                    imagesTrans.append(images.get(i) + "#");
                }
            }
            return imagesTrans.toString();
        }
        return null;
    }

}
