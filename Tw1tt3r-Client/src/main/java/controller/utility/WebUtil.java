package controller.utility;

import dtos.PersonDto;
import dtos.RoomDto;
import dtos.TweetDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.ServicesControllerService;
import web.serviceinterfaces.services.ActionServiceControllerService;
import web.serviceinterfaces.services.PersonServiceControllerService;
import web.serviceinterfaces.services.RoomServiceControllerService;
import web.serviceinterfaces.services.TweetServiceControllerService;

import java.io.IOException;

public class WebUtil {
    public static PersonDto getPerson(int id) throws IOException {
        return(TransactionServiceGenerator.getInstance().createService(PersonServiceControllerService.class).getPerson(id).execute().body());
    }

    public static void updateLastSeen(int id){
        TransactionServiceGenerator.getInstance().createService(PersonServiceControllerService.class).updateLastSeen(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    public static void block(int currentPersonId, int personId){
        TransactionServiceGenerator.getInstance().createService(ServicesControllerService.class).block(currentPersonId, personId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    public static void makeFollow(int currentPersonId, int personId){
        TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).makeFollow(currentPersonId, personId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    public static void deleteAction(int actionId){
        TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).deleteAction(actionId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    public static void makeReject(int currentPersonId, int personId){
        TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).makeReject(currentPersonId, personId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    public static void report(int tweetId, int currentPersonId){
        TransactionServiceGenerator.getInstance().createService(TweetServiceControllerService.class).report(tweetId, currentPersonId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    public static void makeMute(int currentPersonId, int personId){
        TransactionServiceGenerator.getInstance().createService(ActionServiceControllerService.class).makeMute(currentPersonId, personId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
            }
        });
    }

    public static RoomDto getRoom(int roomId) throws IOException {
        return(TransactionServiceGenerator.getInstance().createService(RoomServiceControllerService.class).getRoom(roomId).execute().body());
    }

    public static TweetDto getTweet(int tweetId) throws IOException {
        return(TransactionServiceGenerator.getInstance().createService(TweetServiceControllerService.class).getTweet(tweetId).execute().body());
    }

}
