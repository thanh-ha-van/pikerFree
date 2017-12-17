package ha.thanh.pikerfree.models.Notification;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by HaVan on 12/6/2017.
 */
@IgnoreExtraProperties
public class DataPayload {

    // use this when need to push notification to user when they have new message and they are offline.
    private String  type;
    private String dataID;

    public DataPayload(String type, String dataID) {
        this.type = type;
        this.dataID = dataID;
    }

    public DataPayload() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataID() {
        return dataID;
    }

    public void setDataID(String dataID) {
        this.dataID = dataID;
    }
}
