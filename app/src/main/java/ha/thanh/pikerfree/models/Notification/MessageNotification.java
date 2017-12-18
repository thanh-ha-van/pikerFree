package ha.thanh.pikerfree.models.Notification;

/**
 * Created by HaVan on 12/6/2017.
 */

public class MessageNotification {

    // use this when need to push notification to user when they have new message and they are offline.
    private String body;
    private String from;
    private String to;
    private double timestamp;

    public MessageNotification(String body, String from, String to, double timestamp) {
        this.body = body;
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }

    public MessageNotification() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}
