package ha.thanh.pikerfree.objects;

import java.util.List;

/**
 * Created by HaVan on 8/27/2017.
 */

public class Conversation {
    int idUser1;
    int idUser2;
    List<Message> messages;

    public Conversation(int idUser1, int idUser2, List<Message> messages) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.messages = messages;
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
