package web;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class TransactionServiceGenerator {

    private static TransactionServiceGenerator instance;

    @Setter
    @Getter
    private static String token;

    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private TransactionServiceGenerator() {
        init();
    }

    public static TransactionServiceGenerator getInstance() {

        if (instance == null) {
            instance = new TransactionServiceGenerator();
        }
        return instance;
    }

    private void init() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8080/")
                .addConverterFactory(JacksonConverterFactory.create());
//                .addConverterFactory(GsonConverterFactory.create());

        httpClient.connectTimeout(15, TimeUnit.SECONDS);
        httpClient.readTimeout(50, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder();

            requestBuilder.addHeader("Authorization", "Bearer " + token);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        OkHttpClient client = httpClient.build();
        retrofit = builder.client(client).build();
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

