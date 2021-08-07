package web.serviceinterfaces.services;

import dtos.MessageDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageServiceControllerService {
    @POST(value = "api/messageservice/savemessage")
    public Call<Void> saveMessage(@Body MessageDto message);
}
