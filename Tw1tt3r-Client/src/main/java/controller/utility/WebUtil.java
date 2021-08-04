package controller.utility;

import dtos.PersonDto;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import web.TransactionServiceGenerator;
import web.serviceinterfaces.ServicesControllerService;
import web.serviceinterfaces.services.PersonServiceControllerService;

public class WebUtil {
    @SneakyThrows
    public static PersonDto getPerson(int id){
        return((PersonDto)TransactionServiceGenerator.getInstance().createService(PersonServiceControllerService.class).getPerson(id).execute().body().getDto());
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
}
