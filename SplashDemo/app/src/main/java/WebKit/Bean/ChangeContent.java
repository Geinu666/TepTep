package WebKit.Bean;

public class ChangeContent {
    private Integer status;
    private String msg;
    private String data;

    public void setData(String data) {
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

    public String getData() {
        return data;
    }
}
