package ha.thanh.pikerfree.models;

import android.location.Location;
import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

import ha.thanh.pikerfree.constants.Constants;


/**
 * Created by HaVan on 8/27/2017.
 */
@IgnoreExtraProperties
public class User {
    private String id;
    private String name;
    private String address;
    private double lat = 0;
    private double lng = 0;
    private boolean isAdmin = false;
    private String avatarLink = Constants.BASE_STORAGE_URL + "userImages/default_profile.jgp";
    private String password = "";

    public User() {
    }

    public User(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
