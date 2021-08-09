package web.serviceinterfaces;

import dtos.TweetDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import utility.TimeLineParent;

import java.util.List;

public interface TimeLineControllerService {
    @POST(value = "api/timeline/gettweetlist")
    public Call<List<TweetDto>> getTweetList(@Query("currentPersonId") int currentPersonId, @Body TimeLineParent timeLineParent, @Query("t") int t);
}
