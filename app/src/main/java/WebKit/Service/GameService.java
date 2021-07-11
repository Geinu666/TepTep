package WebKit.Service;

import WebKit.Bean.LikeBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GameService {
    @FormUrlEncoded
    @POST("game/userLikesGame")
    Call<LikeBean> postLikeGame(@Field("gameId") String gameId);
}
