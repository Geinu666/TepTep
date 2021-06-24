package WebKit;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.splashdemo.MainActivity;
import com.example.splashdemo.SharedPreferencesUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

//Cookie拦截器
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    /**
     * 首次请求
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header: originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferences.Editor config = context.getSharedPreferences("config",
                    context.MODE_PRIVATE).edit();
            config.putStringSet("cookie", cookies);
            config.commit();
        }
        return originalResponse;
    }

    public ReceivedCookiesInterceptor(Context context){
        this.context = context;
    }
}
