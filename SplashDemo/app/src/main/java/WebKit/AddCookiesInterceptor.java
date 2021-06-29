package WebKit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
//cookie拦截器
public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        String url = String.valueOf(chain.request().url());
        Log.i("test", url);
        if (url.equals("http://119.91.130.198/api/userLogin")) {
            Log.i("test", "hit login");
            return chain.proceed(builder.build());
        } else {
            String cookie = context.getSharedPreferences("config",
                    context.MODE_PRIVATE).getString("cookie", null);
            if (cookie != null){
                builder.addHeader("Cookie", cookie);
            }
            return chain.proceed(builder.build());
        }
    }

    public AddCookiesInterceptor(Context context){
        this.context = context;
    }
}
