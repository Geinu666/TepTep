package WebKit.Service;

import com.example.splashdemo.entity.advertisement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface adInterface {
    @GET("version")
    Call<List<advertisement>> getAdMsg();
}
