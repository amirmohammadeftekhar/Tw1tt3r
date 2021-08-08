package web.serviceinterfaces;

import dtos.PersonDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import web.BaseResponse;

import java.util.List;

public interface ProfileControllerService {
    @GET(value = "api/profile/getblockingpersons")
    Call<List<PersonDto>> getBlockingPersons(@Query("currentPersonId") int currentPersonId);

    @GET(value = "api/profile/getfollowerspersons")
    Call<List<PersonDto>> getFollowersPersons(@Query("currentPersonId") int currentPersonId);

    @GET(value = "api/profile/getfollowingspersons")
    Call<List<PersonDto>> getFollowingspersons(@Query("currentPersonId") int currentPersonId);

    @GET(value = "api/profile/reload")
    Call<BaseResponse> reload(@Query("currentPersonId") int currentPersonId);
}
