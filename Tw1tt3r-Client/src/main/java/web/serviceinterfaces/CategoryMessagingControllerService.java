package web.serviceinterfaces;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CategoryMessagingControllerService {
    @POST(value = "api/categorymessaging/sendmessage")
    public Call<Void> sendMessage(@Query("categoryId") int categoryId, @Query("currentPersonId") int currentPersonId, @Query("toSend") String toSend);
}
