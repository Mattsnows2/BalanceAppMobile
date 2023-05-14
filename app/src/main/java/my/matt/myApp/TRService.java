package my.matt.myApp;

import com.google.gson.JsonObject;

import my.matt.myApp.models.TRModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TRService {


    @GET("live?api_key=85fe0621a602474baaa3241fd85a18b9&base=USD&target=EUR,CAD")
    Call<TRModel> getData();
}
