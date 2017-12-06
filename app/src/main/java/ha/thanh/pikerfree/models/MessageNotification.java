package ha.thanh.pikerfree.models;

/**
 * Created by HaVan on 12/6/2017.
 */

public class MessageNotification {private long timestamp;

    private String body;
    private String from;
    private String to;

    public MessageNotification(long timestamp, String body, String from, String to) {
        this.timestamp = timestamp;
        this.body = body;
        this.from = from;
        this.to = to;
    }

    public MessageNotification() {
    }

    public long getTimestamp() {
        return timestamp;
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
