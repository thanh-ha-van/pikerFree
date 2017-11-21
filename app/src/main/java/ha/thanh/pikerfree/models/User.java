package ha.thanh.pikerfree.models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class User {

    private String id;
    private String name;
    private String address;
    private double lat = 0;
    private double lng = 0;
    private boolean isAdmin = false;
    private String avatarLink = "userImages/default_profile.jgp";
    private String email = "";
    private ArrayList<Integer> posts;
    private ArrayList<Integer> conversations;
    private ArrayList<String> mess;

    public User() {
    }

    public User(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ArrayList<Integer> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<Integer> conversations) {
        this.conversations = conversations;
    }

    public ArrayList<String> getMess() {
        return mess;
    }

    public void setMess(ArrayList<String> mess) {
        this.mess = mess;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Integer> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Integer> posts) {
        this.posts = posts;
    }

    public ArrayList<Integer> getConversation() {
        return conversations;
    }

    public void setConversation(ArrayList<Integer> conversation) {
        this.conversations = conversation;
    }
}
