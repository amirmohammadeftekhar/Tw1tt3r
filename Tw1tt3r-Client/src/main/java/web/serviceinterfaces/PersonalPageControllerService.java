package web.serviceinterfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import web.BaseResponse;

public interface PersonalPageControllerService {
    @POST(value = "api/personalpage/blockbuttonaction")
    public Call<Void> blockButtonAction(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @GET(value = "api/personalpage/getfollowstatus")
    public Call<BaseResponse> getFollowStatus(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @POST(value = "api/personalpage/followbuttonaction")
    public Call<Void> followButtonAction(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @POST(value = "api/personalpage/mutebuttonaction")
    public Call<Void> muteButtonAction(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @GET(value = "api/personalpage/messagebuttonaction")
    public Call<BaseResponse> messageButtonAction(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

}
