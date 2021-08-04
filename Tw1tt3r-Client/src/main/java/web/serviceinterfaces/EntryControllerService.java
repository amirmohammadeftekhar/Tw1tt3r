package web.serviceinterfaces;

import dtos.PersonIniDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import web.BaseResponse;

public interface EntryControllerService {
    @POST("/signin")
    public Call<BaseResponse> signin(@Query("userName") String userName, @Query("password") String password);
    @POST("/signup")
    public Call<BaseResponse> signup(@Body PersonIniDto personIniDto);

}
