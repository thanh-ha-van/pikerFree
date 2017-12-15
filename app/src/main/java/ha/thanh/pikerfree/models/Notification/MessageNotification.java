package ha.thanh.pikerfree.models.Notification;

/**
 * Created by HaVan on 12/6/2017.
 */

public class MessageNotification {

    // use this when need to push notification to user when they have new message and they are offline.
    private String body;
    private String from;
    private String to;

    public MessageNotification(String body, String from, String to) {
        this.body = body;
        this.from = from;
        this.to = to;
    }

    public MessageNotification() {
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getBody() {
        return body;
    }
}
