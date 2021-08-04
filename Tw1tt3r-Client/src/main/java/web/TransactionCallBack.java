package web;

import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class TransactionCallBack<T extends BaseResponse> implements Callback<T> {

        public abstract void DoOnResponse(Response<T> response);

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
                Platform.runLater(() -> DoOnResponse(response));
        }

        @Override
        public void onFailure(Call<T> call, Throwable throwable) {

        }

}
