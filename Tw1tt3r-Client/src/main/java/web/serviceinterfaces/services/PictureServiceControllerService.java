package web.serviceinterfaces.services;

import dtos.PictureDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PictureServiceControllerService {
    @POST("api/pictureservice/makepicture")
    public Call<PictureDto> makePicture(@Body PictureDto picture);
}
