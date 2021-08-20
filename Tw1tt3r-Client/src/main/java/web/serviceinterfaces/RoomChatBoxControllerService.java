package web.serviceinterfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import web.BaseResponse;

public interface RoomChatBoxControllerService {
    @POST(value = "api/roomchatbox/sendbuttonaction")
    Call<Void> sendButtonAction(@Query("toSend") String toSend, @Query("currentPersonId") int currentPersonId, @Query("roomId") int roomId, @Query("pictureId") int pictureId);

    @GET(value = "api/roomchatbox/roompvaction")
    public Call<BaseResponse> roomPvAction(@Query("currentPersonId") int currentPersonId, @Query("personUserName") String personUserName);

    @GET(value = "api/roomchatbox/roomgroupaction")
    public Call<BaseResponse> roomGroupAction(@Query("currentPersonId") int currentPersonId, @Query("groupName") String groupName);
}
