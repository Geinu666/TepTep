package WebKit.Bean;

public class OneGame {
    private Integer status;
    private String msg;
    private One data;

    public void setData(One data) {
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

    public One getData() {
        return data;
    }

    public static class One{
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
        private Boolean currentUserLikes;

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getGameId() {
            return gameId;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getCommentCount() {
            return commentCount;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public Integer getInterestCount() {
            return interestCount;
        }

        public void setInterestCount(Integer interestCount) {
            this.interestCount = interestCount;
        }

        public Integer getDownloads() {
            return downloads;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIssuer() {
            return issuer;
        }

        public void setDownloads(Integer downloads) {
            this.downloads = downloads;
        }

        public String getIcon() {
            return icon;
        }

        public void setDisplayDrawings(String displayDrawings) {
            this.displayDrawings = displayDrawings;
        }

        public String getDisplayDrawings() {
            return displayDrawings;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public String getBriefIntro() {
            return briefIntro;
        }

        public void setBriefIntro(String briefIntro) {
            this.briefIntro = briefIntro;
        }

        public Integer getSize() {
            return size;
        }

        public void setAvgScore(Double avgScore) {
            this.avgScore = avgScore;
        }

        public Double getAvgScore() {
            return avgScore;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCurrentUserLikes(Boolean currentUserLikes) {
            this.currentUserLikes = currentUserLikes;
        }

        public Boolean getCurrentUserLikes() {
            return currentUserLikes;
        }
    }
}
