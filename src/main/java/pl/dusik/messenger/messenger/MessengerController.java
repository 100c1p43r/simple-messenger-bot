package pl.dusik.messenger.messenger;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import pl.dusik.messenger.web.data.Message;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Component
public class MessengerController {
    private final static String TOKEN = "PAGE_TOKEN_HERE";
    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://graph.facebook.com/")
            .client(new OkHttpClient())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public void sendMessage(Message message) {
        MessengerService service = retrofit.create(MessengerService.class);

        try {
            service.sendMessage(TOKEN, message).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
