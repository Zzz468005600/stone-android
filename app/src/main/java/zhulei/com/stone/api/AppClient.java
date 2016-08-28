package zhulei.com.stone.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zhulei.com.stone.BuildConfig;

/**
 * Created by zhulei on 16/7/31.
 */
public class AppClient {
    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;

    private static void initClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (builder.interceptors() != null){
            builder.interceptors().clear();
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String query = request.url().query();
                Headers headers = request.headers();
                //可以统一处理头部信息
                return chain.proceed(request);
            }
        })
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS);
        //DEBUG模式下配Log
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        mOkHttpClient = builder.build();
    }

    public static Retrofit retrofit(){
        if (mRetrofit == null){
            initClient();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public interface ApiCallback<T> {

        void onSuccess(T result);

        void onFail(String message);

    }

}
