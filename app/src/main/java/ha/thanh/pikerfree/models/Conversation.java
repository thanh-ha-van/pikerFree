package ha.thanh.pikerfree.models;


import android.support.annotation.NonNull;

public class Conversation implements Comparable<Conversation> {

    private String idUser1;
    private String idUser2;
    private String conversationId;
    private int lastMessId;
    private int lastUser1Mess;
    private int lastUser2Mess;
    private long lastMessTime;


    public Conversation() {
    }

    public Conversation(String idUser1,
                        String idUser2,
                        String conversationId,
                        int lastMessId,
                        int lastUser1Mess,
                        int lastUser2Mess,
                        long lastMessTime) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.conversationId = conversationId;
        this.lastMessId = lastMessId;
        this.lastUser1Mess = lastUser1Mess;
        this.lastUser2Mess = lastUser2Mess;
        this.lastMessTime = lastMessTime;
    }

    @Override
    public int compareTo(@NonNull Conversation f) {
        if (this.lastMessTime > f.lastMessTime) {
            return -1;
        } else if (this.lastMessTime < f.lastMessTime) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getLastUser1Mess() {
        return lastUser1Mess;
    }

    public void setLastUser1Mess(int lastUser1Mess) {
        this.lastUser1Mess = lastUser1Mess;
    }

    public int getLastUser2Mess() {
        return lastUser2Mess;
    }

    public void setLastUser2Mess(int lastUser2Mess) {
        this.lastUser2Mess = lastUser2Mess;
    }

    public long getLastMessTime() {
        return lastMessTime;
    }

    public void setLastMessTime(long lastMessTime) {
        this.lastMessTime = lastMessTime;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
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
