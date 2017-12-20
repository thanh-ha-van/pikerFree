package ha.thanh.pikerfree.dataHelper;

/**
 * Created by HaVan on 12/6/2017.
 */

public class SQLiteNotification {

    // use this when need to push notification to user when they have new follower and they are offline.
    private int id;
    private String mess;
    private int type;
    private String dataID;
    private int isRead;
    private  boolean isSelected = false;

    SQLiteNotification(int id, String mess, int type, String dataID, int isRead) {
        this.id = id;
        this.mess = mess;
        this.type = type;
        this.dataID = dataID;
        this.isRead = isRead;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public SQLiteNotification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDataID() {
        return dataID;
    }

    public void setDataID(String dataID) {
        this.dataID = dataID;
    }

    public int isRead() {
        return isRead;
    }

    public void setRead(int read) {
        isRead = read;
    }
}
