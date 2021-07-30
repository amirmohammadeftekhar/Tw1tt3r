package web;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class TransactionCallBack<T extends BaseResponse> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
/*
                if (response.code() == 403) {
                        TransactionUtil.clearToken();
                }
*/
        }

        @Override
        public void onFailure(Call<T> call, Throwable throwable) {

        }

}
