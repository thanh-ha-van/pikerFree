package ha.thanh.pikerfree.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.security.Timestamp;
import java.util.ArrayList;

import ha.thanh.pikerfree.constants.Constants;

/**
 * Created by HaVan on 8/27/2017.
 */
@IgnoreExtraProperties
public class Post {
    private int postId;
    private String title = "";
    private String description = "No description founded";
    private ArrayList<String> linkImages;
    private int status = Constants.STATUS_OPEN;
    private int ownerId;
    private double lat = 0;
    private double lng  = 0;
    private Timestamp timePosted;
    private String topic = "";

    public Post() {
    }

    public Post(int postId, String title, String description, int ownerId) {
        this.postId = postId;
        this.title = title;
        this.description = description;
    }

}
