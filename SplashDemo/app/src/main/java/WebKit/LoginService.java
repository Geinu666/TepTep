package WebKit;

import WebKit.Bean.LoginBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface LoginService {
    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginBean> postLogin(@Field("username") String name, @Field("password") String password);

    @GET("userMessage")
    Call<LoginBean> getUserMessage();

}
