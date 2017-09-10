package ha.thanh.pikerfree.models;

import android.location.Location;
import android.net.Uri;

import java.security.Timestamp;
import java.util.ArrayList;

/**
 * Created by HaVan on 8/27/2017.
 */

public class Post {
    private int postId;
    private String title = "";
    private String description = "No description founded";
    private ArrayList<Uri> linkImages;
    private int status;
    private int ownerId;
    private Location location;
    private Timestamp timePosted;
    private String topic = "";

    public Post() {

    }

    public Post(int postId, String title, String description, int ownerId) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
    }

    public Post(int postId, String title, String description, ArrayList<Uri> linkImages, int status, int ownerId, Location location, Timestamp timePosted, String topic) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.linkImages = linkImages;
        this.status = status;
        this.ownerId = ownerId;
        this.location = location;
        this.timePosted = timePosted;
        this.topic = topic;
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

    public ArrayList<Uri> getLinkImages() {
        return linkImages;
    }

    public void setLinkImages(ArrayList<Uri> linkImages) {
        this.linkImages = linkImages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Timestamp getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Timestamp timePosted) {
        this.timePosted = timePosted;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
