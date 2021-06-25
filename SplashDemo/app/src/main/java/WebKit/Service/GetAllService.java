package WebKit.Service;

import WebKit.Bean.AllBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 获取所有游戏信息
 */
public interface GetAllService {
    @GET("game/all")
    Call<AllBean> getAllGame();
}
