package WebKit.Service;

import WebKit.Bean.AllCommentBean;
import WebKit.Bean.ChangeContent;
import WebKit.Bean.CommentBean;
import WebKit.Bean.LikeBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 评论接口
 */
public interface CommentService {
    @POST("comment/addComment")
    Call<CommentBean> postComment(@Body CommentBean.Comment comment);

    @GET("comment/all")
    Call<AllCommentBean> getAllComment(@Query("gameId") String gameId);

    @FormUrlEncoded
    @POST("comment/userLikesComment")
    Call<LikeBean> postLikeGame(@Field("commentId") String commentId);

    @FormUrlEncoded
    @POST("comment/content")
    Call<ChangeContent> postChangeContent(@Field("commentId") String commentId,
                                          @Field("content") String content,
                                          @Field("score") Integer score);
}
