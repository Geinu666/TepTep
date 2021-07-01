package WebKit.Service;

import WebKit.Bean.AllBean;
import WebKit.Bean.OneGame;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 获取所有游戏信息
 */
public interface GetGameService {
    @GET("game/all")
    Call<AllBean> getAllGame();

    @GET("game/one")
    Call<OneGame> getOneGame(@Query("gameId") String gameId);
}
