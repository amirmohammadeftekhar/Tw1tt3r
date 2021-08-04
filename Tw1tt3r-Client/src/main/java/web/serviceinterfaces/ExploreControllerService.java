package web.serviceinterfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import utility.TimeLineParent;
import web.BaseResponse;

public interface ExploreControllerService {
    @POST(value = "api/explore/searchbuttonaction")
    public Call<BaseResponse> searchButtonAction(@Query("userName") String userName, @Query("currentPersonId") int currentPersonId);

    @POST(value = "api/explore/gettweetlist")
    public Call<BaseResponse> getTweetList(@Query("currentPersonId") int currentPersonId, @Body TimeLineParent timeLineParent);
}
