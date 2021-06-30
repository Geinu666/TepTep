package WebKit.Bean;

/**
 * 解析评论请求
 */
public class CommentBean {
    private Integer status;
    private String msg;
    private Comment data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setData(Comment data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Comment getData() {
        return data;
    }

    public static class Comment {
        private String commentId;
        private String gameId;
        private String userId;
        private String content;
        private Integer score;
        private Integer likesCount;
        private String commentAt;

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setCommentAt(String commentAt) {
            this.commentAt = commentAt;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setLikesCount(Integer likesCount) {
            this.likesCount = likesCount;
        }

        public String getGameId() {
            return gameId;
        }

        public String getUserId() {
            return userId;
        }

        public Integer getLikesCount() {
            return likesCount;
        }

        public Integer getScore() {
            return score;
        }

        public String getCommentAt() {
            return commentAt;
        }

        public String getCommentId() {
            return commentId;
        }

        public String getContent() {
            return content;
        }
    }
}
