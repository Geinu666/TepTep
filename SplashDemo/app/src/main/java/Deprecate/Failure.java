package Deprecate;

/**
 * 用处不详，即将加入垃圾堆
 */
public class Failure {
    private Integer status;
    private String msg;
    private mResponse data;

    public void setData(mResponse data) {
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

    public mResponse getData() {
        return data;
    }

    public static class mResponse{
        private String response;

        public void setResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }
    }
}
