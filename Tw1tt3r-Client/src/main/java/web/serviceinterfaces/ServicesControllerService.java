package web.serviceinterfaces;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServicesControllerService {
    @POST(value = "api/services/block")
    public Call<Void> block(@Query("currentPersonId") int currentPersonId, @Query("personId") int personId);
}
