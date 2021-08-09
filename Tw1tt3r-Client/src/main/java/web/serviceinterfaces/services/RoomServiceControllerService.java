package web.serviceinterfaces.services;

import dtos.RoomDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RoomServiceControllerService {
    @GET(value = "api/roomservice/getroom")
    public Call<RoomDto> getRoom(@Query("roomId") int roomId);

    @POST(value = "api/roomservice/sendmessage")
    public Call<Void> sendMessage(@Query("roomId") int roomId, @Query("currentPersonId") int currentPersonId, @Query("text") String text, @Query("pictureId") int pictureId);
}
