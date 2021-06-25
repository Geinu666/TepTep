package WebKit;

import WebKit.Service.LoginService;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private LoginService service;

    public static Retrofit getRetrofit(){
        return new Retrofit();
    }

    private Retrofit(){
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://119.91.130.198/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(LoginService.class);
    }

    public LoginService getService(){
        return service;
    }
}
