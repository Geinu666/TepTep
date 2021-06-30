package WebKit.Service;

import WebKit.Bean.CommentBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 评论接口
 */
public interface CommentService {
    @FormUrlEncoded
    @POST("comment/addComment")
    Call<CommentBean> postComment(@Field("content") String content,
                                  @Field("gameId") String gameId,
                                  @Field("score") int score);
}
