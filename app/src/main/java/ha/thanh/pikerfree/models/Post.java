package ha.thanh.pikerfree.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.constants.Constants;

/**
 * Created by HaVan on 8/27/2017.
 */
@IgnoreExtraProperties
public class Post {
    private int postId;
    private String title = "No title found";
    private String description = "No description found";
    private List<String> requestingUser;
    private String grantedUser;


    private int status = Constants.STATUS_OPEN;
    private String ownerId;
    private double lat = 0;
    private double lng = 0;
    private long timePosted;
    private String category = "Unknown";

    public Post() {
    }


    public Post(int postId, String title, String description, String ownerId, double lat, double lng, long timePosted, String category) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
        this.lat = lat;
        this.lng = lng;
        this.timePosted = timePosted;
        this.category = category;
        requestingUser = new ArrayList<>();
    }
    public List<String> getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(List<String> requestingUser) {
        this.requestingUser = requestingUser;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
