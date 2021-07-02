package WebKit.Service;

import WebKit.Bean.AllCommentBean;
import WebKit.Bean.CommentBean;
import WebKit.Bean.LikeBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 评论接口
 */
public interface CommentService {
    @FormUrlEncoded
    @POST("comment/addComment")
    Call<CommentBean> postComment(@Field("content") String content,
                                  @Field("gameId") String gameId,
                                  @Field("score") int score);

    @GET("comment/all")
    Call<AllCommentBean> getAllComment(@Query("gameId") String gameId);

    @FormUrlEncoded
    @POST("comment/userLikesComment")
    Call<LikeBean> postLikeGame(@Field("commentId") String commentId);
}
