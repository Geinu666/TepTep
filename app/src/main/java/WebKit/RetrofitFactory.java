package WebKit;

import android.content.Context;

import WebKit.Service.CommentService;
import WebKit.Service.GameService;
import WebKit.Service.GetGameService;
import WebKit.Service.LoginService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit getRetrofit(Context context){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://119.91.130.198/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    public static Retrofit getLoginRetrofit(Context context){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
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

    public static LoginService getSpecialService(Context context) {
        Retrofit retrofit = getLoginRetrofit(context);
        return retrofit.create(LoginService.class);
    }

    public static CommentService getCommentService(Context context){
        Retrofit retrofit = getRetrofit(context);
        return retrofit.create(CommentService.class);
    }

    public static GameService getGameService(Context context) {
        Retrofit retrofit = getRetrofit(context);
        return retrofit.create(GameService.class);
    }

    public static GetGameService getGetGameService(Context context) {
        Retrofit retrofit = getRetrofit(context);
        return retrofit.create(GetGameService.class);
    }
}
