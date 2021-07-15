package WebKit.Service;

import com.example.teptep.entity.Login;
import com.example.teptep.entity.Register;

import WebKit.Bean.Key;
import WebKit.Bean.LoginBean;
import retrofit2.Call;
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

    @FormUrlEncoded
    @POST("userRegister")
    Call<LoginBean> postRegister(@Field("nickname") String nickname,
                                 @Field("password") String password,
                                 @Field("username") String username);

    @GET("publicKey")
    Call<Key> getKey();

    @POST("encryptedLogin")
    Call<LoginBean> encryptedLogin(@Body Login body);

    @POST("encryptedRegister")
    Call<LoginBean> encryptedRegister(@Body Register body);
}
