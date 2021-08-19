package web.serviceinterfaces;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MessagingMainMenuControllerService {
    @POST(value = "api/messagingmainmenu/openchatbox")
    public Call<Void> openChatBox(@Query("currentPersonId") int currentPersonId, @Query("roomId") int roomId);

    @POST(value = "api/messagingmainmenu/deleteroom")
    public Call<Void> deleteRoom(@Query("currentPersonId") int currentPersonId, @Query("roomId") int roomId);

}
