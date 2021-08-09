package web.serviceinterfaces.services;

import dtos.PictureDto;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface PictureServiceControllerService {
    @Multipart
    @POST("api/pictureservice/makepicture")
    public Call<PictureDto> makePicture(@Part MultipartBody.Part file);

    @GET("api/pictureservice/getpicture")
    public Call<ResponseBody> getPicture(@Query("pictureId") int pictureId);
}
