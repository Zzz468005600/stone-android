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

    @GET(PATH_LATEST_NEWS)
    Observable<Map> getLatestNews();
}