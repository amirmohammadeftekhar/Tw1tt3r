package web.serviceinterfaces.services;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ActionServiceControllerService {
    @POST(value = "api/actionservice/block")
    public Call<Void> block(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);
}
