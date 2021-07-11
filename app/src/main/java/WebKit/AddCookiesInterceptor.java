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
        HashSet<String> perferences = (HashSet) context.getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookie", null);
        if (perferences != null) {
            for (String cookie : perferences) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }

    public AddCookiesInterceptor(Context context){
        this.context = context;
    }
}

