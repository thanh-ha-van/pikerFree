package ha.thanh.pikerfree.models;

import android.location.Location;
import android.net.Uri;


/**
 * Created by HaVan on 8/27/2017.
 */

public class User {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
