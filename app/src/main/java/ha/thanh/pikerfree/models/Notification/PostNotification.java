package ha.thanh.pikerfree.models.Notification;

/**
 * Created by HaVan on 12/6/2017.
 */

public class PostNotification {


    // use this when need to push notification to user when the post they following
    // (post you write, post you send request) had change
    // the change maybe post status, requesting user list, granted user
    private String body;
    private String from;
    private String to;

    public PostNotification(String body, String from, String to) {
        this.body = body;
        this.from = from;
        this.to = to;
    }

    public PostNotification() {
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
