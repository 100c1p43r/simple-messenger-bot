package pl.dusik.messenger.web.data;

import java.util.List;

public class Entry {
    private long id;
    private long time;
    private List<Message> messaging;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<Message> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<Message> messaging) {
        this.messaging = messaging;
    }
}
