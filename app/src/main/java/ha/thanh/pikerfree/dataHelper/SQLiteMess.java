package ha.thanh.pikerfree.dataHelper;

/**
 * Created by HaVan on 8/27/2017.
 */

public class SQLiteMess {

    private int id;
    private String conversationId;
    private int lastMessId;

    public SQLiteMess() {
    }

    public SQLiteMess(int id, String conversationId, int lastMessId) {
        this.id = id;
        this.conversationId = conversationId;
        this.lastMessId = lastMessId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getLastMessId() {
        return lastMessId;
    }

    public void setLastMessId(int lastMessId) {
        this.lastMessId = lastMessId;
    }
}
