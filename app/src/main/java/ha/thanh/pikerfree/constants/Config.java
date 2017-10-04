package ha.thanh.pikerfree.constants;


public class Config {

    private boolean isPermissionWriteFile = false;
    private boolean isPermissionCamera = false;
    private boolean isPermissionLocation = false;

    private boolean isFirstRun = true;
    private String userID;
    private double userLat;
    private double userLng;

    public Config() {

    }

    public double getUserLat() {
        return userLat;
    }

    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }

    public double getUserLng() {
        return userLng;
    }

    public void setUserLng(double userLng) {
        this.userLng = userLng;
    }

    public boolean isFirstRun() {
        return isFirstRun;
    }

    public void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isPermissionWriteFile() {
        return isPermissionWriteFile;
    }

    public void setPermissionWriteFile(boolean permissionWriteFile) {
        isPermissionWriteFile = permissionWriteFile;
    }

    public boolean isPermissionCamera() {
        return isPermissionCamera;
    }

    public void setPermissionCamera(boolean permissionCamera) {
        isPermissionCamera = permissionCamera;
    }

    public boolean isPermissionLocation() {
        return isPermissionLocation;
    }

    public void setPermissionLocation(boolean permissionLocation) {
        isPermissionLocation = permissionLocation;
    }
}

