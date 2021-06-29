package WebKit;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit getRetrofit(Context context){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
//                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://119.91.130.198/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
    private RetrofitFactory(){
    }

    public static LoginService getLoginService(Context context){
        Retrofit retrofit = getRetrofit(context);
        return retrofit.create(LoginService.class);
    }
}
