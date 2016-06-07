package zhulei.com.stone.util;

import android.text.TextUtils;

import java.util.ArrayList;
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

    public static ArrayList<String> getImages(String images){
        ArrayList<String> imagesList = new ArrayList<>();
        if (!TextUtils.isEmpty(images)){
            String[] imageArray = images.split("#");
            for (int i = 0; i < imageArray.length; i ++){
                imagesList.add(imageArray[i]);
            }
            return imagesList;
        }
        return null;
    }

}
