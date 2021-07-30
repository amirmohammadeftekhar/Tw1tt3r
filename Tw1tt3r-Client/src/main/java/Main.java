import com.example.tw1tt3rServer.controller.secureController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Dto;
import dtos.StringDto;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import web.BaseResponse;
import web.TransactionCallBack;
import web.TransactionServiceGenerator;
import web.services.EntryControllerService;
import web.services.PersonWebService;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
    }
}
