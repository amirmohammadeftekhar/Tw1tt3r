package web.serviceinterfaces.services;

import dtos.TweetDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TweetServiceControllerService {
    @POST(value = "api/tweetservice/maketweet")
    public Call<Void> makeTweet(@Query("text") String text, @Query("parentTweetId") int parentTweetId, @Query("currentPersonId") int currentPersonId, @Query("pictureId") int pictureId);

    @GET(value = "api/tweetservice/gettweet")
    public Call<TweetDto> getTweet(@Query("tweetId") int tweetId);

    @POST(value = "api/tweetservice/report")
    public Call<Void> report(@Query("tweetId") int tweetId, @Query("currentPersonId") int currentPersonId);
}
