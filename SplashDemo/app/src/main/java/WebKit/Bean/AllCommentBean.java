package WebKit.Bean;

import com.example.splashdemo.Comment;

import java.util.List;

public class AllCommentBean {
    private Integer status;
    private String msg;
    private List<Comment> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public List<Comment> getData() {
        return data;
    }
}
