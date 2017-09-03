package ha.thanh.pikerfree.objects;

/**
 * Created by HaVan on 8/27/2017.
 */

public class Message {
    private String author;
    private String text;
    private long time;

    public Message(String author, String text, long time) {
        this.author = author;
        this.text = text;
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
