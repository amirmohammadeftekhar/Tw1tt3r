package web.serviceinterfaces.services;

import dtos.PersonDto;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PersonServiceControllerService {
    @POST("/api/personservice/getperson")
    public Call<PersonDto> getPerson(@Query("id") int id);
    @POST("/api/personservice/updatelastseen")
    public Call<Void> updateLastSeen(@Query("id") int id);
}
