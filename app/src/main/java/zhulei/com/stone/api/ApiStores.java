package zhulei.com.stone.api;

import java.util.Map;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhulei on 16/8/3.
 */
public interface ApiStores {
    String METHOD_GET = "GET";

    String PATH_LATEST_NEWS = "news/latest";
    String PATH_START_IMAGE = "start-image/1080*1776";

    @GET(PATH_LATEST_NEWS)
    Observable<Map> getLatestNews();
    @GET(PATH_START_IMAGE)
    Observable<Map> getZhiHu();
}
