package web.serviceinterfaces.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ActionServiceControllerService {
    @POST(value = "api/actionservice/block")
    public Call<Void> block(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @GET(value = "api/actionservice/isblocking")
    public Call<Boolean> isBlocking(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @GET(value = "api/actionservice/isfollowing")
    public Call<Boolean> isFollowing(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @GET(value = "api/actionservice/issourcemuting")
    public Call<Boolean> isSourceMuting(@Query("source") int source, @Query("destination") int destination);

    @GET(value = "api/actionservice/getfollowerspersonscount")
    public Call<Integer> getFollowersPersonsCount(@Query("personId") int personId);

    @GET(value = "api/actionservice/getfollowingspersonscount")
    public Call<Integer> getFollowingsPersonsCount(@Query("personId") int personId);

    @POST(value = "api/actionservice/makefollow")
    public Call<Void> makeFollow(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);

    @POST(value = "api/actionservice/makereject")
    public Call<Void> makeReject(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);


    @POST(value = "api/actionservice/deleteaction")
    public Call<Void> deleteAction(@Query("actionId") int actionId);
}
