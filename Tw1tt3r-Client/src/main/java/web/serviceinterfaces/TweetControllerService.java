package web.serviceinterfaces;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TweetControllerService {
    @POST(value = "api/tweet/likebuttonaction")
    public Call<Void> likeButtonAction(@Query("tweetId") int tweetId, @Query("currentPersonId") int currentPersonId);

    @POST(value = "api/tweet/savetweetbuttonaction")
    public Call<Void> saveTweetButtonAction(@Query("tweetId") int tweetId, @Query("currentPersonId") int currentPersonId);

}
