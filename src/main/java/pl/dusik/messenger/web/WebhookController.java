package pl.dusik.messenger.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.dusik.messenger.messenger.MessengerController;
import pl.dusik.messenger.web.data.Entry;
import pl.dusik.messenger.web.data.Event;
import pl.dusik.messenger.web.data.Message;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/webhook")
public class WebhookController {
    private final static String TOKEN = "SUPER_SECRET_TOKEN";

    @Autowired
    private MessengerController messengerController;

    @GetMapping
    public @ResponseBody String getHook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge,
            HttpServletResponse response) {
        if (TOKEN.equals(token) && "subscribe".equals(mode)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return challenge;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "";
        }
    }

    @PostMapping
    public @ResponseBody
    String onEvent(@RequestBody Event event, HttpServletResponse response) {
        if ("page".equals(event.getObject())) {
            messageFromEvent(event).ifPresent(m ->
                messengerController.sendMessage(Message.of(m.getSender().getId(), reverse(m.getMessage().getText()))));
            response.setStatus(HttpStatus.OK.value());
            return "EVENT_RECEIVED";
        }

        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "";
    }


    private static Optional<Message> messageFromEvent(Event event) {
        Message message = null;
        List<Entry> entry = event.getEntry();

        if (isNonEmpty(entry)) {
            List<Message> messaging = entry.get(0).getMessaging();
            if (isNonEmpty(messaging)) {
                message = messaging.get(0);
            }
        }

        return Optional.of(message);
    }

    private static boolean isNonEmpty(List list) {
        return list != null && list.size() > 0;
    }

    private static String reverse(String in)  {
        byte[] buf = in.getBytes();
        int pivot = buf.length / 2;
        int len = buf.length - 1;
        for (int i = 0; i < pivot; i++) {
            buf[i] ^= buf[len-i];
            buf[len-i] ^= buf[i];
            buf[i] ^= buf[len-i];
        }
        return new String(buf);
    }
}
