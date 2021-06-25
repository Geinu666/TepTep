package WebKit.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllBean {

    private Integer status;
    private String msg;
    private List<GameBean> data;

    public void setData(List<GameBean> data) {
        this.data = data;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getStatus() {
        return status;
    }

    public List<GameBean> getData() {
        return data;
    }

    public static class GameBean {
        private String gameId;
        private String name;
        private Integer size;
        private String issuer;
        private Integer downloads;
        private Double avgScore;
        private Integer commentCount;
        private Integer interestCount;
        private String icon;
        private String displayDrawings;
        private String briefIntro;

        public void setName(String name) {
            this.name = name;
        }

        public void setAvgScore(Double avgScore) {
            this.avgScore = avgScore;
        }

        public void setBriefIntro(String briefIntro) {
            this.briefIntro = briefIntro;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public void setDisplayDrawings(String displayDrawings) {
            this.displayDrawings = displayDrawings;
        }

        public void setDownloads(Integer downloads) {
            this.downloads = downloads;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setInterestCount(Integer interestCount) {
            this.interestCount = interestCount;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public Double getAvgScore() {
            return avgScore;
        }

        public Integer getSize() {
            return size;
        }

        public String getBriefIntro() {
            return briefIntro;
        }

        public String getDisplayDrawings() {
            return displayDrawings;
        }

        public String getGameId() {
            return gameId;
        }

        public String getIcon() {
            return icon;
        }

        public String getIssuer() {
            return issuer;
        }

        public Integer getDownloads() {
            return downloads;
        }

        public Integer getInterestCount() {
            return interestCount;
        }

        public Integer getCommentCount() {
            return commentCount;
        }
    }
}
