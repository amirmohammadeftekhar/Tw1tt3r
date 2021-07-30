package web.services;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import web.BaseResponse;

public interface EntryControllerService {
    @POST("/signin")
    public Call<BaseResponse> login(@Query("userName") String userName, @Query("password") String password);

}
