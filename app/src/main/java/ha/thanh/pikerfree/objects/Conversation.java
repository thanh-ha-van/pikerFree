package ha.thanh.pikerfree.objects;

/**
 * Created by HaVan on 8/27/2017.
 */

public class Conversation {
    private int idUser1;
    private int idUser2;
    private int conversationId;

    public Conversation(int idUser1, int idUser2, int coversationId) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.conversationId = coversationId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }

}
