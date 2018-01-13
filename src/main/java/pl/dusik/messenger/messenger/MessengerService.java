package pl.dusik.messenger.messenger;

import pl.dusik.messenger.web.data.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

public interface MessengerService {
    @POST("/v2.6/me/messages")
    Call<Map<String, String>> sendMessage(@Query("access_token") String token, @Body Message message);
}
