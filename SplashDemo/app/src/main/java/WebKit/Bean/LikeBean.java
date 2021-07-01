package WebKit.Bean;

public class LikeBean {
    private Integer status;
    private String msg;
    private Like data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(Like data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public Like getData() {
        return data;
    }

    public static class Like{
        private String details;
        private boolean likes;

        public void setDetails(String details) {
            this.details = details;
        }

        public void setLikes(boolean likes) {
            this.likes = likes;
        }

        public String getDetails() {
            return details;
        }

        public boolean isLikes() {
            return likes;
        }
    }
}
