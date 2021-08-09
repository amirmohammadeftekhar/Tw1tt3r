package web.serviceinterfaces.services;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CategoryServiceControllerService {
    @POST(value = "api/categoryservice/sendmessage")
    public Call<Void> sendMessage(@Query("categoryId") int categoryId, @Query("currentPersonId") int currentPersonId, @Query("text") String text);
}
