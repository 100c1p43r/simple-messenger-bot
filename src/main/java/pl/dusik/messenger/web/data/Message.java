package pl.dusik.messenger.web.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private MessageRecipient recipient;
    private MessageSender sender;
    private MessageBody message;

    public Message() {
    }

    private Message(MessageRecipient recipient, MessageBody message) {
        this.recipient = recipient;
        this.message = message;
    }

    public static Message of(String recipient, String body) {
        MessageRecipient r = new MessageRecipient();
        r.setId(recipient);
        MessageBody b = new MessageBody();
        b.setText(body);
        return new Message(r, b);
    }

    public MessageRecipient getRecipient() {
        return recipient;
    }

    public void setRecipient(MessageRecipient recipient) {
        this.recipient = recipient;
    }

    public MessageSender getSender() {
        return sender;
    }

    public void setSender(MessageSender sender) {
        this.sender = sender;
    }

    public MessageBody getMessage() {
        return message;
    }

    public void setMessage(MessageBody message) {
        this.message = message;
    }
}
