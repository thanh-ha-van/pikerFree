package ha.thanh.pikerfree.models;

import android.location.Location;
import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;


/**
 * Created by HaVan on 8/27/2017.
 */
@IgnoreExtraProperties
public class User {
    private String id;
    private String name;
    private String address;
    private Location location;
    private int[] postIds;
    private boolean isAdmin;
    private Uri avatarLink;
    private boolean isMale;
    private String password;

    public User() {
    }

    public User(String id, String name, String address, Location location, int[] postIds, boolean isAdmin, Uri avatarLink) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = location;
        this.postIds = postIds;
        this.isAdmin = isAdmin;
        this.avatarLink = avatarLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int[] getPostIds() {
        return postIds;
    }

    public void setPostIds(int[] postIds) {
        this.postIds = postIds;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Uri getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(Uri avatarLink) {
        this.avatarLink = avatarLink;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
